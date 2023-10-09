package com.company;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static javafx.scene.paint.Color.*;

public class Main extends Application {

    static final int WIDTH = 200;
    static final int HEIGHT = 200;
    static final int PARTICLE_NUMBER = 100;
    Canvas canvas = new Canvas(WIDTH,HEIGHT);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Color particleColor = BLUE;
    Color backgroundColor = BLACK;


    static double[][] table = new double[WIDTH][HEIGHT];
    static Color[][] trailMap = new Color[WIDTH][HEIGHT];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = new AnchorPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

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
        gc.setFill(BLACK);
        gc.fillRect(0,0,WIDTH,HEIGHT);

        //System.out.println(Arrays.deepToString(table));

        List<Particle> particles = new ArrayList<>(PARTICLE_NUMBER);
        for (int i = 0; i < PARTICLE_NUMBER; i++) {
            Particle particle = new Particle();
            particles.add(particle);
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), event -> {
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


            for (int p = 0; p < PARTICLE_NUMBER; p++) {
                for (int i = 0; i < HEIGHT; i++) {
                    for (int j = 0; j < WIDTH; j++) {
                        if (i == particles.get(p).x && j == particles.get(p).y){
                            trailMap[i][j] = particleColor;
                            gc.setFill(trailMap[i][j]);
                            gc.fillRect(i,j, 1,1);
                        }
                    }
                }
                particles.get(p).move();

            }

            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    if (table[i][j] >= 0.01);
                    table[i][j] = table[i][j]-0.01;
                }
            }
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    if (trailMap[i][j] != backgroundColor){
                        trailMap[i][j] = new Color(
                                trailMap[i][j].getRed()*0.99 < 0.01 ? 0 : trailMap[i][j].getRed()*0.99,
                                trailMap[i][j].getGreen()*0.99 < 0.01 ? 0 : trailMap[i][j].getGreen()*0.99,
                                trailMap[i][j].getBlue()*0.99 < 0.01 ? 0 : trailMap[i][j].getBlue()*0.99,
                                /*trailMap[i][j].getGreen()*0.99,
                                trailMap[i][j].getBlue()*0.99, */
                                trailMap[i][j].getOpacity());
                        gc.setFill(trailMap[i][j]);
                        gc.fillRect(i,j, 1,1);
                    }
                }
            }

            // TODO: 05.10.2023 ADD RGB MAP FOR TRAIL INTENSITY (OTHER table is for the pheronome intensity))))

        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        primaryStage.setScene(scene);
        primaryStage.setTitle("a");
        primaryStage.show();
    }
}
