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
 * Description: This is the class for the kicks file
 */

public class KicksFile
{
    public static final File file = new File(Main.getInstance().getDataFolder() + "/kicks.yml");
    private FileConfiguration kicks;

    public KicksFile()
    {
        if(!file.exists())
            try{
                file.createNewFile();
                this.kicks = YamlConfiguration.loadConfiguration(file);
                this.kicks.createSection("players");
                this.kicks.save(file);
            }catch(IOException exception){
                exception.printStackTrace();
            }
        this.kicks = YamlConfiguration.loadConfiguration(file);
    }

    public boolean wasKicked(String playerNickname)
    {
        return Boolean.parseBoolean(this.kicks.getString("players." + playerNickname + ".kicked"));
    }

    public boolean exists(String playerNickname)
    {
        return this.kicks.getString("players." + playerNickname + ".kicked") != null;
    }

    public void add(String playerNickname)
    {
        try {
            if(!exists(playerNickname))
            {
                this.kicks.createSection("players." + playerNickname + ".kicked");
                this.kicks.set("players." + playerNickname + ".kicked", false);
                this.kicks.save(file);
            }
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public void kick(String playerNickname)
    {
        try {
            this.kicks.set("players." + playerNickname + ".kicked", true);
            this.kicks.save(file);
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
