package com.company;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import static javafx.scene.paint.Color.*;

public class Main extends Application {

    static final int WIDTH = 200;
    static final int HEIGHT = 200;
    static int ZOOM = 4;

    static int PARTICLE_NUMBER = 1000;
    static double TRAIL_DECAY = 0.3;
    static int LOOK_LENGTH = 7;
    static double DIFFUSION_RATE = 0.01;

    static boolean isColourChanging = false;
    static Color particleColor = WHITE;
    static Color backgroundColor = BLACK;

    static Stage stage;
    static Stage simulationStage = new Stage();
    static Canvas canvas = new Canvas(WIDTH * ZOOM, HEIGHT * ZOOM);
    static GraphicsContext gc = canvas.getGraphicsContext2D();

    static List<Preset> presets = FileController.getAllPresets();
    static double[][] table = new double[WIDTH][HEIGHT];
    static Color[][] trailMap = new Color[WIDTH][HEIGHT];

    static List<Screen> screens = Screen.getScreens();
    static Rectangle2D bounds = screens.get(1).getVisualBounds();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent editorWindow = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Editor.fxml")));



        stage = primaryStage;
        stage.setScene(new Scene(editorWindow));
        stage.setTitle("Control Panel");
        stage.setX(bounds.getMinX() +100);
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

        //Particle creation
        List<Particle> particles = new ArrayList<>(PARTICLE_NUMBER);
        for (int i = 0; i < PARTICLE_NUMBER; i++) {
            Particle particle = new Particle();
            particles.add(particle);
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(30), event -> {

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
                    if (table[i][j] <= 0.9 && table[i][j] >= DIFFUSION_RATE*10){
                    diffuse(i, j, DIFFUSION_RATE);}
                }
            }

            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    if (trailMap[i][j] != backgroundColor) {
                        trailMap[i][j] = new Color(
                                trailMap[i][j].getRed() * TRAIL_DECAY < 0.01 ? 0 : trailMap[i][j].getRed() * TRAIL_DECAY,
                                trailMap[i][j].getGreen() * TRAIL_DECAY < 0.01 ? 0 : trailMap[i][j].getGreen() * TRAIL_DECAY,
                                trailMap[i][j].getBlue() * TRAIL_DECAY < 0.01 ? 0 : trailMap[i][j].getBlue() * TRAIL_DECAY,
                                trailMap[i][j].getOpacity());
                        gc.setFill(trailMap[i][j]);
                        gc.fillRect(i * ZOOM, j * ZOOM, ZOOM, ZOOM);
                    }
                }
            }

        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        AnchorPane root = new AnchorPane();
        root.getChildren().add(canvas);
        Scene simulationScene = new Scene(root, WIDTH * ZOOM, HEIGHT * ZOOM);
        simulationStage.setX(bounds.getMinX() + 700);
        simulationStage.setY(100);
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

    public static void diffuse(int i, int j, double diffusionRate){
        table[i+1][j+1] += diffusionRate;
        table[i+1][j] += diffusionRate;
        table[i+1][j-1] += diffusionRate;
        table[i][j-1] += diffusionRate;
        table[i-1][j-1] += diffusionRate;
        table[i-1][j] += diffusionRate;
        table[i-1][j+1] += diffusionRate;
        table[i][j+1] += diffusionRate;
        table[i][j] -= diffusionRate*10;
    }
}

