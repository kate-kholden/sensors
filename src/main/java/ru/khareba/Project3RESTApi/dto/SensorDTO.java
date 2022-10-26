package ru.khareba.Project3RESTApi.dto;

import javax.validation.constraints.NotEmpty;

public class SensorDTO {

    @NotEmpty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
