package org.openjfx.pathfinfing;

import java.util.LinkedList;
import java.util.Random;

public class MapNode {
    private LinkedList<MapNode> neighbours;
    private int posX;
    private int posY;
    private int fCost;
    private int hCost;
    private MapNode parent;
    private boolean traversable;

    public boolean isTraversable() {
        return traversable;
    }
    public boolean areNeighboursTraversable(){
        for(MapNode n:neighbours){
            if(n.isTraversable()&&n!=parent)
                return true;
        }
        return false;
    }
//    public MapNode findTraversableNeighbour(double blockProb){
//        Random rand= new Random();
//        while(true) {
//            for (MapNode n : neighbours) {
//                if(rand.nextDouble()>)
//            }
//        }
//    }

    public void setTraversable(boolean traversable) {
        this.traversable = traversable;
    }

    public void setTraversable(double probability) {
        double prob = new Random().nextDouble();
        if (prob > probability) {
            this.traversable = false;
            System.out.println("Node " + this + "is not traversable prob = " + prob);
        }
    }


    MapNode(int posX, int posY) {
        this.neighbours = new LinkedList<>();
        this.posX = posX;
        this.posY = posY;
        this.traversable = true;
        this.fCost = 0;
        this.parent = null;
    }

    MapNode(MapNode m){
        this.neighbours=new LinkedList<>(m.getNeighbours());
        this.posX=m.posX;
        this.posY=m.posY;
        this.fCost=m.fCost;
        this.hCost=m.hCost;
        this.parent = null;
        this.traversable=m.traversable;

    }


    int distance(MapNode n1, MapNode n2) {
        return (int) (Math.sqrt((int) (Math.pow(n1.getPosX() - n2.getPosX(), 2) + Math.pow(n1.getPosY() - n2.getPosY(), 2))) * 10);
    }


    public boolean addNeighbour(MapNode node) {
        return neighbours.add(node);
    }


    @Override
    public String toString() {
        return String.format("[%d,%d]", posX, posY);
    }

    public boolean equals(MapNode m) {
        return this.posX==m.posX&&this.posY==m.posY;
    }

    public LinkedList<MapNode> getNeighbours() {
        return neighbours;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public MapNode getParent() {
        return parent;
    }

    public void setParent(MapNode parent) {
        this.parent = parent;
    }

    public int getFcost() {
        return fCost;
    }

    public void setFcost(int fCost) {
        this.fCost = fCost;
    }

    public void setFcost(MapNode start, MapNode end, MapNode n) {
        this.fCost = distance(start, n) + distance(n, end);
    }

    public void setFcost(MapNode start, MapNode end) {
        this.fCost = distance(start, this) + distance(this, end);
    }

    public void setHcost(MapNode end) {
        this.hCost = distance(this, end);
    }

    public int getHcost() {
        return hCost;
    }

}
