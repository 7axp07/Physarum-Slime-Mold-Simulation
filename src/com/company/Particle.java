package com.company;

public class Particle {
    int x;
    int y;
    Direction direction;
    boolean border = false;
    double randomness = 0.98;

    /*Sensor sensorL = new Sensor(x+direction.x);
    Sensor sensorR;
    Sensor sensorFront = new Sensor(x+ direction.x, y+ direction.y);*/

    public Particle(){
        x = (int) (Math.random()*(Main.WIDTH-5)+3);
        y = (int) (Math.random()*(Main.HEIGHT-5)+3);
        direction = Direction.getRandomDirection();
    }

    public void move() {
        if (x>=Main.WIDTH-2 || x<=2 || y>=Main.HEIGHT-2 || y<= 2){
            border = true;
            Direction turnAround = Direction.getTurnAround(direction);
            direction = turnAround;
            x = x+ turnAround.x ;
            y = y+ turnAround.y ;
        }
        else {border = false;}
        direction= sense(direction);
        if (Math.random() > randomness){
            if (Math.random()>0.5){
                direction = Direction.getFrontRightTurn(direction);
            }
            else {
                direction = Direction.getFrontLeftTurn(direction);
            }
        }
        x += direction.x%Main.WIDTH;
        y += direction.y%Main.HEIGHT;



        if (Main.table[x][y] < 1){
            Main.table[x][y] += 0.1;
        }

    }

    private Direction sense(Direction current) {
        double front = Main.table[x+current.x][y+current.y];
        double frontright = Main.table[x+Direction.getFrontRightTurn(current).x ][y+Direction.getFrontRightTurn(current).y];
        double frontleft = Main.table[x+Direction.getFrontLeftTurn(current).x][y+Direction.getFrontLeftTurn(current).y];
        if (!border){
            front += Main.table[x+current.x+current.x][y+current.y+current.y];
            frontright += Main.table[x+Direction.getFrontRightTurn(current).x+Direction.getFrontRightTurn(Direction.getFrontRightTurn(current)).x ][y+Direction.getFrontRightTurn(current).y+Direction.getFrontRightTurn(Direction.getFrontRightTurn(current)).y];
            frontleft += Main.table[x+Direction.getFrontLeftTurn(current).x+Direction.getFrontLeftTurn(Direction.getFrontLeftTurn(current)).x][y+Direction.getFrontLeftTurn(current).y+Direction.getFrontLeftTurn(Direction.getFrontLeftTurn(current)).y];
        }
        double max = front;
        if (Math.max(max, frontright) == frontright){
         max = frontright;
         return Direction.getFrontRightTurn(current);
        }
        else if (max == frontright){
            if (Math.random()>0.5){
                return Direction.getFrontRightTurn(current);
            }
            else {
               return current;
            }
        }
        else if (Math.max(max, frontleft) == frontleft){
            max = frontleft;
            return Direction.getFrontLeftTurn(current);
        }
        else if (max == frontleft){
            if (Math.random()>0.5){
                return Direction.getFrontLeftTurn(current);
            }
            else {
                return current;
            }
        }
        return current;
    }
}
