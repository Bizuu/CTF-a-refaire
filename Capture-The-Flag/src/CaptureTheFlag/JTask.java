package CaptureTheFlag;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.Collection;

public class JTask extends BukkitRunnable {

    private Main main;
    private int timer = 65;

    JTask(Main main) {
        this.main = main;
    }

    @Override
    public void run() {

        if (timer == 30) {
            Collection<? extends Player> collec = Bukkit.getOnlinePlayers();
            for (Player p : collec) {
                Location loc = main.getConfig().getLocation("Spawn");
                if (p.getWorld() == loc.getWorld()) {
                    p.sendMessage(main.getPrefix() + " Il ne reste plus que " + timer + " secondes !");
                }
            }
        }

        if (timer == 5) {
            main.setState(Gstate.End);
            Collection<? extends Player> collec = Bukkit.getOnlinePlayers();
            for (Player p : collec) {
                Location loc = main.getConfig().getLocation("Spawn");
                if (p.getWorld() == loc.getWorld()) {

                    if(main.getBluePoints() == main.getRedPoints()){
                        p.sendTitle("§8Equipe gagnante : §f EGALITE","Points de l'équipe rouge : " + main.getRedPoints() + " Points de l'équipe bleue : " + main.getBluePoints());

                    }else{
                    if(main.getBluePoints() > main.getRedPoints()){
                        p.sendTitle("§8Equipe gagnante : §3 BLEU ","Points de l'équipe rouge : " + main.getRedPoints() + " Points de l'équipe bleue : " + main.getBluePoints());
                    }
                    if(main.getBluePoints() < main.getRedPoints()){
                        p.sendTitle("§8Equipe gagnante : §c ROUGE ","Points de l'équipe rouge : " + main.getRedPoints() + " Points de l'équipe bleue : " + main.getBluePoints());
                    }
                    }
                    p.sendMessage(main.getPrefix() + " Fin de la partie ! Vous serez téléportés dans quelques secondes...");
                    p.setGameMode(GameMode.SPECTATOR);
                    main.finDeGame();
                }
            }
        }

        if (timer == 0) {
            Collection<? extends Player> collec = Bukkit.getOnlinePlayers();
            for (Player p : collec) {
                Location loc = main.getConfig().getLocation("Spawn");
                if (p.getWorld() == loc.getWorld()) {
                    p.sendMessage(main.getPrefix() + " Téléportation...");
                    p.setGameMode(GameMode.SURVIVAL);
                    p.performCommand("spawn");
                    p.getInventory().clear();


                    main.getPlayersInGame().remove(p);
                    if (main.getRed().contains(p)) {
                        main.getRed().remove(p);
                    }
                    if (main.getBlue().contains(p)) {
                        main.getBlue().remove(p);
                    }
                }
            }
            main.setState(Gstate.Starting);
            cancel();
        }

        timer--;

        Collection<? extends Player> collec = Bukkit.getOnlinePlayers();
        for (Player p : collec) {
            Location loc = main.getConfig().getLocation("Spawn");
            if (loc.getWorld() == p.getWorld()) {
                if (main.isState(Gstate.Pvp)) {
                    if(timer > 5){
                        ScoreboardManager manager = Bukkit.getScoreboardManager();
                        Scoreboard board = manager.getNewScoreboard();
                        Objective objective = board.registerNewObjective("ScoreBoard", "dummy");
                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                        objective.setDisplayName("§7[§1§lC§f§lT§4§lF§7]");
                        Score scorelel2 = objective.getScore("§7Joueur(s) §r: §e " + Bukkit.getWorld(main.getConfig().getLocation("Spawn").getWorld().getName()).getPlayers().size() + " §r/§e " + main.getConfig().get("Max"));
                        scorelel2.setScore(9);

                        Score scorelel = objective.getScore( " §f▏ §3L'Equipe bleu a §1: " + main.getBluePoints() + " points" );
                        scorelel.setScore(1);

                        Score scorele3 = objective.getScore(" §f▏ §3Kill(s) §f: " + main.getPlayersKills(p));
                        scorele3.setScore(6);

                        Score score = objective.getScore("§3■ §fInformations §8»");
                        score.setScore(8);

                        Score scoreX = objective.getScore( " §f▏ §3Mort(s) §f: " + main.getPlayersDeath(p) );
                        scoreX.setScore(5);


                        if (main.getPlayersDeath(p) != 0 || main.getPlayersKills(p) != 0) {
                            Score scoreXXX = objective.getScore(" §f▏ §3" + main.calculRatio(p) +  " Ratio");
                            scoreXXX.setScore(4);
                        } else {
                            Score scoreXXX = objective.getScore(" §f▏ §30.0  Ratio");
                            scoreXXX.setScore(4);
                        }

                        Score score11 = objective.getScore(" §f▏ §3Temps : §b" + timer);
                        score11.setScore(10);
                        Score score12 = objective.getScore(" §f▏ §3Points : §e" + main.getPoints(p));
                        score12.setScore(7);
                        Score score1 = objective.getScore("§3■ §fPoints §8»");
                        score1.setScore(3);
                        Score score3 = objective.getScore(" §f▏ §3L'équipe rouge a : §c" + main.getRedPoints() + " points");
                        score3.setScore(2);
                        p.setScoreboard(board);
                    }
                    else{
                        ScoreboardManager manager = Bukkit.getScoreboardManager();
                        Scoreboard board = manager.getNewScoreboard();
                        Objective objective = board.registerNewObjective("ScoreBoard", "dummy");
                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                        objective.setDisplayName("");
                        p.setScoreboard(board);
                    }
                }
            }
        }
    }
}

