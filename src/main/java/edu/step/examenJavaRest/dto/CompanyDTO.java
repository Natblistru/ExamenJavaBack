package edu.step.examenJavaRest.dto;

import edu.step.examenJavaRest.model.Department;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class CompanyDTO {

    private Integer id;


    private String name;


    public CompanyDTO() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


