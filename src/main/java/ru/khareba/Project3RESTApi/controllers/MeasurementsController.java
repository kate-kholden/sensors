package ru.khareba.Project3RESTApi.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.khareba.Project3RESTApi.dto.MeasurementDTO;
import ru.khareba.Project3RESTApi.models.Measurement;
import ru.khareba.Project3RESTApi.services.MeasurementsServices;
import ru.khareba.Project3RESTApi.util.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsServices measurementsServices;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementsServices measurementsServices,
                                  MeasurementValidator measurementValidator, ModelMapper modelMapper) {
        this.measurementsServices = measurementsServices;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<MeasurementDTO> getMeasurement() {
        return measurementsServices.findAll().stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MeasurementDTO getMeasurement(@PathVariable("id") int id) {
        return convertToMeasurementDTO(measurementsServices.findOne(id));
    }

    @GetMapping("/rainyDays")
    public List<MeasurementDTO> getRainyDays() {
        return measurementsServices.findRainyDays(true).stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public Long getCountOfRainy() {
        return getRainyDays().stream().count();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult) {
        measurementValidator.validate(convertToMeasurement(measurementDTO), bindingResult);
        if (bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List <FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new MeasurementNotCreatedException(errorMessage.toString());
        }
        measurementsServices.save(measurementDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // todo отдалить метод. Должен передавать 1000 раз случайные измерения
//    @PostMapping("/add1000requests")
//    public ResponseEntity<HttpStatus> create1000() {
//        RestTemplate restTemplate = new RestTemplate();
//        Map<String,String> json = new HashMap();
//
//        for (int i = 0; i<1000; i++) {
//            json.put("value", String.valueOf(Math.random() * 50));
//            json.put("raining", String.valueOf(Math.random() < 0.5));
//            json.put("sensorName", "Sensor name");
//            String url = "http://localhost:8080/measurements/add";
//            restTemplate.postForObject(url, new HttpEntity<>(json), Measurement.class);
//        }
//
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> measurementException(MeasurementNotFoundException exception) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                "Measurement with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> measurementException(MeasurementNotCreatedException exception) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO (Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

}