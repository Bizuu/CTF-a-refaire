package Events;

import CaptureTheFlag.Main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InventoryClickEvent implements Listener {


    private Main main;

    public InventoryClickEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(e.getView().getTitle().equals("§6§l§nShop")){
            switch(e.getCurrentItem().getType()){
                case GOLDEN_APPLE:
                    if(main.getPoints(p) >= 2){
                        main.setPlayerPoints(p,main.getPoints(p) - 2);
                        p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                        p.closeInventory();
                    }
                    else {
                        e.getWhoClicked().closeInventory();
                        e.getWhoClicked().sendMessage(main.getPrefix() + " Erreur : pas assez de points.");
                    }
                    break;
                case POTION:
                    if(main.getPoints(p) >= 5){
                        main.setPlayerPoints(p,main.getPoints(p) - 5);
                        ItemStack it = new ItemStack(Material.POTION);
                        PotionMeta meta = (PotionMeta) it.getItemMeta();
                        meta.setMainEffect(PotionEffectType.REGENERATION);
                        PotionEffect invisibility = new PotionEffect(PotionEffectType.REGENERATION, 20*10, 1);
                        meta.addCustomEffect(invisibility,true);
                        it.setItemMeta(meta);
                        p.getInventory().addItem(it);
                        p.closeInventory();
                    }
                    else {
                        e.getWhoClicked().closeInventory();
                        e.getWhoClicked().sendMessage(main.getPrefix() + " Erreur : pas assez de points.");
                    }
                    break;
                case DIAMOND_SWORD:
                    if(main.getPoints(p) >= 7){
                        main.setPlayerPoints(p,main.getPoints(p) - 7);
                        ItemStack it = new ItemStack(Material.DIAMOND_SWORD);
                        ItemMeta meta = it.getItemMeta();
                        meta.setDisplayName("§cCouteau de chocapic");
                        meta.addEnchant(Enchantment.DAMAGE_ALL,1,true);
                        it.setItemMeta(meta);
                        p.getInventory().addItem(it);
                        p.closeInventory();
                    }
                    else {
                        e.getWhoClicked().closeInventory();
                        e.getWhoClicked().sendMessage(main.getPrefix() + " Erreur : pas assez de points.");
                    }
                    break;
                case SPLASH_POTION:
                    if(main.getPoints(p) >= 7){
                        main.setPlayerPoints(p,main.getPoints(p) - 7);
                        ItemStack it = new ItemStack(Material.POTION);
                        PotionMeta meta = (PotionMeta) it.getItemMeta();
                        meta.setMainEffect(PotionEffectType.INVISIBILITY);
                        PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, Integer.MAX_VALUE);
                        meta.addCustomEffect(invisibility,true);
                        it.setItemMeta(meta);
                        p.getInventory().addItem(it);
                        p.closeInventory();
                    }
                    else {
                        e.getWhoClicked().closeInventory();
                        e.getWhoClicked().sendMessage(main.getPrefix() + " Erreur : pas assez de points." + "");
                    }
                    break;
                case BOW:
                    if(main.getPoints(p) >= 10){
                        main.setPlayerPoints(p,main.getPoints(p) - 10);
                        ItemStack it = new ItemStack(Material.BOW);
                        ItemMeta meta = it.getItemMeta();
                        meta.addEnchant(Enchantment.ARROW_DAMAGE,9,true);
                        it.setItemMeta(meta);
                        p.getInventory().addItem(it);
                        p.closeInventory();
                    }
                    else {
                        e.getWhoClicked().closeInventory();
                        e.getWhoClicked().sendMessage(main.getPrefix() + " Erreur : pas assez de points.");
                    }
                    break;
                case ENCHANTED_GOLDEN_APPLE:
                    if(main.getPoints(p) >= 12){
                        main.setPlayerPoints(p,main.getPoints(p) - 12);
                        p.getInventory().addItem(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
                        p.closeInventory();
                    }
                    else {
                        e.getWhoClicked().closeInventory();
                        e.getWhoClicked().sendMessage(main.getPrefix() + " Erreur : pas assez de points.");
                        p.closeInventory();
                    }
                    break;
            }
        }
    }

}
