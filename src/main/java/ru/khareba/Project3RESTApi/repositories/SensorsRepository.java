package ru.khareba.Project3RESTApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.khareba.Project3RESTApi.models.Measurement;
import ru.khareba.Project3RESTApi.models.Sensor;

import java.util.List;

@Repository
public interface SensorsRepository extends JpaRepository <Sensor,Integer> {
    List<Sensor> findByName (String name);}
