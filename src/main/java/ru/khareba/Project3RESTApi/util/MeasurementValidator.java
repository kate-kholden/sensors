package ru.khareba.Project3RESTApi.util;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.khareba.Project3RESTApi.models.Measurement;
import ru.khareba.Project3RESTApi.services.SensorsServices;

@Component
public class MeasurementValidator implements Validator {
    private final SensorsServices sensorsServices;

    public MeasurementValidator(SensorsServices sensorsServices) {
        this.sensorsServices = sensorsServices;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if (measurement.getSensor() == null) {
            return;
        }
        if (sensorsServices.findByName(measurement.getSensor().getName()) == null) {
            errors.rejectValue("sensorName", "There is not sensor with such name");
        }
    }
}
