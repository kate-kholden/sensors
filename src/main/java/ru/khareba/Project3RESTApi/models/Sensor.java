package ru.khareba.Project3RESTApi.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "Sensor")
public class Sensor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@Column(name = "name")
    @OneToMany (mappedBy = "sensor")
    @NotEmpty
    private List<Measurement> name; // ХРЕН ПОЙМЕШЬ ЧТО ТУТ ДЕЛАТЬ

    public Sensor() {
    }

    public Sensor(List<Measurement> name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Measurement> getName() {
        return name;
    }

    public void setName(List<Measurement> name) {
        this.name = name;
    }
}
