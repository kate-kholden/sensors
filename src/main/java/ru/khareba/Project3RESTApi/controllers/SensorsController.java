package ru.khareba.Project3RESTApi.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.khareba.Project3RESTApi.dto.SensorDTO;
import ru.khareba.Project3RESTApi.models.Measurement;
import ru.khareba.Project3RESTApi.models.Sensor;
import ru.khareba.Project3RESTApi.services.SensorsServices;
import ru.khareba.Project3RESTApi.util.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorsServices sensorsServices;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorsServices sensorsServices, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorsServices = sensorsServices;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<ru.khareba.Project3RESTApi.dto.SensorDTO> getSensors() {
        return sensorsServices.findAll().stream().map(this::convertToSensorDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ru.khareba.Project3RESTApi.dto.SensorDTO getSensor(@PathVariable("id") int id) {
        return convertToSensorDTO(sensorsServices.findOne(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult) {
        sensorValidator.validate(convertToSensor(sensorDTO), bindingResult);

        if (bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List <FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new SensorNotCreatedException(errorMessage.toString());
        }
        sensorsServices.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity <SensorErrorResponse> sensorException(SensorNotFoundException exception) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity <SensorErrorResponse> sensorException(SensorNotCreatedException exception) {
        SensorErrorResponse response = new SensorErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(ru.khareba.Project3RESTApi.dto.SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private ru.khareba.Project3RESTApi.dto.SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, ru.khareba.Project3RESTApi.dto.SensorDTO.class);
    }

}