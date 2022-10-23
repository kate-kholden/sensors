package ru.khareba.Project3RESTApi.dto;

import ru.khareba.Project3RESTApi.models.Measurement;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class SensorDTO {

    @NotEmpty
    private List<Measurement> name; // ХРЕН ПОЙМЕШЬ ЧТО ТУТ ДЕЛАТЬ

    public List<Measurement> getName() {
        return name;
    }

    public void setName(List<Measurement> name) {
        this.name = name;
    }
}
