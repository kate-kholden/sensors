package ru.khareba.Project3RESTApi.dto;

import ru.khareba.Project3RESTApi.models.Sensor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class MeasurementDTO {
    @NotEmpty(message = "Value should be not empty")
    @Min(value = -100, message = "Temperature should be more than -100")
    @Max(value = 100, message = "Temperature should be less then 100")
    private float value;

    @NotEmpty (message = "Raining should be not empty")
    private boolean raining;

    @NotEmpty (message = "Sensor name should be not empty")
    private SensorDTO sensor;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
