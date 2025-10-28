package org.vanillacraft.melonpermissions;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        MelonPermissions.inst.reload();
    }
}
