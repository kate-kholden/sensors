package ru.khareba.Project3RESTApi.dto;

import ru.khareba.Project3RESTApi.models.Measurement;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class SensorDTO {

    @NotEmpty
    private String name; // todo отдалить связь

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
