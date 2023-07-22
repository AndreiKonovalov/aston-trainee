package ru.konovalov.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Employee {


    private Long id;

    private String firstName;

    private String lastName;

    private Integer salary;

    private Date birthday;

    private Long positionId;

}
