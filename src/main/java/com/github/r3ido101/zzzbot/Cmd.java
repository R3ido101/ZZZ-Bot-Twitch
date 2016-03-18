package com.github.r3ido101.zzzbot;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;


public class Cmd extends ListenerAdapter {


    public static Logger logger = LoggerFactory.getLogger(Main.class);
    public static Map<String, Object> conf = null;
    public static File configurationFile = new File("./Config/botlogin.yml");

    public void onGenericMessage(GenericMessageEvent e) {


        String botName = (String) conf.getOrDefault("name", " nick");
        String oauthPassword = (String) conf.getOrDefault("oauth", "default password");
        String channel = (String) conf.getOrDefault("channel", "default channel");


        if (e.getMessage().equalsIgnoreCase("!test")) {
            e.respondWith("this is a test.");
        }

        if (e.getMessage().equalsIgnoreCase("Kappa")) {
            e.respond(botName + "your not a Kappa your a KappaRoss");

        }
        if (e.getMessage().equalsIgnoreCase("!fucked")) {
            e.respond(e.getUser() + " " + " - whent go go and fuck a girl!");

        }

        if (e.getMessage().equalsIgnoreCase("!bot")) {
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
