package org.vanillacraft.melonpermissions;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PermissionData {
    public HashMap<String, GroupPermNode> groups;
    public HashMap<String, PlayerPermNode> players;

    private File dataFile;
    private FileConfiguration dataYaml;
    private MelonPermissions plugin;

    public PermissionData(MelonPermissions plugin) {
        this.plugin = plugin;
        this.groups = new HashMap<>();
        this.players = new HashMap<>();

        dataFile = new File(plugin.getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            plugin.getDataFolder().mkdirs();
            plugin.saveResource("data.yml", false);
        }

        dataYaml = new YamlConfiguration();
        try {
            dataYaml.load(dataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        this.loadGroups();
        this.loadPlayers();
    }

    private void loadPlayers() {
        List<String> playerUuids = new ArrayList<>();
        playerUuids.addAll(this.dataYaml.getConfigurationSection("players").getKeys(false));

        for (String playerUuid : playerUuids) {
            List<String> playerGroups = dataYaml.getStringList("players." + playerUuid + ".groups");
            players.put(playerUuid, new PlayerPermNode(playerGroups));
        }
    }

    private void loadGroups() {
        List<String> groupNames = new ArrayList<>();
        groupNames.addAll(this.dataYaml.getConfigurationSection("groups").getKeys(false));

        for (String groupName : groupNames) {
            List<String> setPermissions = dataYaml.getStringList("groups." + groupName + ".setpermissions");
            groups.put(groupName, new GroupPermNode(setPermissions));
        }
    }

    public GroupPermNode getGroup(String groupName) {
        return groups.get(groupName);
    }

    public PlayerPermNode getPlayer(String playerUuid) {
        return players.get(playerUuid);
    }
}
