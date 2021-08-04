package net.pathfinding.me.PathFinding;


import net.pathfinding.me.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class AStar {

    private Node begin;

    private Node goal;

    final private Main plugin;

    private Material mat=Material.GLASS;

    public AStar(Main pl){
        this.plugin = pl;
    }

    private void computeNodes(Location start, Location end){
        this.begin = new Node(start, 0);
        this.goal = new Node(end, 0);
    }

    public void computePath(Location start, Location end){
        computeNodes(start, end);

        PriorityQueue<Node> openList = new PriorityQueue<>();
        PriorityQueue<Node> closedList = new PriorityQueue<>();

        Set<Location> oLocation = new HashSet<>();
        Set<Location> cLocation = new HashSet<>();

        begin.totalCost = begin.cost + begin.calcHeuristics(goal);

        begin.estimatedCost = begin.calcHeuristics(goal);

        openList.add(begin);

        oLocation.add(begin.loc.getBlock().getLocation());

        final Node[] listnode = {null};

        new BukkitRunnable(){

            @Override
            public void run() {
                Node n = openList.peek();

                n.getBlocksAround(goal);

                openList.remove(n);
                closedList.add(n);

                oLocation.remove(n.loc.getBlock().getLocation());
                cLocation.add(n.loc.getBlock().getLocation());

                for(Node m : n.neighbors){
                    double totalWeight = n.cost + n.calcHeuristics(m);
                    if(!oLocation.contains(m.loc.getBlock().getLocation()) && !cLocation.contains(m.loc.getBlock().getLocation())){
                        m.parent = n;
                        m.cost = totalWeight;
                        //The *1.5 is to add weight to the distance, optimizes algorithm.
                        m.totalCost = m.cost + 1.5*m.calcHeuristics(goal);

                        openList.add(m);
                        oLocation.add(m.loc.getBlock().getLocation());
                    } else {
                        if(totalWeight < m.cost){
                            m.parent = n;
                            m.cost = totalWeight;
                            m.totalCost = m.cost + 1.5*m.calcHeuristics(goal);
                            if(closedList.contains(m)){
                                closedList.remove(m);
                                openList.add(m);

                                cLocation.remove(m.loc.getBlock().getLocation());
                                oLocation.add(m.loc.getBlock().getLocation());
                            }
                        }
                    }
                }
                if(n.loc.getWorld().getBlockAt(n.loc).getLocation().equals(goal.loc.getWorld().getBlockAt(goal.loc).getLocation())){
                    cancel();
                    listnode[0] = n;
                    displayPath(listnode[0]);
                }
            }
        }.runTaskTimer(plugin, 0, 0);

    }

    private void displayPath(Node n){
        while(n!=null){
            n.loc.clone().add(0,1,0).getBlock().setType(this.mat);
            n=n.parent;
        }
    }

    public void setMaterial(Material m){
        this.mat = m;
    }

}
