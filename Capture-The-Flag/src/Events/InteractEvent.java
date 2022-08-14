package Events;

import CaptureTheFlag.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEvent implements Listener {

    private Main main;

    public InteractEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getLocation().equals(main.getConfig().getLocation("Teams.Signs.main"))) {
                if (main.isPlayerInGame(e.getPlayer())) {
                    e.getPlayer().sendMessage("Erreur : tu es déjà dans une partie ! Déco reco");
                    return;
                }
                if (main.getConfig().getInt("Max") >= main.getPlayersInGame().size()) {
                    main.addPlayerInGame(e.getPlayer());
                }
                else {
                    e.getPlayer().sendMessage(main.getPrefix() + " Erreur : trop de monde dans la partie");
                }
            }
        }

    }

}
