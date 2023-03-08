package dev.hashnode.lsotov.http.gzip.encoding;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

@Slf4j
@UtilityClass
public class GzipEncoderReader {

    public static final String GZIP = "gzip";

    public static String readGzipConnection(final String requestUrl, final boolean addGzipHeader) throws IOException {
        final var url = new URL(requestUrl);
        final var connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        if (addGzipHeader) {
            connection.setRequestProperty("Accept-Encoding", GZIP);
        }
        final var responseCode = connection.getResponseCode();
        final var encoding = connection.getContentEncoding();
        String response;
        if (GZIP.equalsIgnoreCase(encoding)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())))) {
                response = reader.readLine();
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                response = reader.readLine();
            }
        }
        log.info("ResponseCode=[{}] encoding=[{}]", responseCode, encoding);
        log.info("Response from server=[{}]", response);
        connection.disconnect();

        return response;
    }
}
