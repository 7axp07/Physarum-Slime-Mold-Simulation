package com.company;

public class Particle {
    int x;
    int y;
    Direction direction;
    Sensor sensorL = new Sensor(x+direction.x);
    Sensor sensorR;
    Sensor sensorFront = new Sensor(x+ direction.x, y+ direction.y);
}
