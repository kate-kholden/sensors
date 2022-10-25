package ru.khareba.Project3RESTApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khareba.Project3RESTApi.models.Measurement;
import ru.khareba.Project3RESTApi.models.Sensor;
import ru.khareba.Project3RESTApi.repositories.MeasurementsRepository;
import ru.khareba.Project3RESTApi.util.MeasurementNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsServices {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsServices sensorsServices;

    @Autowired
    public MeasurementsServices(MeasurementsRepository measurementsRepository, SensorsServices sensorsServices) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsServices = sensorsServices;
    }
    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    public List<Measurement> findRainyDays(boolean raining) {
        return measurementsRepository.findByRaining(raining);
    }
    public List<Measurement> findBySensor(String sensorName) {
        return measurementsRepository.findBySensor(sensorName);
    }

    public Measurement findOne(int id) throws MeasurementNotFoundException {
        Optional<Measurement> foundMeasurement = measurementsRepository.findById(id);
        return foundMeasurement.orElseThrow(MeasurementNotFoundException::new);
    }
    @Transactional
    public void save(Measurement measurement) {
        measurement.setDate(LocalDateTime.now());
        System.out.println(measurement.toString());

        //measurement.setSensor(sensorsServices.findByName(measurement.getSensor().getName()));
        // TODO не работает, а надо взять и найти объект по имени в БД и добавить его сюда

        measurementsRepository.save(measurement);
    }
}
