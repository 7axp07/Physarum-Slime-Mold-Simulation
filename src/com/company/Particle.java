package com.company;

public class Particle {
    int x;
    int y;
    Direction direction;

    /*Sensor sensorL = new Sensor(x+direction.x);
    Sensor sensorR;
    Sensor sensorFront = new Sensor(x+ direction.x, y+ direction.y);*/

    public Particle(){
        x = Main.WIDTH/2;
        y = Main.HEIGHT/2;
        direction = Direction.getRandomDirection();
    }

    public void move() {
        if (Math.random() > 0.95){
            direction = Direction.getRandomDirection();
        }
        x += direction.x%Main.WIDTH;
        y += direction.y%Main.HEIGHT;
        if (x>=Main.WIDTH-1 || x<=1 || y>=Main.HEIGHT-1 || y<= 1){
            Direction turnAround = Direction.getTurnAround(direction);
            direction = turnAround;
            x = x+ turnAround.x ;
            y = y+ turnAround.y ;
        }


//        Main.table[y][x] += 0.01;

    }
}
