package org.openjfx;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.util.Random;



public class Car {
    private double posX;
    private double posY;
    private int gridX;
    private int gridY;
    private double imgCentreX;
    private double imgCentreY;
    private double speed;
    double driven = 0;
    Image img;


    Car(int gridX,int gridY,double speed) {
        this.gridX=gridX;
        this.gridY=gridY;
        try {
            img = new Image(new FileInputStream("C:\\Users\\kubac\\Desktop\\sam.png"));
            imgCentreX=img.getWidth()/2;
            imgCentreY=img.getHeight()/2;
            System.out.println("img size: "+img.getWidth()+","+img.getHeight());
        } catch (Exception e) {
            img=null;
        }

        this.speed=speed;
    }

    void countPos(int gridX,int gridY){

    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    void draw(GraphicsContext gc){ gc.drawImage(img,posX,posY); }
    void draw(GraphicsContext gc,double x,double y){
        gc.drawImage(img,x+img.getWidth()/2,y+img.getHeight()/2);
    }

    void driveUp(GraphicsContext gc,double howFar){
        final long startNanoTime = System.nanoTime();
        double x = this.posX;
        double y = this.posX;

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {

                double t = 2;
                System.out.println(t);
                System.out.println("y: "+y);
                posY += speed*t;
                driven+=speed*t;
                // background image clears canvas
                gc.clearRect(0, 0, 512, 512);
                gc.drawImage( img, posX, posY );

                if(posY-y>=howFar||posY>0) {
                    this.stop();

                }

            }
        }.start();
    }


    }


