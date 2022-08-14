package Events;

import CaptureTheFlag.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class BlockBreakEvent implements Listener {

    private Main main;

    public BlockBreakEvent(Main main) {
        this.main = main;
    }


    @EventHandler
    public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent e) {
        if (e.getBlock().getType().equals(Material.BLUE_BANNER)){

            if(main.getBlue().contains(e.getPlayer())){



                e.getPlayer().sendMessage(main.getPrefix() + " Vous ne pouvez pas voler votre propre drapeau.");
                e.setCancelled(true);
                return;
            }


            if (main.getRed().contains(e.getPlayer())){
                for(Player p : main.getPlayersInGame()){
                    p.sendMessage(main.getPrefix() + "§cLe joueur " + e.getPlayer().getName() + " a volé le drapeau bleu !");
                }
                e.getBlock().setType(Material.AIR);
                e.getPlayer().getInventory().setHelmet(new ItemStack(Material.BLUE_BANNER));
            }

        }


        if (e.getBlock().getType().equals(Material.RED_BANNER)){

            if (main.getRed().contains(e.getPlayer())) {

                e.getPlayer().sendMessage(main.getPrefix() + " Vous ne pouvez pas voler votre propre drapeau.");
                e.setCancelled(true);
                return;
            }
        }


        if (main.getBlue().contains(e.getPlayer())) {
            for(Player p : main.getPlayersInGame()){
                p.sendMessage(main.getPrefix() + "§1Le joueur " + e.getPlayer().getName() + " a volé le drapeau rouge !");

            }
            e.getBlock().setType(Material.AIR);
            e.getPlayer().getInventory().setHelmet(new ItemStack(Material.RED_BANNER));
        }


    }
}
