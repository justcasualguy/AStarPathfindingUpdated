package org.openjfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.util.List;
import java.util.Random;

public class App extends Application {






    @Override
    public void start(Stage stage) {

        Pane root = new Pane();
        Scene scene = new Scene(root,800,800);
        stage.setScene(scene);

        double canvasWidth=512;
        double canvasHeight=512;
        int mapSize = 10;
        Canvas backgroundLayer = new Canvas(canvasWidth,canvasHeight);
        Canvas carLayer = new Canvas(canvasWidth,canvasHeight);
        Button button = new Button();
        SquareGridDrawer drawer=new SquareGridDrawer(mapSize);

        root.getChildren().add(backgroundLayer);
        root.getChildren().add(carLayer);

        GraphicsContext backContext= backgroundLayer.getGraphicsContext2D();
        GraphicsContext carContext= carLayer.getGraphicsContext2D();
        backContext.setFill(Color.WHITE);
        backContext.fillRect(0, 0, canvasWidth, canvasHeight);
        //setGrid(back,5,5);
        drawer.drawGrid(backContext,mapSize);
        drawer.computeCellCentre(0,0);
        Car testCar = new Car(0,0,2);
        //testCar.draw(carContext,drawer.getCustomCentreX(),drawer.getCustomCentreY());
        testCar.draw(carContext,0,0);

        //testCar.driveUp(cr,100);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}