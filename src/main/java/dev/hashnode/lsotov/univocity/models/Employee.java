package dev.hashnode.lsotov.univocity.models;

import com.univocity.parsers.annotations.Format;
import com.univocity.parsers.annotations.Parsed;
import lombok.Data;

import java.util.Date;

@Data
public class Employee {
    @Parsed
    private Long id;
    @Parsed
    private String firstName;
    @Parsed
    private String lastName;
    @Parsed(field = "dateOfBirth")
    @Format(formats = {"yyyy-MM-dd"})
    private Date dob;
    @Parsed
    private String email;
    private boolean isActive;
}
