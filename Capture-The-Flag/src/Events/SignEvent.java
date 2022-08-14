package Events;

import CaptureTheFlag.Gstate;
import CaptureTheFlag.Main;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SignEvent implements Listener {
    private Main main;

    public SignEvent(Main main) {
        this.main = main;
    }



    public void setSign() {
        new BukkitRunnable(){


            Location loc = main.getConfig().getLocation("Teams.Signs.main");
            Sign sign = (Sign) loc.getBlock().getState();

            @Override
            public void run() {
                sign.setLine(2,"§ePartie :");

                if (main.isState(Gstate.Pvp)) {
                    sign.setLine(3,"en cours");
                }

                if (main.isState(Gstate.Starting)) {
                    sign.setLine(3,"en lancement");
                }

                if (main.isState(Gstate.End)) {
                    sign.setLine(3,"en fin");
                }

                sign.setLine(0,"§7[§1§lC§f§lT§4§lF§7]");
                sign.setLine(1,"§e" + main.getPlayersInGame().size() + "§r/§e" + main.getConfig().get("Max"));

                sign.update();
            }
        }.runTaskTimer(main,0,20);
    }




    @EventHandler
    public void onSignEdit(SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("[CTF]") && event.getPlayer().hasPermission("ctf_adm")) {
            main.getConfig().set("Teams.Signs.main", event.getBlock().getLocation());
            setSign();
        }
    }
}

