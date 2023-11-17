package edu.step.examenJavaRest.dto;

import edu.step.examenJavaRest.model.Company;
import edu.step.examenJavaRest.model.Employee;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class DepartmentDTO {

    private Integer id;

    private String name;

    private Integer companyId;

    public DepartmentDTO() {
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}

