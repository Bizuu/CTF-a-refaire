package Events;

import CaptureTheFlag.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropEvent implements Listener {

    private Main main;

    public DropEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e){
        if(e.getPlayer().getWorld() == main.getConfig().getLocation("Spawn").getWorld()){
            e.setCancelled(true);
        }
    }


}
