package Events;

import CaptureTheFlag.Gstate;
import CaptureTheFlag.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;

public class MoveEvent implements Listener {

    private Main main;

    public MoveEvent(Main main) {
        this.main = main;
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (main.isState(Gstate.Pvp)) {
            if (e.getPlayer().getWorld().getName().equals(main.getConfig().getLocation("Spawn").getWorld().getName())) {

                if (e.getPlayer().getInventory().getHelmet() != null && e.getPlayer().getInventory().getHelmet().getType().equals(Material.RED_BANNER)) {


                    Location loc1 = main.getConfig().getLocation("Teams.blue.world.locationofbanner");


                    if (e.getPlayer().getLocation().getBlockX() == loc1.getBlockX() && e.getPlayer().getLocation().getBlockY() == loc1.getBlockY() && e.getPlayer().getLocation().getBlockZ() == loc1.getBlockZ()) {


                        ItemStack it = new ItemStack(Material.LEATHER_HELMET);
                        ItemMeta meta = it.getItemMeta();
                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                        meta.setUnbreakable(true);
                        it.setItemMeta(meta);
                        e.getPlayer().getInventory().setHelmet(it);
                        Collection<? extends Player> collec = Bukkit.getOnlinePlayers();
                        for (Player p : collec) {
                            if (p.getWorld() == main.getConfig().getLocation("Spawn").getWorld()) {
                                p.sendMessage(main.getPrefix() + " Un point a été marqué par l'équipe §1Bleu §r!");
                            }
                        }
                        Location loc = main.getConfig().getLocation("Teams.red.world.locationofbanner");
                        assert loc != null;
                        Block bloc = loc.getBlock();
                        bloc.setType(Material.RED_BANNER);
                        main.addBluePoints();
                        main.addPoints(e.getPlayer());
                        main.addPoints(e.getPlayer());
                        main.addPoints(e.getPlayer());
                    }
                }

                if (e.getPlayer().getInventory().getHelmet() != null && e.getPlayer().getInventory().getHelmet().getType().equals(Material.BLUE_BANNER)) {

                    Location loc1 = main.getConfig().getLocation("Teams.red.world.locationofbanner");


                    if (e.getPlayer().getLocation().getBlockX() == loc1.getBlockX() && e.getPlayer().getLocation().getBlockY() == loc1.getBlockY() && e.getPlayer().getLocation().getBlockZ() == loc1.getBlockZ()) {


                        ItemStack it = new ItemStack(Material.LEATHER_HELMET);
                        ItemMeta meta = it.getItemMeta();
                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                        meta.setUnbreakable(true);
                        it.setItemMeta(meta);
                        e.getPlayer().getInventory().setHelmet(it);
                        Collection<? extends Player> collec = Bukkit.getOnlinePlayers();
                        for (Player p : collec) {
                            if (p.getWorld() == main.getConfig().getLocation("Spawn").getWorld()) {
                                p.sendMessage(main.getPrefix() + " Un point a été marqué par l'équipe §cRouge §r!");
                            }
                        }
                        Location loc = main.getConfig().getLocation("Teams.blue.world.locationofbanner");
                        assert loc != null;
                        Block bloc = loc.getBlock();
                        bloc.setType(Material.BLUE_BANNER);
                        main.addRedPoints();
                        main.addPoints(e.getPlayer());
                        main.addPoints(e.getPlayer());
                        main.addPoints(e.getPlayer());
                        e.setCancelled(true);


                    }
                }


            }
        }
    }
}
