package ru.khareba.Project3RESTApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khareba.Project3RESTApi.models.Measurement;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MeasurementsRepository  extends JpaRepository <Measurement, Integer> {

    List<Measurement> findByRaining(boolean isRaining);
}
