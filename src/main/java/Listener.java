import org.pircbotx.Channel;
import org.pircbotx.hooks.ListenerAdapter;

import org.pircbotx.hooks.types.GenericMessageEvent;


public class Listener extends ListenerAdapter {

    public void onGenericMessage(GenericMessageEvent e){
        if(e.getMessage().startsWith("!hi")) {
            e.respondWith("Hey!" + "  " + e.getMessage());
        }

        if(e.getUser().equals("r3ido101")){
            e.respond("Hello! R3ido101");
        }

        if(e.getMessage().equals("!zzz")) {

        }
    }

}