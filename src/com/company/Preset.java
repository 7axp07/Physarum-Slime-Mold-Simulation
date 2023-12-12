package com.company;

import javafx.scene.paint.Color;

public class Preset {
    String name;
    int number;
    Color color;
    boolean isColourChanging;
    double trailDecay;
    int visionRange;
    double diffusionRate;

    public Preset(String name, int number, Color color, boolean isColourChanging, double trailDecay, double diffusionRate, int visionRange) {
        this.name = name;
        this.number = number;
        this.color = color;
        this.isColourChanging = isColourChanging;
        this.trailDecay = trailDecay;
        this.diffusionRate = diffusionRate;
        this.visionRange = visionRange;
    }

    @Override
    public String toString() {
        return name ;
    }

    public String parseToString() {
        return "" + name +
                ";" + number +
                ";" + color +
                ";" + isColourChanging +
                ";" + trailDecay +
                ";" + diffusionRate +
                ";" + visionRange
                ;
    }

/*    public static Preset toPreset(String string){


    }*/
}
