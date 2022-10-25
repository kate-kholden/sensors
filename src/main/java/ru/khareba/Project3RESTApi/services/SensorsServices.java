package ru.khareba.Project3RESTApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khareba.Project3RESTApi.models.Measurement;
import ru.khareba.Project3RESTApi.models.Sensor;
import ru.khareba.Project3RESTApi.repositories.SensorsRepository;
import ru.khareba.Project3RESTApi.util.SensorNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsServices {
    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsServices (SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }
    public List<Sensor> findAll(){
        return sensorsRepository.findAll();
    }

    public Sensor findOne(int id) throws SensorNotFoundException {
        Optional<Sensor> foundSensor = sensorsRepository.findById(id);
        return foundSensor.orElseThrow(SensorNotFoundException::new);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }
}
