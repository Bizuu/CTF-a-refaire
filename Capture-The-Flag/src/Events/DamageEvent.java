package Events;

import CaptureTheFlag.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEvent implements Listener {

    private Main main;

    public DamageEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
            Player p = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            if(main.isPlayerInGame(p) && main.isPlayerInGame(damager)){
                if(main.getRed().contains(p) && main.getRed().contains(damager)){
                    e.setCancelled(true);
                }
                if(main.getBlue().contains(p) && main.getBlue().contains(damager)){
                    e.setCancelled(true);
                }

                if(e.getDamage() > p.getHealth()){
                    main.killPlayer(p);
                    main.addPoints(damager);
                }

            }
        }
    }

}
