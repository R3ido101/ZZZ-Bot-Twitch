package com.github.r3ido101.zzzbot;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.UserHostmask;
import org.pircbotx.hooks.*;
import org.pircbotx.hooks.types.GenericMessageEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Listener extends ListenerAdapter {
    public static Logger logger				= LoggerFactory.getLogger(Main.class);

    public void onGenericMessage(GenericMessageEvent e) {

        if(e.getMessage().equalsIgnoreCase("!test")) {
            e.respondWith("this is a test.");
        }

        if(e.getMessage().equalsIgnoreCase("Kappa")) {
           e.respond("your not a Kappa your a KappaRoss");

        }
        if(e.getMessage().equalsIgnoreCase("!fucked")) {
            e.respond(" - whent go go and fuck a girl!");

        }

        if(e.getMessage().equalsIgnoreCase("!bot")) {
            e.respondWith("/me " + "⛔ " + "I'm zZz_Bot Made By R3ido101 (https://www.twitch.tv/R3ido101) I use Pircbotx version - " + PircBotX.VERSION + " to work!" + " ⛔");
        }

    }

}


//TODO: Add where you can use user channel and botname as a string updated by the config file