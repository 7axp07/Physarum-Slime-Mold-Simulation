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
        direction= sense(direction);
        if (Math.random() > 0.95){
            if (Math.random()>0.5){
                direction = Direction.getFrontRightTurn(direction);
            }
            else {
                direction = Direction.getFrontLeftTurn(direction);
            }
        }
        x += direction.x%Main.WIDTH;
        y += direction.y%Main.HEIGHT;
        if (x>=Main.WIDTH-1 || x<=1 || y>=Main.HEIGHT-1 || y<= 1){
            Direction turnAround = Direction.getTurnAround(direction);
            direction = turnAround;
            x = x+ turnAround.x ;
            y = y+ turnAround.y ;
        }


        Main.table[x][y] = 1;

    }

    private Direction sense(Direction current) {
        double front = Main.table[x+current.x][y+current.y];
        double frontright = Main.table[x+Direction.getFrontRightTurn(current).x][y+Direction.getFrontRightTurn(current).y];;
        double frontleft = Main.table[x+Direction.getFrontLeftTurn(current).x][y+Direction.getFrontLeftTurn(current).y];;
        double max = front;
        if (Math.max(max, frontright) == frontright){
         max = frontright;
         return Direction.getFrontRightTurn(current);
        }
        else if (Math.max(max, frontleft) == frontleft){
            max = frontleft;
            return Direction.getFrontLeftTurn(current);
        }
        return current;
    }
}
