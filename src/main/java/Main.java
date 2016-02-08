import org.slf4j.Logger;

/**
 * TODO
 *
 * Fix "Error:(52, 61) java: incompatible types: java.lang.String cannot be converted to org.omg.CORBA.Object
 Error:(54, 55) java: incompatible types: java.lang.String cannot be converted to org.omg.CORBA.Object
 Error:(53, 62) java: incompatible types: java.lang.String cannot be converted to org.omg.CORBA.Object"
 and see where they are coming from
 *
 */

import org.omg.CORBA.Object;
import org.pircbotx.Configuration;
import org.pircbotx.*;
import org.pircbotx.cap.EnableCapHandler;
import org.slf4j.Logger;
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
        File directory = configurationFile.getParentFile();
        if (!directory.exists()) directory.mkdirs();

        if (!configurationFile.exists()) {
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
            Yaml yaml = new Yaml(new Representer(), new DumperOptions());
            conf = (Map<String, Object>)yaml.load(new FileInputStream(configurationFile));
        } catch (Throwable t) {
            conf = new HashMap();
            t.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String botName = (String) conf.getOrDefault("nick", " nick");
        String oauthPassword = conf.getOrDefault("password", "default password");
        String channel = conf.getOrDefault("channel", "default channel");

        Configuration configuration = new Configuration.Builder() //
                .setAutoNickChange(false) //
                .setOnJoinWhoEnabled(false) //
                .setCapEnabled(true) //
                .addCapHandler(new EnableCapHandler("twitch.tv/membership")) //
                .addServer("irc.twitch/.tv") //
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