package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileController {

    public static void addPreset(String preset){

    }

    public static List<Preset> getAllPresets(){
        List<Preset> presets = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("data"));
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] preset = line.split(";");

            }


            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return presets;
    }

}
