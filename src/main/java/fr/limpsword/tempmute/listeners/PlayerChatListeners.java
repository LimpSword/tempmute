package fr.limpsword.tempmute.listeners;

import fr.limpsword.tempmute.sanction.MuteHandler;
import fr.limpsword.tempmute.sanction.TimeParser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class PlayerChatListeners implements Listener {

    private final MuteHandler muteHandler;

    public PlayerChatListeners(MuteHandler muteHandler) {
        this.muteHandler = muteHandler;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (muteHandler.isMuted(player) && !player.hasPermission("tempmute.bypass")) {
            player.sendMessage("§cYou are still muted for "
                    + TimeParser.getDurationString(muteHandler.getTimeRemaining(player)) + ".");
            event.setCancelled(true);
        }
    }
}