package CaptureTheFlag;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class GTask extends BukkitRunnable {

    private Main main;
    private int timer = 90;
    private static boolean isTimerStarted;
    private boolean enoughPlayers = false;


    public static boolean TimerStarted(){
        return isTimerStarted;
    }

    public GTask(Main main) {
        this.main = main;
    }

        @Override
        public void run(){

            if(timer == 90){
                isTimerStarted = true;
            }

            if (timer == 0) {
                Collection<? extends Player> collec = Bukkit.getOnlinePlayers();
                for (Player p : collec) {
                    Location loc = main.getConfig().getLocation("Spawn");
                    if (p.getWorld() == loc.getWorld()) {

                        if (main.getBlue().size() == main.getRed().size()) {
                            boolean b = new Random().nextBoolean();
                            if (b == true) {
                                main.getBlue().add(p);
                            }
                            if (b == false) {
                                main.getRed().add(p);
                            }
                        } else {

                            if (main.getRed().size() > main.getBlue().size()) {
                                main.getBlue().add(p);

                            }
                            if (main.getRed().size() < main.getBlue().size()) {
                                main.getRed().add(p);
                            }
                        }



                        if (main.getBlue().contains(p)) {
                            p.teleport(main.getConfig().getLocation("Teams.blue.world.locationofspawn"));
                        }

                        if (main.getRed().contains(p)) {
                            p.teleport(main.getConfig().getLocation("Teams.red.world.locationofspawn"));
                        }

                        Location block = main.getConfig().getLocation("Teams.red.world.locationofbanner");
                        Block bloc = block.getBlock();
                        bloc.setType(Material.RED_BANNER);

                        Location block2 = main.getConfig().getLocation("Teams.blue.world.locationofbanner");
                        Block bloc2 = block2.getBlock();
                        bloc2.setType(Material.BLUE_BANNER);

                        if (main.getBlue().contains(p)) {
                            p.getInventory().clear();
                            p.getInventory().setHelmet(this.createArmorBlue(Material.LEATHER_HELMET));
                            p.getInventory().setChestplate(this.createArmorBlue(Material.LEATHER_CHESTPLATE));
                            p.getInventory().setLeggings(this.createArmorBlue(Material.LEATHER_LEGGINGS));
                            p.getInventory().setBoots(this.createArmorBlue(Material.LEATHER_BOOTS));
                            ItemStack sword = new ItemStack(Material.STONE_SWORD);
                            p.getInventory().setItem(0, sword);
                        }
                        if (main.getRed().contains(p)) {
                            p.getInventory().clear();
                            p.getInventory().setHelmet(this.createArmorRed(Material.LEATHER_HELMET));
                            p.getInventory().setChestplate(this.createArmorRed(Material.LEATHER_CHESTPLATE));
                            p.getInventory().setLeggings(this.createArmorRed(Material.LEATHER_LEGGINGS));
                            p.getInventory().setBoots(this.createArmorRed(Material.LEATHER_BOOTS));
                            ItemStack sword = new ItemStack(Material.STONE_SWORD);
                            p.getInventory().setItem(0, sword);
                        }
                        p.sendMessage(main.getPrefix() + " Les règles sont simples : Cassez le drapeau adverse et retournez à votre base. L'équipe avec le plus de points gagne !");
                        p.sendMessage(main.getPrefix() + " Par chocapic_du_56 (Bizu#6802)");
                    }
                }
                isTimerStarted = false;
                main.setState(Gstate.Pvp);
                JTask t = new JTask(main);
                t.runTaskTimer(main, 0, 20);
                cancel();
            }

            Collection<? extends Player> collec = Bukkit.getOnlinePlayers();
            for (Player p : collec) {
                Location loc = main.getConfig().getLocation("Spawn");
                if (loc.getWorld() == p.getWorld()) {
                    if (main.isState(Gstate.Starting)) {
                        ScoreboardManager manager = Bukkit.getScoreboardManager();
                        Scoreboard board = manager.getNewScoreboard();
                        Objective objective = board.registerNewObjective("ScoreBoard", "dummy");
                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                        objective.setDisplayName("§7[§1§lC§f§lT§4§lF§7]");
                        Score scorelel2 = objective.getScore("§2Joueur(s) §r: §e " + main.getPlayersInGame().size() + " §r/§e " + main.getConfig().get("Max"));
                        scorelel2.setScore(3);
                        Score scorelel = objective.getScore("§dTemps restant : " + timer);
                        scorelel.setScore(2);

                        if(enoughPlayers == true){
                            Score scorele3 = objective.getScore(" §f▏ §3Lancement bientôt !");
                            scorele3.setScore(1);
                        }
                        else{
                            Score scorele3 = objective.getScore("§f▏ §3 En attente de joueurs...");
                            scorele3.setScore(1);
                        }

                        Score score = objective.getScore("§3■ §fInformations §8»");
                        score.setScore(5);
                        Score score2 = objective.getScore(" ");
                        score2.setScore(4);
                        p.setScoreboard(board);
                        if (timer <= 5) {
                            p.sendTitle("§6" + timer, "");
                        }
                    }
                }
            }

            int min = (int) main.getConfig().get("Min");
            int max = (int) main.getConfig().get("Max");

            if (main.getPlayersInGame().size() >= min ) {
                enoughPlayers = true;
                timer--;
                if(main.getPlayersInGame().size() == max){
                    timer = 0;
                }
            }
            else{
                enoughPlayers = false;
            }
        }

    public ItemStack createArmorRed(Material material){
        ItemStack it = new ItemStack(material);
        ItemMeta meta = it.getItemMeta();
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,3,true);
        meta.setLore(Arrays.asList("§cArmure rouge magique de chocapic_du_56"));
        it.setItemMeta(meta);
        LeatherArmorMeta leather = (LeatherArmorMeta) it.getItemMeta();
        leather.setColor(Color.RED);
        it.setItemMeta(leather);
        return it;
    }

    public ItemStack createArmorBlue(Material material){
        ItemStack it = new ItemStack(material);
        ItemMeta meta = it.getItemMeta();
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,3,true);
        meta.setLore(Arrays.asList("Armure bleue magique de chocapic_du_56"));
        it.setItemMeta(meta);
        LeatherArmorMeta leather = (LeatherArmorMeta) it.getItemMeta();
        leather.setColor(Color.BLUE);
        it.setItemMeta(leather);
        return it;
    }
}
