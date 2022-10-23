package ru.khareba.Project3RESTApi.util;

public class SensorNotCreatedException extends RuntimeException {
    public SensorNotCreatedException (String msg){
        super(msg);
    }
}
