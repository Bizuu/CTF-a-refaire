package CaptureTheFlag;

import Events.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main extends JavaPlugin {

    private int bluePoints = 0;
    private int redPoints = 0;
    private Gstate state;
    private ArrayList<Player> playersInGame;
    private ArrayList<Player> blue;
    private ArrayList<Player> red;
    private String prefix = "§7[§1§lCapture§f§lThe§4§lFlag§7]";
    private GTask task = new GTask(this);
    private HashMap<Player,Integer> playersKills;
    private HashMap<Player,Integer> points;
    private HashMap<Player, Integer> playersDeath;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        playersInGame = new ArrayList<>();
        blue = new ArrayList<>();
        red = new ArrayList<>();
        playersKills = new HashMap<>();
        points = new HashMap<>();
        playersDeath = new HashMap<>();

        this.setState(Gstate.Starting);

        this.getCommand("ctf").setExecutor(new Commands(this));
        this.getServer().getPluginManager().registerEvents(new DamageEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new MoveEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new SignEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new QuitEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new InventoryClickEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new InteractEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new DropEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new ChatEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreakEvent(this), this);

        }

    public ArrayList<Player> getBlue(){
        return this.blue;
    }

    public ArrayList<Player> getRed(){
        return this.red;
    }

    public ArrayList<Player> getPlayersInGame(){
        return this.playersInGame;
    }
    public boolean isPlayerInGame(Player p){
        return this.playersInGame.contains(p);
    }

    public double calculRatio(Player p){
        return this.getPlayersKills(p) / this.getPlayersDeath(p);
    }


    public void addPlayerInGame(Player p){
        this.playersInGame.add(p);
        Location loc = this.getConfig().getLocation("Spawn");
        p.teleport(loc);
        if(GTask.TimerStarted() == true){
            this.playersDeath.put(p,0);
            this.playersKills.put(p,0);
            this.points.put(p,0);
            return;
        }
        GTask t = new GTask(this);
        t.runTaskTimer(this, 0, 20);
        this.playersDeath.put(p,0);
        this.playersKills.put(p,0);
        this.points.put(p,0);
    }

    public String getPrefix(){
        return this.prefix;
    }

    public InventoryView shop(Player p){
        Inventory inv = Bukkit.createInventory(null,9,"§6§l§nShop");
        inv.setItem(0,this.createItem(Material.GOLDEN_APPLE,"§e§lPomme en or","§7> §fCoût: 2pts"));
        inv.setItem(1,this.createItem(Material.POTION,"§e§lPotion de régénération","§7> §fCoût: 5pts"));
        inv.setItem(2,this.createItem(Material.DIAMOND_SWORD,"§e&§Epee en diamant tranchant 2","§7> §fCoût: 7pts"));
        inv.setItem(3,this.createItem(Material.SPLASH_POTION,"§e§lPotion d'invisibilité","§7> §fCoût: 7pts"));
        inv.setItem(4,this.createItem(Material.BOW,"§e§lArc puissance 5","§7> §fCoût: 10pts"));
        inv.setItem(5,this.createItem(Material.ENCHANTED_GOLDEN_APPLE,"§e§lPomme de Notch","§7> §fCoût: 12pts"));
        return p.openInventory(inv);
    }

    public void setState(Gstate state){
        this.state = state;
    }

    public boolean isState(Gstate state) {
        return this.state == state;
    }


    public int getRedPoints() {
        return redPoints;
    }

    public void addRedPoints(){
        this.redPoints++;
    }

    public int getBluePoints() {
        return bluePoints;
    }

    public void addBluePoints() {
        this.bluePoints++;
    }

    public int getPlayersKills(Player p) {
        return playersKills.get(p);
    }

    public void killPlayer(Player p){
        p.setGameMode(GameMode.SPECTATOR);
        p.sendTitle("Vous êtes mort !","Réapparition dans 5 secondes...");
        new BukkitRunnable(){
            @Override
            public void run() {
                if(getRed().contains(p)){
                    p.setGameMode(GameMode.SURVIVAL);
                    p.teleport(getConfig().getLocation("Teams.red.world.locationofspawn"));
                    p.getInventory().clear();
                    p.getInventory().setHelmet(task.createArmorRed(Material.LEATHER_HELMET));
                    p.getInventory().setChestplate(task.createArmorRed(Material.LEATHER_CHESTPLATE));
                    p.getInventory().setLeggings(task.createArmorRed(Material.LEATHER_LEGGINGS));
                    p.getInventory().setBoots(task.createArmorRed(Material.LEATHER_BOOTS));
                    ItemStack sword = new ItemStack(Material.STONE_SWORD);
                    p.getInventory().setItem(0, sword);
                }

                if(getBlue().contains(p)){
                    p.teleport(getConfig().getLocation("Teams.blue.world.locationofspawn"));
                    p.setGameMode(GameMode.SURVIVAL);
                    p.getInventory().clear();
                    p.getInventory().setHelmet(task.createArmorBlue(Material.LEATHER_HELMET));
                    p.getInventory().setChestplate(task.createArmorBlue(Material.LEATHER_CHESTPLATE));
                    p.getInventory().setLeggings(task.createArmorBlue(Material.LEATHER_LEGGINGS));
                    p.getInventory().setBoots(task.createArmorBlue(Material.LEATHER_BOOTS));
                    ItemStack sword = new ItemStack(Material.STONE_SWORD);
                    p.getInventory().setItem(0, sword);
                    p.setGameMode(GameMode.SURVIVAL);
                }
            }
        }.runTaskLater(this,5 * 20);
    }

    public void addPoints(Player p){
        this.points.put(p, this.getPoints(p) + 1);
    }

    public int getPoints(Player p) {
        return points.get(p);
    }

    public int getPlayersDeath(Player p) {
        return playersDeath.get(p);
    }

    public void finDeGame() {
        this.bluePoints = 0;
        this.redPoints = 0;
        this.playersDeath.clear();
        this.playersKills.clear();
    }

    public void setPlayerPoints(Player p,int i){
        this.points.put(p,i);
    }

    private ItemStack createItem(Material m, String s,String lore){
        ItemStack it = new ItemStack(m);
        ItemMeta meta = it.getItemMeta();
        meta.setDisplayName(s);
        meta.setLore(Arrays.asList(lore));
        it.setItemMeta(meta);
        return it;
    }
}
