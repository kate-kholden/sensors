package ru.khareba.Project3RESTApi.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.khareba.Project3RESTApi.models.Sensor;
import ru.khareba.Project3RESTApi.services.SensorsServices;

@Component
public class SensorValidator implements Validator {
    private final SensorsServices sensorsServices;

    public SensorValidator(SensorsServices sensorsServices) {
        this.sensorsServices = sensorsServices;

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        if(sensorsServices.findByName(sensor.getName()) != null){
            errors.rejectValue("name", "Sensor name should be unique. There is a sensor with such name");
        }
    }
}