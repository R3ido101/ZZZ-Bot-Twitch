package me.r3ido101.zzzbot;

import org.pircbotx.hooks.ListenerAdapter;

import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;


public class Listener extends ListenerAdapter {

    @Override
    public void onConnect(ConnectEvent event) throws Exception {
        super.onConnect(event);
        event.respond("Hello Im Here @R3ido101");
    }

    public void onGenericMessage(GenericMessageEvent e){

        if(e.getMessage().startsWith("!hi")) {
            e.respondWith("Hey!" + "  " + e.getMessage());
        }

        if(e.getUser().equals("r3ido101")){
            e.respond("Hello! R3ido101");
        }

        if(e.getMessage().equals("!zzz")) {

            e.respondWith("Hello I am zZz-Bot how can i help?!");

        }

        if(e.getMessage().equals("!nb")) {
            e.respondWith("these are the commands for NightBot");
            e.respond("!commands");
        }

        if(e.getUser().equals("r3ido101")){
            e.respondWith("hey My Master hows it going!! :)");
        }
    }



}