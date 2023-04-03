package me.albedim.permsecurity.listener;

import me.albedim.permsecurity.Main;
import me.albedim.permsecurity.annotation.PermissionsHandler;
import me.albedim.permsecurity.utils.KicksFile;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;
import java.util.*;

/*
  Copyright 2023 albedim.
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
      http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

/**
 * @author: Alberto Di Maio, albedim <dimaio.albe@gmail.com>
 * Created on: 02/04/23
 * Created at: 00:14
 * Version: 1.0.0
 * Description: This is the class for the join event
*/

public class JoinEvent implements Listener
{

    /**
     * Event
     * @param e <event>
     */

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        // Add player to kicks list if new
        KicksFile kicksFile =  new KicksFile();
        kicksFile.add(player.getName());
        // Kick player if they are kicked in the list
        if(kicksFile.wasKicked(player.getName())) {
            player.kickPlayer(Main.getInstance().getConfig().getString("settings.kick_message"));
        }else{
            Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

                @Override
                public void run()
                {
                    player.sendMessage("check");
                    // Get current permissions every 10s
                    Set<PermissionAttachmentInfo> currentPermissions = player.getEffectivePermissions();
                    // If they have all permissions
                    if (player.hasPermission("*")) {
                        // If they can't have these permissions
                        if(!Main.getPermissionsFile().hasPermission(player.getName(), "*"))
                        {
                            KicksFile kicksFile = new KicksFile();
                            kicksFile.kick(player.getName());
                            player.kickPlayer(Main.getInstance().getConfig().getString("settings.kick_message"));
                            sendMessage(player.getName());
                        }
                    } else {
                        for (PermissionAttachmentInfo permission : currentPermissions)
                            // If they can't have this permission
                            if (!Main.getPermissionsFile().hasPermission(player.getName(), permission.getPermission()))
                            {
                                KicksFile kicksFile = new KicksFile();
                                kicksFile.kick(player.getName());
                                player.kickPlayer(Main.getInstance().getConfig().getString("settings.kick_message"));
                                sendMessage(player.getName(), currentPermissions);
                                break;
                            }
                    }
                }
            }, 140L, 140L);
        }
    }

    private void sendMessage(String playerNickname, Set<PermissionAttachmentInfo> permissions)
    {
        String formattedPermissions = getFormattedPermissions(permissions);
        for(Player staffer : Bukkit.getOnlinePlayers())
            if(staffer.hasPermission(Main.getInstance().getConfig().getString("settings.staff_permission")))
            {
                staffer.sendMessage("§a§lPermSecurity");
                staffer.sendMessage(" §a- §7L'utente §8[§c" + playerNickname + "§8] §7 ha provato ad ottenere dei permessi");
                staffer.sendMessage(" §a- §7Permessi trovati: §8[" + formattedPermissions + "§8]");
                staffer.sendMessage(" §a- §7Kickato: §8[§aSI§8]");
            }
    }

    /**
     * Takes a set of permissions and format it as a string
     * @param permissions
     * @return formattedPermissions
     */

    @PermissionsHandler
    private String getFormattedPermissions(Set<PermissionAttachmentInfo> permissions)
    {
        String formattedPermissions = "";
        for(PermissionAttachmentInfo permission : permissions)
            formattedPermissions += "§a" + permission.getPermission() + "§8, ";
        return formattedPermissions;
    }

    private void sendMessage(String playerNickname)
    {
        for(Player staffer : Bukkit.getOnlinePlayers())
            if(staffer.hasPermission(Main.getInstance().getConfig().getString("settings.staff_permission")))
            {
                staffer.sendMessage("§a§lPermSecurity");
                staffer.sendMessage("§a - §7L'utente §8[§c" + playerNickname + "§8] §7 ha provato ad ottenere dei permessi");
                staffer.sendMessage("§a - §7Permessi trovati: §8[§a*§8]");
                staffer.sendMessage("§a - §7Kickato: §8[§aSI§8]");
            }
    }
}
