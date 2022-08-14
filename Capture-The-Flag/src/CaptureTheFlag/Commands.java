package CaptureTheFlag;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private Main main;


    public Commands(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;

            if (command.getName().equalsIgnoreCase("ctf")) {
                if (args[0].equalsIgnoreCase("admin")) {
                    if (p.hasPermission("perm_adm") || p.isOp()) {
                        if (args[1].equalsIgnoreCase("setmin")) {
                            int i = Integer.parseInt(args[2]);
                            main.getConfig().set("Min", i);
                            main.saveConfig();
                        }
                        if (args[1].equalsIgnoreCase("setmax")) {
                            int i = Integer.parseInt(args[2]);
                            main.getConfig().set("Max", i);
                            main.saveConfig();
                        }
                        if (args[1].equalsIgnoreCase("pause")) {
                            main.setState(Gstate.Pause);
                            if (main.getPlayersInGame().size() != 0) {
                                for (int i = 0; i < main.getPlayersInGame().size(); i++) {
                                    Player pInGame = main.getPlayersInGame().get(i);
                                    pInGame.performCommand("spawn");
                                    main.getPlayersInGame().remove(pInGame);
                                }
                                p.sendMessage("§7[§1§lCapture§f§lThe§4§lFlag§7]§3 partie mise en pause");
                            }
                        }
                        if (args[1].equalsIgnoreCase("setspawn")) {
                            if(args[2].equalsIgnoreCase("shop")){
                                main.getConfig().set("Pnj",p.getLocation());
                                main.saveConfig();
                            }
                            if (args[2].equalsIgnoreCase("spawn")) {
                                main.getConfig().set("Spawn", p.getLocation());
                                main.saveConfig();
                            }
                            if (args[2].equalsIgnoreCase("blue")) {
                                main.getConfig().set("Teams.blue.world.locationofspawn", p.getLocation());
                                main.saveConfig();
                            }
                            if (args[2].equalsIgnoreCase("red")) {
                                main.getConfig().set("Teams.red.world.locationofspawn", p.getLocation());
                                main.saveConfig();
                            }
                        }
                        if (args[1].equalsIgnoreCase("setbanner")) {
                            if (args[2].equalsIgnoreCase("blue")) {
                                main.getConfig().set("Teams.blue.world.locationofbanner", p.getLocation());
                                main.saveConfig();
                            }
                            if (args[2].equalsIgnoreCase("red")) {
                                main.getConfig().set("Teams.red.world.locationofbanner", p.getLocation());
                                main.saveConfig();
                            }
                        }
                    } else {
                        p.sendMessage(ChatColor.BLUE + "Tu n'as pas la permission de faire ceci !");
                    }
                }

                if(args[0].equalsIgnoreCase("shop") && !(commandSender instanceof Player)){
                    if(p.getWorld() == main.getConfig().getLocation("Spawn").getWorld()){
                        main.shop(p);
                    }
                }
            }
        }
        return false;
    }
}
