package com.company;

public enum Direction {
    UP(0, -1), UPRIGHT (1,-1),RIGHT(1, 0), DOWNRIGHT(1,1), DOWN(0, 1), DOWNLEFT(-1,1),LEFT(-1, 0), UPLEFT(-1,-1);

    int x;
    int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public static Direction getRandomDirection() {
        return values()[(int) (Math.random() * values().length)];
    }

    public static int index(Direction current) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i] == current) {
                return i;
            }
        }
        return -1;
    }

    public static Direction getLeftTurn(Direction current) {
        return values()[(index(current)-2 +values().length)%values().length];
    }
    public static Direction getFrontLeftTurn(Direction current) {
        return values()[(index(current)-1 +values().length)%values().length];
    }

    public static Direction getTurnAround(Direction current){
        return values()[(index(current)-4 +values().length)%values().length];
    }

    public static Direction getRightTurn(Direction current) {
        return values()[(index(current)+2)% values().length];
    }

    public static Direction getFrontRightTurn(Direction current) {
        return values()[(index(current)+1)% values().length];
    }

}


