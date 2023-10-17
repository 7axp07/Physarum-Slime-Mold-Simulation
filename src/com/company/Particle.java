package com.company;

import static com.company.Main.LOOK_LENGTH;

public class Particle {
    int x;
    int y;
    Direction direction;
    boolean border = false;
    double randomness = 0.97;

    /*Sensor sensorL = new Sensor(x+direction.x);
    Sensor sensorR;
    Sensor sensorFront = new Sensor(x+ direction.x, y+ direction.y);*/

    public Particle(){
        x = (int) (Math.random()*(Main.WIDTH-LOOK_LENGTH-3)+LOOK_LENGTH+1);
        y = (int) (Math.random()*(Main.HEIGHT-LOOK_LENGTH-3)+LOOK_LENGTH+1);
        direction = Direction.getRandomDirection();
    }

    public void move() {

        direction= sense(direction, LOOK_LENGTH);
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

        if (x>=Main.WIDTH- 1 || x<=1 || y>=Main.HEIGHT-1 || y<= 1){
            Direction turnAround = Direction.getTurnAround(direction);
            direction = turnAround;
            x = x+ turnAround.x ;
            y = y+ turnAround.y ;
        }

        Main.table[x][y] += 10;
      /*  if (Main.table[x][y] < 1){
            Main.table[x][y] += Main.table[x][y]/2;
        }*/

    }

    private Direction sense(Direction current, int lookLenght) {
        double front = sensor(x,y,current, lookLenght);
                //Main.table[x+current.x][y+current.y];
        double frontright =  sensor(x, y,Direction.getFrontRightTurn(current), lookLenght);
                //Main.table[x+Direction.getFrontRightTurn(current).x ][y+Direction.getFrontRightTurn(current).y];
        double frontleft = sensor(x,y,Direction.getFrontLeftTurn(current), lookLenght);
                //Main.table[x+Direction.getFrontLeftTurn(current).x][y+Direction.getFrontLeftTurn(current).y];
       /* if (!border && lookLenght > 1){
            front += Main.table[x+current.x+current.x][y+current.y+current.y];
            frontright += Main.table[x+Direction.getFrontRightTurn(current).x+Direction.getFrontRightTurn(Direction.getFrontRightTurn(current)).x ][y+Direction.getFrontRightTurn(current).y+Direction.getFrontRightTurn(Direction.getFrontRightTurn(current)).y];
            frontleft += Main.table[x+Direction.getFrontLeftTurn(current).x+Direction.getFrontLeftTurn(Direction.getFrontLeftTurn(current)).x][y+Direction.getFrontLeftTurn(current).y+Direction.getFrontLeftTurn(Direction.getFrontLeftTurn(current)).y];
        }*/
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

    private double sensor(int x, int y, Direction direction, int lookLength){
        double sum = 0.0;
        if (x>=Main.WIDTH- LOOK_LENGTH || x<=LOOK_LENGTH || y>=Main.HEIGHT-LOOK_LENGTH || y<= LOOK_LENGTH){lookLength -= LOOK_LENGTH;}
        for (int i = 0; i < lookLength; i++) {
            int xi = x+direction.x;
            int yi = y+direction.y;
            if (xi < 0 || yi < 0 || xi >= Main.WIDTH || yi >= Main.HEIGHT){
                break;
            }
            sum += Main.table[xi][yi];
        }
        return sum;
    }
}
