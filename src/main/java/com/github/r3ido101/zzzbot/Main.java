package com.github.r3ido101.zzzbot;


import com.mb3364.twitch.api.Twitch;
import com.mb3364.twitch.api.auth.Scopes;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.EnableCapHandler;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Main
{


    public static Logger logger				= LoggerFactory.getLogger(Main.class);
    public static Map<String, Object> conf = null;
    public static File					configurationFile	= new File("./Config/botlogin.yml");

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
            Map<String, Object> load = (Map<String, Object>)yaml.load(new FileInputStream(configurationFile));
			conf = load;
            logger.info("Managed to load config file");
        } catch (Throwable t) {
            logger.error("Config file load failed");
            conf = new HashMap<String, Object>();
            t.printStackTrace();
        }
    }

    public static void main(String[] args) throws URISyntaxException {
        setupFolders();
        String botName = (String) conf.getOrDefault("name", " nick");
        String oauthPassword = (String) conf.getOrDefault("oauth", "default password");
        String channel = (String) conf.getOrDefault("channel", "default channel");
        logger.info("botName : "+botName+" oauthPassword :  "+oauthPassword+" channel : "+channel);
        logger.info("We are now connecting to " + channel);

        Configuration configuration = new Configuration.Builder() //
                .setAutoNickChange(false) //
                .setOnJoinWhoEnabled(false) //
                .setCapEnabled(true) //
                .addCapHandler(new EnableCapHandler("twitch.tv/membership")) //
                .addServer("irc.twitch.tv") //
                .setName(botName) //
                .setServerPassword(oauthPassword) //
                .addAutoJoinChannel("#" + channel) //
                .addListener(new Commands()) //
                .buildConfiguration();

        PircBotX bot = new PircBotX(configuration);
        try {
            bot.startBot();
        } catch (Throwable t) {
            t.printStackTrace();
        }

        Twitch twitch = new Twitch();
        twitch.setClientId("shjdkashjkfdl"); // This is your registered application's client ID

/* Specify your registered callback URI */
        URI callbackUri = new URI("http://127.0.0.1:23522/authorize.html");

/* Get the authentication URL. Note: you will set the required scopes needed here. */
        String authUrl = twitch.auth().getAuthenticationUrl(twitch.getClientId(), callbackUri, Scopes.USER_READ, Scopes.CHANNEL_READ);

/* Send the user to the webpage somehow so that they can authorize your application */
        openWebpage(authUrl);

/* Waits for the user to authorize or deny your application. Note: this function will block until a response is received! */
        boolean authSuccess = twitch.auth().awaitAccessToken();

/* Check if authentication was successful */
        if (authSuccess) {
  /* The access token is automatically set in the Twitch object and will be sent with all further API requests! */
            String accessToken = twitch.auth().getAccessToken(); // if we want to explicitly get it for some reason
            System.out.println("Access Token: " + accessToken);
        } else {
  /* Authentication failed, most likely because the user denied the authorization request */
            System.out.println(twitch.auth().getAuthenticationError());
        }




    private static class Commands extends ListenerAdapter {

        public void onGenericMessage(GenericMessageEvent e) {

            //public static File = configurationFile	= new File("./Config/botlogin.yml");

            String botName = (String) conf.getOrDefault("name", " nick");
            String oauthPassword = (String) conf.getOrDefault("oauth", "default password");
            String channel = (String) conf.getOrDefault("channel", "default channel");
            logger.info("botName : "+botName+" oauthPassword :  "+oauthPassword+" channel : "+channel);
            logger.info("We are now connecting to " + channel);


            if(e.getMessage().equalsIgnoreCase("!test")) {
                e.respondWith("this is a test.");
            }

            if(e.getMessage().equalsIgnoreCase("Kappa")) {
                e.respond(botName + "your not a Kappa your a KappaRoss");

            }
            if(e.getMessage().equalsIgnoreCase("!fucked")) {
                e.respond(e.getUser() + " " + " - whent go go and fuck a girl!");

            }

            if(e.getMessage().equalsIgnoreCase("!bot")) {
                e.respondWith("/me " + "⛔ " + "I'm zZz_Bot Made By R3ido101 (https://www.twitch.tv/R3ido101) I use Pircbotx version - " + PircBotX.VERSION + " to work!" + " ⛔");
            }

            if (e.getMessage().equalsIgnoreCase("!test")) {
                e.respondWith(e.getUser().getNick() + " " + "zZz-Bot Is speaking in" + " " + channel);
            }

            if (e.getMessage().equalsIgnoreCase("!time")) {
                String time = new java.util.Date().toString();
                e.respondWith("Hey " + e.getUser().getNick() + " the time is " + time + " !");
            }

        }

    }

}

// TODO: This is a reminder that i need to add the twitch api to my bot so i know i can get it work work with the api under the package
// TODO: me.r3ido101.zzzbot.twitchapi.
// TODO: Need to check to see if the bot will pick up vital information form the twitch api.
// TODO: Need to add welcome message when bot is started.