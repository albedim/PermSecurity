package me.albedim.permsecurity;

import me.albedim.permsecurity.annotation.Getter;
import me.albedim.permsecurity.executor.CommandExecutor;
import me.albedim.permsecurity.listener.JoinEvent;
import me.albedim.permsecurity.utils.KicksFile;
import me.albedim.permsecurity.utils.PermissionsFile;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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
 * Description: This is the class for the plugin
 */

public class Main extends JavaPlugin
{
    private static Main plugin;
    private static PermissionsFile permissionsFile;

    private static KicksFile kicksFile;

    @Override
    public void onEnable()
    {
        super.onEnable();
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        getCommand("aggiungi_perms_deluxe_essentials").setExecutor(new CommandExecutor());
        getCommand("give").setExecutor(new CommandExecutor());
        getCommand("removeKick").setExecutor(new CommandExecutor());
        saveDefaultConfig();
        plugin = this;
        permissionsFile = new PermissionsFile();
        kicksFile = new KicksFile();
    }

    @Override public void onDisable() { super.onDisable(); }

    @Getter public static Main getInstance() { return plugin; }

    @Getter public static PermissionsFile getPermissionsFile() { return permissionsFile; }
}
