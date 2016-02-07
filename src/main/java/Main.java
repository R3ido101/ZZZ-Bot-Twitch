import org.omg.CORBA.Object;
import org.pircbotx.Configuration;
import org.pircbotx.*;
import org.pircbotx.cap.EnableCapHandler;
import org.pircbotx.exception.IrcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class Main extends PircBotX {

    public static Logger logger = LoggerFactory.getLogger(Main.class);
    public static Map<String, Object> conf = null;
    public static Yaml cfg = new Yaml();
    public static File configurationFile = new File("Config/botlogin.yml");
    public static void setupFolders(){
        File f = new File("Config");
        f.mkdir();

        try{
            if(!configurationFile.exists());){
                configurationFile.createNewFile();
                Scanner scanner = new Scanner(Main.class.getResourceAsStream("./BotLogin.yml"));
                FileWriter fileWriter = null;

                    fileWriter = new FileWriter(configurationFile);
                while (scanner.hasNextLine()) {
                    fileWriter.write(scanner.nextLine() + "\n");

                }
                fileWriter.close();
                scanner.close();
                logger.info("I've Finished Writing your Default Config!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    try {
        conf = (Map<String, Object>) yaml.load(new FileInputStream(configurationFile));
    } catch(FileNotFoundException e) {
        e.printStackTrace();
    }

    public static void main(String[] args) throws Exception{
        //Configure what we want our bot to do
        Configuration configuration = new Configuration.Builder()
                .setAutoNickChange(false) //Twitch doesn't support multiple users
                .setOnJoinWhoEnabled(false) //Twitch doesn't support WHO command
                .setCapEnabled(true)
                .addCapHandler(new EnableCapHandler("twitch.tv/membership")) //Twitch by default doesn't send JOIN, PART, and NAMES unless you request it, see https://github.com/justintv/Twitch-API/blob/master/IRC.md#membership

                .addServer("irc.twitch.tv")
                .setName("zZz_Bot") //Your twitch.tv username
                .setServerPassword("oauth:zony7woyptimasiitbajybg6wjniri") //Your oauth password from http://twitchapps.com/tmi
                .addAutoJoinChannel("#r3ido101") //Some twitch channel
                .addListener(new Listener())
                .buildConfiguration();

        //Create our bot with the configuration
        PircBotX bot = new PircBotX(configuration);
        //Connect to the server
            try {
                bot.startBot();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IrcException e) {
                e.printStackTrace();
            }
        };

}