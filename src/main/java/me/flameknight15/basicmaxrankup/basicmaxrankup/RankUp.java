package me.flameknight15.basicmaxrankup.basicmaxrankup;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RankUp {
    BasicMaxRankUp main;

    public RankUp(BasicMaxRankUp main) {
        this.main = main;
    }


    public void rankup(Player player) {
        ArrayList<String> availableRanks = new ArrayList<String>();
        for (String s : main.ranks) {
            availableRanks.add(s);
        }
/*        for (int i = 0; i < availableRanks.size(); i++) {

            //for (String s : main.perms.getPlayerGroups(player)) {
            String playerRankGroup = main.perms.getPlayerGroups(player)[1];
            String comparingTo = availableRanks.get(i).split(":")[0];

                if (comparingTo.equalsIgnoreCase(playerRankGroup)) {
                    break;
                } else{
                    //player.sendMessage(comparingTo + " | " + playerRankGroup + " " + i );
                    //availableRanks.remove(availableRanks.get(i));

                }
            //}
        }*/

        for (int i = 0; i < availableRanks.size(); i++) {
            //player.sendMessage(main.ranks.size() + "");
            //double playerBalance = main.econ.getBalance(player);
            String playerRank = main.ret.getPlayerRank(player);

            //player.sendMessage(rankInfo + " " + rankInfo[0] + " " + rankInfo[1]);
            //double rankupCost = Double.parseDouble(rankInfo[1]);
            //String rank = rankInfo[0];
            //player.sendMessage(rankInfo + " " + rank + " " + rankupCost);
            double playerBalance = main.econ.getBalance(player);
            String[] rankInfo = availableRanks.get(i).split(":");
            //player.sendMessage(availableRanks.get(i));
            //boolean cont = false;
/*            ArrayList ownRanks = new ArrayList<String>();
            for(String nextRank : main.perms.getPlayerGroups(player)){
                player.sendMessage(nextRank);
                ownRanks.add(nextRank);
            }*/

            double rankupCost = Double.parseDouble(rankInfo[1]);
            //player.sendMessage(availableRanks.toString()/*playerRank + " " + rank + " " + rankupCost + ""*/);

            if (playerRank != null) {
                if (!(playerRank.equalsIgnoreCase("Free"))) {
                    String playerRankGroup = main.ret.getPlayerRank(player);
                    String comparingTo = availableRanks.get(i).split(":")[0];
                    if (comparingTo.equalsIgnoreCase(playerRankGroup)) {
                    //if so get next rank and how much it costs
                    //See if the player has enough to rank up


                    //player.sendMessage("THIS ONE: " + playerBalance + " | " + rankupCost);
                    if (playerBalance >= rankupCost) {
                        //If so rank up
                        //player.sendMessage("THIS ONE: " + playerBalance + " | " + rankupCost);
                        //if (player.hasPermission("cmi.command.rankup")) {
                        //runCommand(player, "cmi rankup");
                        if (Bukkit.isPrimaryThread()) {
                            player.chat("/" + "rankup");
                            delay(1000);
                        } else {
                            Bukkit.getScheduler().runTask(main, new Runnable() {
                                @Override
                                public void run() {
                                    player.chat("/" + "rankup");
                                    //If lag is caused - Add delay here
                                    delay(1000);
                                }
                            });
                        }
                        //}
                        //Pause for a tick to allow other plugin to update
                    }
                }
                }
            }

        }
    }

    public void delay(long time) {
        final long PERIOD = time; // Adjust to suit timing
        long lastTime = System.currentTimeMillis() - PERIOD;
        long thisTime = System.currentTimeMillis();
        if ((thisTime - lastTime) >= PERIOD) {
            lastTime = thisTime;
        }
    }
}
