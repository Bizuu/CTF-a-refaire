package Events;

import CaptureTheFlag.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Collection;

public class ChatEvent implements Listener {

    private Main main;

    public ChatEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.getMessage().startsWith("!")) {
            if (main.getBlue().contains(e.getPlayer())) {
                e.setCancelled(true);
                for (Player p : main.getBlue()) {
                    p.sendMessage("§1[" + e.getPlayer().getName() + "]§r " + e.getMessage());
                }
            }
            if (main.getRed().contains(e.getPlayer())) {
                e.setCancelled(true);
                for (Player p : main.getRed()) {
                    p.sendMessage("§c[" + e.getPlayer().getName() + "]§r " + e.getMessage());
                }
            }
        } else if (e.getPlayer().getWorld() == main.getConfig().getLocation("Spawn").getWorld()) {
            Collection<? extends Player> collec = Bukkit.getOnlinePlayers();
            for (Player p : collec) {
                Location loc = main.getConfig().getLocation("Spawn");
                if (p.getWorld() == loc.getWorld()) {
                    p.sendMessage();
                }
            }
        }
    }
}
