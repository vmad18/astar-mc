package net.pathfinding.me.PathFinding;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node>{

    public Node parent = null;

    public List<Node> neighbors = new ArrayList<>();;

    public double totalCost = Double.MAX_VALUE;
    public double cost;
    public double estimatedCost;

    public Location loc;

    public Node(Location l, double c){
        this.loc = l;
        this.cost = c;
    }

    @Override
    public int compareTo(Node n) {
        return Double.compare(this.totalCost,n.totalCost);
    }

    //Similar to BFS
    public void getBlocksAround(Node goal){
        List<Location> locations = new ArrayList<>();

        Location loc1 = this.loc.clone().add(1,0,0);
        Location loc2 = this.loc.clone().add(1,1,0);
        Location loc3 = this.loc.clone().add(1,-1,0);

        Location loc4 = this.loc.clone().add(-1,0,0);
        Location loc5 = this.loc.clone().add(-1,1,0);
        Location loc6 = this.loc.clone().add(-1,-1,0);

        Location loc7 = this.loc.clone().add(0,0,1);
        Location loc8 = this.loc.clone().add(0,1,1);
        Location loc9 = this.loc.clone().add(0,-1,1);

        Location loc10 = this.loc.clone().add(0,0,-1);
        Location loc11 = this.loc.clone().add(0,1,-1);
        Location loc12 = this.loc.clone().add(0,-1,-1);


        Location loc13 = this.loc.clone().add(1,0,1);
        Location loc14 = this.loc.clone().add(1,1,1);
        Location loc15 = this.loc.clone().add(1,-1,1);

        Location loc16 = this.loc.clone().add(-1,0,-1);
        Location loc17 = this.loc.clone().add(-1,1,-1);
        Location loc18 = this.loc.clone().add(-1,-1,-1);

        Location loc19 = this.loc.clone().add(-1,0,1);
        Location loc20 = this.loc.clone().add(-1,1,1);
        Location loc21 = this.loc.clone().add(-1,-1,1);

        Location loc22 = this.loc.clone().add(1,0,-1);
        Location loc23 = this.loc.clone().add(1,1,-1);
        Location loc24 = this.loc.clone().add(1,-1,-1);

        locations.add(loc1);
        locations.add(loc2);
        locations.add(loc3);
        locations.add(loc4);
        locations.add(loc5);
        locations.add(loc6);
        locations.add(loc7);
        locations.add(loc8);
        locations.add(loc9);
        locations.add(loc10);
        locations.add(loc11);
        locations.add(loc12);
        locations.add(loc13);
        locations.add(loc14);
        locations.add(loc15);
        locations.add(loc16);
        locations.add(loc17);
        locations.add(loc18);
        locations.add(loc19);
        locations.add(loc20);
        locations.add(loc21);
        locations.add(loc22);
        locations.add(loc23);
        locations.add(loc24);

        for(Location l:locations){
            if(isStandable(l) && isMoveable(l)){
                Node o = new Node(l, cost+1.5);
                //The *1.5 is to add weight to the distance, optimizes algorithm.
                o.totalCost = 1.5*o.calcHeuristics(goal) + o.cost;
                o.estimatedCost = o.calcHeuristics(goal);
                neighbors.add(o);
            }
        }
    }

    public boolean isStandable(Location l){
        return l.getBlock().getType().isSolid();
    }

    public boolean isMoveable(Location l){
        return l.clone().add(0, 1, 0).getBlock().isPassable() && l.clone().add(0, 2,0).getBlock().isPassable();
    }


    public double calcHeuristics(Node goal){
        return this.loc.distance(goal.loc);
    }

}