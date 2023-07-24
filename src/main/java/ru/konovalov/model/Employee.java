package ru.konovalov.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {


    private Long id;

    private String firstName;

    private String lastName;

    private Integer salary;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;

    private Long positionId;
}
