package net.pathfinding.me;

import net.pathfinding.me.PathFinding.AStar;
import net.pathfinding.me.Utils.PlayerSelectPoint;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin implements CommandExecutor {

    final private HashMap<Player, PlayerSelectPoint> pd = new HashMap<>();

    private AStar shortpath;

    @Override
    public void onEnable(){
        this.shortpath = new AStar(this);
    }

    @Override
    public void onDisable(){}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Unable to execute command(s).");
            return false;
        }

        Player p = (Player) sender;

        if(!(pd.containsKey(p))){
            pd.put(p, new PlayerSelectPoint(p));
        }

        if(label.equalsIgnoreCase("pos1")){
            pd.get(p).setLoc1(p.getLocation());
            return true;
        }else if(label.equalsIgnoreCase("pos2")){
            pd.get(p).setLoc2(p.getLocation());
            return true;
        }else if(label.equalsIgnoreCase("setmat")){
            if(args.length>0){
                switch(args[0]){
                    case("em"):
                        shortpath.setMaterial(Material.EMERALD_BLOCK);
                        break;
                    case("glass"):
                        shortpath.setMaterial(Material.GLASS);
                        break;
                    case("dia"):
                        shortpath.setMaterial(Material.DIAMOND_BLOCK);
                        break;
                    case("ol"):
                        shortpath.setMaterial(Material.OAK_LEAVES);
                        break;
                    default:
                        p.sendMessage(ChatColor.GOLD + "Block Types: em:Emerald Block, glass:Glass, dia:Diamond Block, ol:Oak Leaves");
                        break;
                }
            }else{
                p.sendMessage(ChatColor.RED + "Correct Command Usage: /setmat <block>");
            }
            return true;
        }else if(label.equalsIgnoreCase("calcpath")){
            if(pd.get(p).getLoc1()==null || pd.get(p).getLoc2()==null){
                p.sendMessage(ChatColor.RED + "You must set the start and end node.");
                return true;
            }
            shortpath.computePath(pd.get(p).getLoc1(), pd.get(p).getLoc1());
            return true;
        }

        return false;
    }
}
