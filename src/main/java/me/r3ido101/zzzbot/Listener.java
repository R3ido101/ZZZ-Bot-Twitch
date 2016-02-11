package me.r3ido101.zzzbot;

import org.pircbotx.hooks.*;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class Listener extends ListenerAdapter {

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

    }

}
