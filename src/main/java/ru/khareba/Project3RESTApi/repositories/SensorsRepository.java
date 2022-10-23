package ru.khareba.Project3RESTApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khareba.Project3RESTApi.models.Sensor;

@Repository
public interface SensorsRepository extends JpaRepository <Sensor,Integer> {
}
