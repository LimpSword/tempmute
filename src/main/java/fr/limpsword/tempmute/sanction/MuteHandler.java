package fr.limpsword.tempmute.sanction;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fr.limpsword.tempmute.database.SQLQueries;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;

public final class MuteHandler implements Runnable {

    private final List<String> uuidToRemove = Lists.newArrayList();
    private final Map<String, Long> playersMute = Maps.newHashMap();

    public void load(SQLQueries sqlQueries, Plugin plugin) {
        playersMute.putAll(sqlQueries.load());

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, 0, 20);
    }

    public boolean isMuted(OfflinePlayer player) {
        return playersMute.containsKey(player.getUniqueId().toString());
    }

    public long getTimeRemaining(Player player) {
        if (!isMuted(player)) return -1;
        return playersMute.get(player.getUniqueId().toString()) - System.currentTimeMillis();
    }

    public boolean mute(OfflinePlayer player, Long until) {
        if (!isMuted(player)) {
            playersMute.put(player.getUniqueId().toString(), System.currentTimeMillis() + until);
            return true;
        }
        return false;
    }

    public boolean unmute(OfflinePlayer player) {
        if (isMuted(player)) {
            playersMute.remove(player.getUniqueId().toString());
            return true;
        }
        return false;
    }

    public void save(SQLQueries sqlQueries) {
        sqlQueries.save(playersMute);
    }

    @Override
    public void run() {
        playersMute.forEach((uuid, until) -> {
            if (until < System.currentTimeMillis()) {
                // Mute has ended
                uuidToRemove.add(uuid);
            }
        });

        for (String uuid : uuidToRemove) {
            playersMute.remove(uuid);
        }
        uuidToRemove.clear();
    }
}