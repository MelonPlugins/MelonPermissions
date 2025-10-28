package org.vanillacraft.melonpermissions;

import java.util.ArrayList;
import java.util.List;

public class GroupPermNode {
    public List<String> permissionNodes;

    public GroupPermNode(List<String> setPermissions) {
        permissionNodes = new ArrayList<>(setPermissions);
    }

    public void addPermission(String permissionNode) {
        permissionNodes.add(permissionNode);
    }

    public void removePermission(String permissionNode) {
        permissionNodes.remove(permissionNode);
    }

    public boolean hasPermission(String permissionNode) {
        return permissionNodes.contains(permissionNode);
    }

    public List<String> getPermissionNodes() {
        return permissionNodes;
    }
}
