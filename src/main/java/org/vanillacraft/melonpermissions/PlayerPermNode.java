package org.vanillacraft.melonpermissions;

import java.util.ArrayList;
import java.util.List;

public class PlayerPermNode {
    public List<String> inheritedGroups;

    public PlayerPermNode(List<String> inheritedGroups) {
        this.inheritedGroups = new ArrayList<>(inheritedGroups);
    }

    public List<String> getGroups() {
        return inheritedGroups;
    }

    public List<String> getPermissions() {
        ArrayList<String> permissions = new ArrayList<>();
        for (String group : inheritedGroups) {
            permissions.addAll(MelonPermissions.data.getGroup(group).permissionNodes);
        }
        return permissions;
    }

    public void addGroup(String group) {
        inheritedGroups.add(group);
    }

    public boolean hasGroup(String group) {
        return inheritedGroups.contains(group);
    }

    public boolean hasPermission(String permission) {
        return getPermissions().contains(permission);
    }
}
