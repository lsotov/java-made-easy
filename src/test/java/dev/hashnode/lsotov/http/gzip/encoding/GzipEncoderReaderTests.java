package dev.hashnode.lsotov.http.gzip.encoding;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GzipEncoderReaderTests {

    public static final String REQUEST_URL = "https://lsotov.hashnode.dev/";
    public static final String BLOG_NAME = "Java Made Easy";

    @Test
    void itShouldParseResponseWhenUsingGzipEncoding() throws IOException {
        final String response = GzipEncoderReader.readGzipConnection(REQUEST_URL, true);
        assertThat(response).contains(BLOG_NAME);
    }

    @Test
    void itShouldParseResponseWhenNotUsingGzipEncoding() throws IOException {
        final String response = GzipEncoderReader.readGzipConnection(REQUEST_URL, false);
        assertThat(response).contains(BLOG_NAME);
    }
}
