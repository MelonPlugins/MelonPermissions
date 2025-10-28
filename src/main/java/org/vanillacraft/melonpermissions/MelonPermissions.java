package org.vanillacraft.melonpermissions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public final class MelonPermissions extends JavaPlugin implements Listener {

    public static MelonPermissions inst;
    public static PermissionData data;
    public static HashMap<String, PermissionAttachment> permissionAttachments = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        setPlayerPerms(player);
    }

    public void setPlayerPerms(Player player) {
        PermissionAttachment permissionAttachment = player.addAttachment(this);
        String uuid = player.getUniqueId().toString();
        if (data.getPlayer(uuid) == null) {
            data.players.put(uuid, new PlayerPermNode(List.of("default")));
        }
        for (String groupName : data.getPlayer(uuid).inheritedGroups) {
            for (String permissionNode : data.getGroup(groupName).permissionNodes) {
                permissionAttachment.setPermission(permissionNode, true);
            }
        }
        permissionAttachments.put(uuid, permissionAttachment);
    }

    public void reload() {
        removeAttachments();
        data = new PermissionData(this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            setPlayerPerms(player);
        }
    }

    public void removeAttachments() {
        for (String uuid : permissionAttachments.keySet()) {
            PermissionAttachment attachment = permissionAttachments.get(uuid);
            for (String permission : data.getPlayer(uuid).getPermissions()) {
                attachment.unsetPermission(permission);
            }
            attachment.remove();
        }
    }

    @Override
    public void onEnable() {
        inst = this;
        getServer().getPluginManager().registerEvents(this, this);
        reload();

        registerCommand("mpreload", new ReloadCommand());
    }

    @Override
    public void onDisable() {
    }
}
