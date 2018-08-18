package me.flameknight15.basicmaxrankup.basicmaxrankup;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Retrieval {
    BasicMaxRankUp main;
    Retrieval(BasicMaxRankUp m){
        main = m;
    }
    String getPlayerRank(Player player) {
        //FileConfiguration rankUps = YamlConfiguration.loadConfiguration(new File("plugins/CMI/ranks.yml"));
        List localSet = main.localSet;
        ArrayList<String> ranks = new ArrayList<>();
        //String playerRank = null;

        for (Object configObject = localSet.iterator(); ((Iterator) configObject).hasNext(); ) {
            String rank = (String) ((Iterator) configObject).next();
            ranks.add(rank);
        }
        String playerRank = main.perms.getPlayerGroups(player)[0];

        for (int i = 0; i < ranks.size(); i++) {
            for (int a = 1; a < main.perms.getPlayerGroups(player).length; a++) {
                //player.sendMessage("Perms: " + main.perms.getPlayerGroups(player)[a] + " " + ranks.get(i));
                if (ranks.get(i).split(":")[0].equalsIgnoreCase(main.perms.getPlayerGroups(player)[a])) {
                    //player.sendMessage("Permss: " + main.perms.getPlayerGroups(player)[a]);
                    playerRank = ranks.get(i).split(":")[0];
                }
            }
        }



/*            if(!(main.perms.getPlayerGroups(player).length > 1)) {
                if (ranks.get(i).equalsIgnoreCase(main.perms.getPlayerGroups(player)[0])) {
                    playerRank = ranks.get(i);
                }
            }else if (main.perms.getPlayerGroups(player)[1] != null) {
                if (ranks.get(i).equalsIgnoreCase(main.perms.getPlayerGroups(player)[1])) {
                    playerRank = ranks.get(i);
                }
            }else if (main.perms.getPlayerGroups(player)[2] != null) {
                if (ranks.get(i).equalsIgnoreCase(main.perms.getPlayerGroups(player)[2])) {
                    playerRank = ranks.get(i);
                }
            }*/

        return playerRank;
    }

        public double getRankupCost (Player player){
            FileConfiguration rankUps = YamlConfiguration.loadConfiguration(new File("plugins/CMI/ranks.yml"));
            Set localSet = rankUps.getKeys(false);
            ArrayList<String> ranks = new ArrayList<>();
            String playerRank = null;
            double rankupCost = 0;

            for (Object configObject = localSet.iterator(); ((Iterator) configObject).hasNext(); ) {
                String rank = (String) ((Iterator) configObject).next();
                ranks.add(rank);
            }

            for (int i = 0; i < ranks.size(); i++) {
                if (ranks.get(i).equalsIgnoreCase(main.perms.getPlayerGroups(player)[0])) {
                    playerRank = ranks.get(i);
                    if (!playerRank.equalsIgnoreCase("Free"))
                        rankupCost = rankUps.getDouble("" + ranks.get(i + 1) + ".MoneyCost");
                } else if (!(main.perms.getPlayerGroups(player)[1].isEmpty() || main.perms.getPlayerGroups(player)[1] == null))
                    if (ranks.get(i).equalsIgnoreCase(main.perms.getPlayerGroups(player)[1])) {
                        playerRank = ranks.get(i);
                        if (!playerRank.equalsIgnoreCase("Free"))
                            rankupCost = rankUps.getDouble("" + ranks.get(i + 1) + ".MoneyCost");
                    }
            }

            return rankupCost;
        }
}
