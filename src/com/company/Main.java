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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static javafx.scene.paint.Color.*;

public class Main extends Application {

    static int WIDTH = 200;
    static int HEIGHT = 200;
    static int PARTICLE_NUMBER = 2000;
    static double TRAIL_DECAY = 0.9;
    static int LOOK_LENGTH = 7;
    static double DIFFUSION_RATE = 0.1;
    static int ZOOM = 3;
    Canvas canvas = new Canvas(WIDTH*ZOOM,HEIGHT*ZOOM);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Color particleColor = LAVENDER;
    Color backgroundColor = BLACK;


    static double[][] table = new double[WIDTH][HEIGHT];
    static Color[][] trailMap = new Color[WIDTH][HEIGHT];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane editor = new GridPane();


        AnchorPane root = new AnchorPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, WIDTH*ZOOM, HEIGHT*ZOOM);

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
        gc.fillRect(0,0,WIDTH*ZOOM,HEIGHT*ZOOM);

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

            particleColor = new Color(Math.random(), Math.random(), Math.random(), 1);
            for (int p = 0; p < PARTICLE_NUMBER; p++) {
                trailMap[particles.get(p).x][particles.get(p).y] = particleColor;
                gc.setFill(trailMap[particles.get(p).x][particles.get(p).y]);
                gc.fillRect(particles.get(p).x*ZOOM,particles.get(p).y*ZOOM, ZOOM,ZOOM);
                particles.get(p).move();
            }

            for (int i = 1; i+1 < HEIGHT; i++) {
                for (int j = 1; j+1 < WIDTH; j++) {
                    if (table[i][j] >= 0.1 ){
                    table[i][j] -= 0.1;}
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
                    if (trailMap[i][j] != backgroundColor){
                        trailMap[i][j] = new Color(
                                trailMap[i][j].getRed()*TRAIL_DECAY < 0.01 ? 0 : trailMap[i][j].getRed()*TRAIL_DECAY,
                                trailMap[i][j].getGreen()*TRAIL_DECAY < 0.01 ? 0 : trailMap[i][j].getGreen()*TRAIL_DECAY,
                                trailMap[i][j].getBlue()*TRAIL_DECAY < 0.01 ? 0 : trailMap[i][j].getBlue()*TRAIL_DECAY,
                                /*trailMap[i][j].getGreen()*0.99,
                                trailMap[i][j].getBlue()*0.99, */
                                trailMap[i][j].getOpacity());
                        gc.setFill(trailMap[i][j]);
                        gc.fillRect(i*ZOOM,j*ZOOM, ZOOM,ZOOM);
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
