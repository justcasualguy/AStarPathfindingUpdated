package org.openjfx;

import org.openjfx.pathfinfing.Map;
import org.openjfx.pathfinfing.MapNode;

import java.util.LinkedList;
import java.util.Vector;

public class Test {
    static public int elemId(int x,int y,int size){
        return y*size+x;
    }
    public static void main(String[] args) {
        int size=20;
        Map map = new Map(size);

        int startX=5;
        int startY=5;
        int endX=3;
        int endY=3;
        MapNode start = map.getNodes().get(elemId(startX,startY,size));
        MapNode end = map.getNodes().get(elemId(endX,endY,size));


        for(MapNode m:map.getNodes())
            map.setNodeNeighbours(m);

        LinkedList<MapNode> result = map.findPath(start,end);


        System.out.println(String.format("Start:[%d,%d] End:[%d,%d]",startX,startY,endX,endY));

        System.out.println("Shortest path:");
        System.out.println(result);
        System.out.println("Walked path: "+map.findPathParameterized(start,end,0.7));

    }
}
