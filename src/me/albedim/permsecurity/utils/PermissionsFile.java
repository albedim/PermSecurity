package me.albedim.permsecurity.utils;

import me.albedim.permsecurity.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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
 * Created at: 14:34
 * Version: 1.0.0
 * Description: This is the class for the permissions file
 */

public class PermissionsFile
{
    public static final File file = new File(Main.getInstance().getDataFolder() + "/permissions.yml");
    private FileConfiguration permissions;

    public PermissionsFile()
    {
        if(!file.exists())
            try{
                file.createNewFile();
                this.permissions = YamlConfiguration.loadConfiguration(file);
                this.permissions.createSection("permissions");
                this.permissions.save(file);
            }catch(IOException exception){
                exception.printStackTrace();
            }
        this.permissions = YamlConfiguration.loadConfiguration(file);
    }

    public boolean hasPermission(String playerNickname, String permission)
    {
        String users = this.permissions.getString("permissions.p_" + permission + ".users");
        if(users != null)
        {
            String[] usersArray = users.split(",");
            for (String user : usersArray)
                if (user.equals(playerNickname))
                    return true;
            return false;
        }
        return true;
    }
}
