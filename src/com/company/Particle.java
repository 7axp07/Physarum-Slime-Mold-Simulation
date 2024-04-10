package com.company;

import static com.company.Main.LOOK_LENGTH;
import static com.company.Main.WIDTH;

public class Particle {
    int x;
    int y;
    Direction direction;
    boolean border = false;
    double randomness = 0.99;


    public Particle(){
        x = (int) (Math.random()*(Main.WIDTH-LOOK_LENGTH-2)+LOOK_LENGTH+1);
        y = (int) (Math.random()*(Main.HEIGHT-LOOK_LENGTH-2)+LOOK_LENGTH+1);
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
    }

    private Direction sense(Direction current, int lookLenght) {
        double front = sensor(x, y, current, lookLenght);
        double frontright = sensor(x, y, Direction.getFrontRightTurn(current), lookLenght);
        double frontleft = sensor(x, y, Direction.getFrontLeftTurn(current), lookLenght);

        double max = front;
        if (frontright > frontleft) {
            if (Math.max(max, frontright) == frontright) {
                max = frontright;
                return Direction.getFrontRightTurn(current);
            }
            else if (max == frontright) {
                if (Math.random() > 0.5) {
                    return Direction.getFrontRightTurn(current);
                }
                else {
                    return current;
                }
            }
        } else {
            if (Math.max(max, frontleft) == frontleft) {
                max = frontleft;
                return Direction.getFrontLeftTurn(current);
            } else if (max == frontleft) {
                if (Math.random() > 0.5) {
                    return Direction.getFrontLeftTurn(current);
                } else {
                    return current;
                }
            }
        }
        return current;
    }

    private double sensor(int x, int y, Direction direction, int lookLength){
        double sum = 0.0;
        if (x>=Main.WIDTH- LOOK_LENGTH){
            lookLength -= lookLength-(WIDTH-x);}
        else if (x<=LOOK_LENGTH){
            lookLength -= (lookLength + x);
        }
        else if ( y>=Main.HEIGHT-LOOK_LENGTH){
            lookLength -= lookLength-(WIDTH-y);
        }
        else if (y<= LOOK_LENGTH){
            lookLength -= (lookLength + y);
        }
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
