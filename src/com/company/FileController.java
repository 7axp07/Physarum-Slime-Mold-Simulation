package com.company;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileController {


    public static void addPreset(){
        try {
            PrintWriter printwriter = new PrintWriter(new File("presets"));
            for (int i = 0; i < Main.presets.size(); i++) {
                printwriter.println(Main.presets.get(i).parseToString());
            }
            printwriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<Preset> getAllPresets(){
        List<Preset> presets = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("presets"));
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] array = line.split(";");
                presets.add(new Preset(array[0], Integer.parseInt(array[1]), Color.web(array[2]) ,
                        Boolean.parseBoolean(array[3]), Double.parseDouble(array[4]),Double.parseDouble(array[5]),
                        Integer.parseInt(array[6])));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return presets;
    }

}
