package com.company;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static javafx.scene.paint.Color.*;

public class Main extends Application {

    static int WIDTH = 200;
    static int HEIGHT = 200;
    static int PARTICLE_NUMBER = 1000;
    static double TRAIL_DECAY = 0.3;
    static int LOOK_LENGTH = 7;
    static double DIFFUSION_RATE = 0.1;
    static int ZOOM = 4;
    static boolean isColourChanging = false;
    static Color particleColor = LAVENDER;
    static Color backgroundColor = BLACK;

    static Stage stage;
    static Stage simulationStage = new Stage();
    static Canvas canvas = new Canvas(WIDTH * ZOOM, HEIGHT * ZOOM);
    static GraphicsContext gc = canvas.getGraphicsContext2D();


    static double[][] table = new double[WIDTH][HEIGHT];
    static Color[][] trailMap = new Color[WIDTH][HEIGHT];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Preset test = new Preset("test",3, BLUE, false, 0.1, 3 );
        System.out.println(test.toString());

        Parent editorWindow = FXMLLoader.load(getClass().getResource("Editor.fxml"));

        stage = primaryStage;
        stage.setScene(new Scene(editorWindow));
        stage.setTitle("Simulation Editor");
        stage.setX(20);
        stage.setY(150);
        stage.show();
    }

    static public void generateSim() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                table[i][j] = Math.random();
            }
        }
        //Background

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                trailMap[i][j] = backgroundColor;
            }
        }
        gc.setFill(backgroundColor);
        gc.fillRect(0, 0, WIDTH * ZOOM, HEIGHT * ZOOM);

        //System.out.println(Arrays.deepToString(table));

        List<Particle> particles = new ArrayList<>(PARTICLE_NUMBER);
        for (int i = 0; i < PARTICLE_NUMBER; i++) {
            Particle particle = new Particle();
            particles.add(particle);
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(30), event -> {
           /* for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    Color color = new Color(table[i][j], table[i][j], table[i][j], 1);
                    gc.setFill(color);
                    gc.fillRect(j,i,1,1);
                }}*/
            /*for (int i = 0; i < PARTICLE_NUMBER; i++) {
                gc.setFill(particleColor);
                int x = particles.get(i).x;
                int y = particles.get(i).y;
                particles.get(i).move();
                gc.fillRect(particles.get(i).x, particles.get(i).y, 2,2);
                gc.setFill(RED);
                gc.fillRect(x, y, 2,2);
            }*/

            if (isColourChanging) {
                particleColor = new Color(Math.random(), Math.random(), Math.random(), 1);
            }
            for (int p = 0; p < PARTICLE_NUMBER; p++) {
                trailMap[particles.get(p).x][particles.get(p).y] = particleColor;
                gc.setFill(trailMap[particles.get(p).x][particles.get(p).y]);
                gc.fillRect(particles.get(p).x * ZOOM, particles.get(p).y * ZOOM, ZOOM, ZOOM);
                particles.get(p).move();
            }

            for (int i = 1; i + 1 < HEIGHT; i++) {
                for (int j = 1; j + 1 < WIDTH; j++) {
                    if (table[i][j] >= 0.1) {
                        table[i][j] -= 0.1;
                    }
                   /* if (table[i][j] <= 0.9){
                    table[i+1][j+1] += 0.01;
                    table[i+1][j] += 0.01;
                    table[i+1][j-1] += 0.01;
                    table[i][j-1] += 0.01;
                    table[i-1][j-1] += 0.01;
                    table[i-1][j] += 0.01;
                    table[i-1][j+1] += 0.01;
                    table[i][j+1] += 0.01;}*/
                }
            }

            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    if (trailMap[i][j] != backgroundColor) {
                        trailMap[i][j] = new Color(
                                trailMap[i][j].getRed() * TRAIL_DECAY < 0.01 ? 0 : trailMap[i][j].getRed() * TRAIL_DECAY,
                                trailMap[i][j].getGreen() * TRAIL_DECAY < 0.01 ? 0 : trailMap[i][j].getGreen() * TRAIL_DECAY,
                                trailMap[i][j].getBlue() * TRAIL_DECAY < 0.01 ? 0 : trailMap[i][j].getBlue() * TRAIL_DECAY,
                                /*trailMap[i][j].getGreen()*0.99,
                                trailMap[i][j].getBlue()*0.99, */
                                trailMap[i][j].getOpacity());
                        gc.setFill(trailMap[i][j]);
                        gc.fillRect(i * ZOOM, j * ZOOM, ZOOM, ZOOM);
                    }
                }
            }

            // TODO: 05.10.2023 ADD RGB MAP FOR TRAIL INTENSITY (OTHER table is for the pheronome intensity))))

        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        AnchorPane root = new AnchorPane();
        root.getChildren().add(canvas);
        Scene simulationScene = new Scene(root, WIDTH * ZOOM, HEIGHT * ZOOM);
        simulationStage.setScene(simulationScene);
        simulationStage.show();



        simulationScene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ESCAPE) {
                simulationStage.close();
                timeline.stop();
            }
            if (key.getCode() == KeyCode.SPACE) {
                saveInFile(takeScreenshot(simulationScene), (int) (Math.random()*PARTICLE_NUMBER));
            }
        });

    }

    static WritableImage takeScreenshot(Scene scene) {
        WritableImage image = scene.snapshot(new WritableImage(WIDTH*ZOOM, HEIGHT*ZOOM));
        return image;
    }

    public static void saveInFile(WritableImage image, int i) {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File(i + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// TODO: 26.10.2023 add more bg colors ?? maybe
// TODO: 26.10.2023 allow adding multiple Editor setting (custom sight particles color etc)