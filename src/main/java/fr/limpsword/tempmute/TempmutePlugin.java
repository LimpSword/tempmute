package fr.limpsword.tempmute;

import fr.limpsword.tempmute.commands.TempmuteCommand;
import fr.limpsword.tempmute.commands.UnmuteCommand;
import fr.limpsword.tempmute.database.SQLConnection;
import fr.limpsword.tempmute.database.SQLQueries;
import fr.limpsword.tempmute.listeners.PlayerChatListeners;
import fr.limpsword.tempmute.sanction.MuteHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TempmutePlugin extends JavaPlugin {

    private final MuteHandler muteHandler = new MuteHandler();

    private final SQLConnection sqlConnection = new SQLConnection();
    private final SQLQueries sqlQueries = new SQLQueries(sqlConnection);

    @Override
    public void onEnable() {
        saveDefaultConfig();

        sqlConnection.enable(getConfig(), () -> {
            muteHandler.load(sqlQueries, this);
        });

        Bukkit.getPluginManager().registerEvents(new PlayerChatListeners(muteHandler), this);

        Objects.requireNonNull(getCommand("tempmute")).setExecutor(new TempmuteCommand(muteHandler));
        Objects.requireNonNull(getCommand("unmute")).setExecutor(new UnmuteCommand(muteHandler));
    }

    @Override
    public void onDisable() {
        muteHandler.save(sqlQueries);
        sqlConnection.disable();
    }
}