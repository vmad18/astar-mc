package net.pathfinding.me.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerSelectPoint {

    private Location loc1=null;

    private Location loc2=null;

    private Player player;

    public PlayerSelectPoint(Player p){
        this.player = p;
    }

    public void setLoc1(Location l){
        this.loc1 = l;
    }

    public void setLoc2(Location l){
        this.loc2 = l;
    }

    public Location getLoc1(){
        return this.loc1;
    }

    public Location getLoc2(){
        return this.loc2;
    }

}
