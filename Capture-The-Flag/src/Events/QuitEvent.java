package Events;

import CaptureTheFlag.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    private Main main;

    public QuitEvent(Main main){
        this.main = main;
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (main.isPlayerInGame(e.getPlayer())) {
            main.getPlayersInGame().remove(e.getPlayer());
        }
        if (main.getRed().contains(e.getPlayer())) {
            main.getRed().remove(e.getPlayer());
        }

        if (main.getBlue().contains(e.getPlayer())) {
            main.getBlue().remove(e.getPlayer());
        }
    }

}
