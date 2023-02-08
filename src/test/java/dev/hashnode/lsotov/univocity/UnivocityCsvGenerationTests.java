package dev.hashnode.lsotov.univocity;

import dev.hashnode.lsotov.univocity.models.Employee;
import dev.hashnode.lsotov.univocity.utils.CsvGenerator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UnivocityCsvGenerationTests {

    public static final String FILE_PATH = "src/test/resources/univocity/exported_data.csv";

    @AfterAll
    public static void tearDown() {
        final File file = new File(FILE_PATH);
        file.delete();
    }

    @Test
    public void itShouldExportListToCsv() throws IOException {
        final var sdf = new SimpleDateFormat("yyyy-MM-dd");
        final var employees = getMockDataList();

        CsvGenerator.generateCsv(FILE_PATH, employees, Employee.class);

        final var file = new FileReader(FILE_PATH);
        final var records = CSVFormat
                .DEFAULT
                .parse(file);

        for (CSVRecord record : records) {
            // Assert that we exported 5 columns
            assertThat(record.stream().toList().size()).isEqualTo(5);

            if (record.getRecordNumber() == 1) { // Assert all column names
                assertThat(record.get(0)).isEqualTo("id");
                assertThat(record.get(1)).isEqualTo("firstName");
                assertThat(record.get(2)).isEqualTo("lastName");
                assertThat(record.get(3)).isEqualTo("dateOfBirth");
                assertThat(record.get(4)).isEqualTo("email");
            } else { // Assert each value
                final var employeeId = Long.valueOf(record.get(0));
                final var employee = employees.stream().filter(e -> employeeId.equals(e.getId())).findFirst().orElseThrow();
                assertThat(employeeId).isEqualTo(employee.getId());
                assertThat(record.get(1)).isEqualTo(employee.getFirstName());
                assertThat(record.get(2)).isEqualTo(employee.getLastName());
                assertThat(record.get(3)).isEqualTo(sdf.format(employee.getDob()));
                assertThat(record.get(4)).isEqualTo(employee.getEmail());
            }
        }
    }

    private List<Employee> getMockDataList() {
        final var list = new ArrayList<Employee>();

        for (int i = 0; i < 10; i++) {
            var emp = new Employee();
            emp.setId((long) i);
            emp.setFirstName(RandomStringUtils.randomAlphabetic(5));
            emp.setLastName(RandomStringUtils.randomAlphabetic(5));
            emp.setDob(new Date(ThreadLocalRandom.current().nextInt() * 1000L));
            emp.setEmail(RandomStringUtils.randomAlphabetic(5) + "@mail.com");
            emp.setActive(true);

            list.add(emp);
        }

        return list;
    }
}
