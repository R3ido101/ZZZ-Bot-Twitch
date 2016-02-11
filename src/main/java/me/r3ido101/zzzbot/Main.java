package me.r3ido101.zzzbot;
import org.slf4j.Logger;
import org.pircbotx.Configuration;
import org.pircbotx.*;
import org.pircbotx.cap.EnableCapHandler;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.lang.*;

public class Main {
    public static Logger logger				= LoggerFactory.getLogger(Main.class);
    public static Map<String, Object> conf = null;
    public static File					configurationFile	= new File("Config/botlogin.yml");

    public static void setupFolders() {
        logger.info("Begin reading the configuration");
        File directory = configurationFile.getParentFile();
        if (!directory.exists()) directory.mkdirs();

        if (!configurationFile.exists()) {
            logger.info("Config file doesn't exists , attempting to create it");
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("BotLogin.yml")));
                FileWriter writer = new FileWriter(configurationFile);

                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line + "\n");
                }
                writer.flush();
                writer.close();
                logger.info("I've Finished Writing your Default Config!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            logger.info("Config file exists");
            Yaml yaml = new Yaml(new Representer(), new DumperOptions());
            conf = (Map<String, Object>)yaml.load(new FileInputStream(configurationFile));
            logger.info("Managed to load config file");
        } catch (Throwable t) {
            logger.error("Config file load failed");
            conf = new HashMap();
            t.printStackTrace();
        }
    }

    public static void main(String[] args) {
        setupFolders();
        String botName = (String) conf.getOrDefault("name", " nick");
        String oauthPassword = (String) conf.getOrDefault("oauth", "default password");
        String channel = (String) conf.getOrDefault("channel", "default channel");
        logger.info("botName : "+ botName+" oauthPassword :  "+oauthPassword+" channel : "+channel);

        Configuration configuration = new Configuration.Builder() //
                .setAutoNickChange(false) //
                .setOnJoinWhoEnabled(false) //
                .setCapEnabled(true) //
                .addCapHandler(new EnableCapHandler("twitch.tv/membership")) //
                .addServer("irc.twitch.tv") //
                .setName(botName) //
                .setServerPassword(oauthPassword) //
                .addAutoJoinChannel(channel) //
                .addListener(new Listener()) //
                .buildConfiguration();

        PircBotX bot = new PircBotX(configuration);
        try {
            bot.startBot();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}