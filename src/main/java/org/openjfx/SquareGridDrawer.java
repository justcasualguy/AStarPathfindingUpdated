package org.openjfx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class SquareGridDrawer {
    private double cellLength;
    private int cellsInRow;
    private double cellCentre;
    private double customCentreX;
    private double customCentreY;


    public SquareGridDrawer() {
    }

    public SquareGridDrawer(int cellsInRow) {
        this.cellsInRow = cellsInRow;
    }
    void computeCellLength(Canvas canvas, int cellsInRow){
        cellLength=canvas.getHeight()/cellsInRow;
        cellCentre=cellLength/2;
    }

    void computeCellCentre(int cellPosX,int cellPosY){
        customCentreX=cellPosX*cellLength+cellCentre;
        customCentreY=cellPosY*cellLength+cellCentre;
    }
    public SquareGridDrawer(double cellLength) {
        this.cellLength = cellLength;
    }




     void drawGrid(GraphicsContext gc, int cellsInRow){
        for(int x=0;x<=gc.getCanvas().getWidth();x++){
            gc.moveTo(x*gc.getCanvas().getWidth()/cellsInRow,0);
            gc.lineTo(x*gc.getCanvas().getWidth()/cellsInRow,gc.getCanvas().getHeight());
            gc.stroke();
        }
        for(int y=0;y<=gc.getCanvas().getHeight();y++){
            gc.moveTo(0,y*gc.getCanvas().getHeight()/cellsInRow);
            gc.lineTo(gc.getCanvas().getWidth(),y*gc.getCanvas().getHeight()/cellsInRow);
            gc.stroke();
        }
    }

    void setGrid(GraphicsContext gc,double gridWidth,double gridHeight){

        for(double x=0;x<=gc.getCanvas().getWidth();x+=(gc.getCanvas().getWidth()/gridWidth)){
            gc.moveTo(x,0);
            gc.lineTo(x,gc.getCanvas().getHeight());
            gc.stroke();
        }
        for(double y=0;y<=gc.getCanvas().getHeight();y+=(gc.getCanvas().getHeight()/gridHeight)){
            gc.moveTo(0,y);
            gc.lineTo(gc.getCanvas().getWidth(),y);
            gc.stroke();
        }
    }

    public double getCustomCentreX() {
        return customCentreX;
    }

    public void setCustomCentreX(double customCentreX) {
        this.customCentreX = customCentreX;
    }

    public double getCustomCentreY() {
        return customCentreY;
    }

    public void setCustomCentreY(double customCentreY) {
        this.customCentreY = customCentreY;
    }
}
