package org.openjfx.pathfinfing;

import java.util.*;

public class Map  {

    private Vector<MapNode> nodes;
    private int size;
    List<MapNode> path;

    public Map(int size) {
        nodes = new Vector<>(size * size);
        this.size = size;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                nodes.add(new MapNode(j, i));
            }
        }


    }

    int findElementId(int posX, int posY) {
        return posY * size + posX;
    }

    MapNode neighbourRight(MapNode node) {
        return nodes.get(findElementId(node.getPosX() + 1, node.getPosY()));
    }

    MapNode neighbourLeft(MapNode node) {
        return nodes.get(findElementId(node.getPosX() - 1, node.getPosY()));
    }

    MapNode neighbourUpper(MapNode node) {
        return nodes.get(findElementId(node.getPosX(), node.getPosY() + 1));
    }

    MapNode neighbourLower(MapNode node) {
        return nodes.get(findElementId(node.getPosX(), node.getPosY() - 1));
    }

    int distance(MapNode n1, MapNode n2) {
        return (int) Math.sqrt((int) (Math.pow(n1.getPosX() - n2.getPosX(), 2) + Math.pow(n1.getPosY() - n2.getPosY(), 2))) * 10;
    }

    public void setNodeNeighbours(MapNode node) {
        int x = node.getPosX();
        int y = node.getPosY();
        if (node == null)
            return;

        if (x == 0) {
            if (y == 0) {
                node.addNeighbour(neighbourRight(node));
                node.addNeighbour(neighbourUpper(node));
            } else if (y == size - 1) {
                node.addNeighbour(neighbourRight(node));
                node.addNeighbour(neighbourLower(node));
            } else {

                node.addNeighbour(neighbourRight(node));
                node.addNeighbour(neighbourLower(node));
                node.addNeighbour(neighbourUpper(node));
            }
        } else if (x == size - 1) {
            if (y == size - 1) {
                node.addNeighbour(neighbourLeft(node));
                node.addNeighbour(neighbourLower(node));
            } else if (y == 0) {
                node.addNeighbour(neighbourLeft(node));
                node.addNeighbour(neighbourUpper(node));
            } else {

                node.addNeighbour(neighbourLeft(node));
                node.addNeighbour(neighbourLower(node));
                node.addNeighbour(neighbourUpper(node));
            }

        } else if (y == 0) {
            if (x == size - 1) {
                node.addNeighbour(neighbourLeft(node));
                node.addNeighbour(neighbourUpper(node));
            } else if (x == 0) ;
            else {
                node.addNeighbour(neighbourRight(node));
                node.addNeighbour(neighbourLeft(node));
                node.addNeighbour(neighbourUpper(node));
            }
        } else if (y == size - 1) {
            if (x > 0 && x < size - 1) {
                node.addNeighbour(neighbourRight(node));
                node.addNeighbour(neighbourLeft(node));
                node.addNeighbour(neighbourLower(node));
            }
        } else {
            node.addNeighbour(neighbourRight(node));
            node.addNeighbour(neighbourLeft(node));
            node.addNeighbour(neighbourLower(node));
            node.addNeighbour(neighbourUpper(node));
        }

        //System.out.println(node+" neighbours: "+node.getNeighbours());
    }

    public Vector<MapNode> getNodes() {
        return nodes;
    }

    public void evaluateNeighbours(MapNode start, MapNode end, MapNode node) {
        for (MapNode n : node.getNeighbours()) {
            node.setFcost(start, end, node);
        }
    }


    public LinkedList<MapNode> findPath(MapNode start, MapNode end) {
        MapNode currentNode = null;
        LinkedList<MapNode> open = new LinkedList<>();
        LinkedList<MapNode> closed = new LinkedList<>();
        LinkedList<MapNode> path = new LinkedList<>();
        LinkedList<MapNode> shortestPath = new LinkedList<>();
        MapNode startCopy = new MapNode(start);


        Vector<LinkedList<MapNode>> paths = new Vector<LinkedList<MapNode>>();
        String s = "";
        currentNode = startCopy;
        open.add(startCopy);

        if(start.equals(end))
            shortestPath.add(end);


        while (!currentNode.equals(end)) {

            if (open.isEmpty())
                return null;

            currentNode = open.
                    stream().
                    min(Comparator.comparing(MapNode::getFcost)).
                    orElseThrow(NoSuchElementException::new);


            path.add(currentNode);
            open.remove(currentNode);
            closed.add(currentNode);
            if (currentNode == end) {
                shortestPath.add(end);
                while (currentNode.getParent() != null) {
                    shortestPath.add(currentNode.getParent());
                    currentNode = currentNode.getParent();
                }
                break;

            }
            for (MapNode neighbour : currentNode.getNeighbours()) {
                // neighbour.setTraversable(0.7);
                if (!neighbour.isTraversable() || closed.contains(neighbour))
                    continue;
                if (!open.contains(neighbour)) {
                    open.add(neighbour);
                    neighbour.setParent(currentNode);

                    neighbour.setFcost(startCopy, end);
                    neighbour.setHcost(end);
                    //Collections.sort(open,Comparator.comparing(MapNode::getHcost));
                } else {
                    if (distance(currentNode, startCopy) > distance(neighbour, startCopy))
                        neighbour.setFcost(startCopy, end);

                }
            }
        }

        Collections.reverse(shortestPath);
        paths.add(path);
        paths.add(shortestPath);
        return new LinkedList<MapNode>(shortestPath);

    }

   public LinkedList<MapNode> findPathParameterized(MapNode start,MapNode end,double blockProb){
        Random rand= new Random();
        LinkedList<MapNode> shortestPath = null;
        LinkedList<MapNode> newShortestPath = new LinkedList<>();
        LinkedList<MapNode> walkedPath = new LinkedList<>();
        MapNode startCopy = new MapNode(start);
        boolean added = false;
        MapNode current = start;
        MapNode parent=null;

        if(start.equals(end)) {
            newShortestPath.add(end);
            return newShortestPath;
        }
        newShortestPath.add(current);
       current=newShortestPath.getLast();
       walkedPath.add(current);
       //w kazdym przejsciu petli dodaje node'a do newShortestPath
        do{
            added = false;


            System.out.println("Current node: "+current);
            parent=current.getParent();


            shortestPath=findPath(current,end);
            System.out.println("Old path: "+shortestPath);
            //jesli warunek spleniony blokuje jedna ze sciezek
            while(!added) {
                double r = rand.nextDouble();
                System.out.println("Found new path: "+shortestPath);
                shortestPath = findPath(current, end);
                if(shortestPath==null){
                    for (MapNode n : current.getNeighbours())
                        if (n != n.getParent())
                            n.setTraversable(true);
                    shortestPath=findPath(current,end);
                }


                if (blockProb > r) {
                    System.out.println("Block prob:  " + blockProb + " Rand val: " + r);
                    System.out.println("Blocked" + shortestPath.get(1));
                    walkedPath.add(shortestPath.get(1));
                    walkedPath.add(current);
                    shortestPath.get(1).setTraversable(false);
                    if (!current.areNeighboursTraversable()) {
                        System.out.println("All neighbours blocked. Unblocking: "+current.getNeighbours()+" except parent: "+current.getParent());
                        for (MapNode n : current.getNeighbours())
                            if (n != n.getParent())
                                n.setTraversable(true);
                    }
                }
                else {
                        System.out.println("Taking path to" + shortestPath.get(1));
                        current.getParent().setTraversable(false);
                        newShortestPath.add(shortestPath.get(1));
                        current=newShortestPath.getLast();
                        walkedPath.add(current);
                        added = true;
                    }
                }
                shortestPath = findPath(newShortestPath.getLast(), end);
                if(current==end)
                    System.out.println("Done");
                else
                    System.out.println("Walked path: : " + walkedPath);
                    System.out.println("New path: " + shortestPath);
                System.out.println("+++++++++++++++++++");
            }while(current!=end);

        System.out.println("NewShortestPath:"+newShortestPath);
        return walkedPath;
    }

    void showInfo() {
        for (MapNode item : nodes) {
            System.out.println(item);
        }
    }
}
