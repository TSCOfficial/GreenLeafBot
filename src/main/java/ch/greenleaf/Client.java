package ch.greenleaf;

import ch.greenleaf.common.listeners.EventListener;
import ch.greenleaf.component.button.ButtonManager;
import ch.greenleaf.features.commands.HelloWorld;
import ch.greenleaf.features.commands.Message;
import ch.greenleaf.features.commands.TicketCommand;
import ch.greenleaf.features.commands.UserInfo;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.net.URI;
import java.net.URISyntaxException;

// push to docker:
// 1. `.\gradlew.bat shadowJar`
// 2. run Dockerfile

public class Client {

    public static WebSocketClientHandler webSocket;

    public final Dotenv config;

    public final ShardManager shardManager;

    public ButtonManager buttonManager;

    public static Client client;

    public Client() throws LoginException, URISyntaxException {
        config = Dotenv.configure().load();

        URI backendUri = new URI("ws://localhost:8080/ws/bot");
        webSocket = new WebSocketClientHandler(backendUri);
        webSocket.connectBlocking();

        // Optional: Reagiere auf Befehle vom Backend
        webSocket.setMessageListener(message -> {
            // TODO: JSON parsen und z. B. in Dispatcher geben
            System.out.println("[Client] Backend-Befehl empfangen: " + message);
        });

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(config.get("TOKEN"));

        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setActivity(Activity.customStatus("Updating..."));
        builder.enableIntents(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_PRESENCES
                                );

        //builder.mysqlConnect


        shardManager = builder.build();



        // Register event listeners
        shardManager.addEventListener(new EventListener());

        // Register Slashcommands
        CommandManager manager = new CommandManager();
        manager.add(new HelloWorld());
        manager.add(new Message());
        manager.add(new UserInfo());
        manager.add(new TicketCommand());

        shardManager.addEventListener(manager);

        // Register Buttons
        buttonManager = new ButtonManager();
        //buttonManager.add(new ButtonOpenTicket());
        shardManager.addEventListener(buttonManager);





        System.out.println("Bot is now online!");
    }

    public Dotenv getConfig(){
        return config;
    }

    public ShardManager getShardManager(){
        return shardManager;
    }


    public static void main(String[] args) {
        try {
            client = new Client();
        } catch (LoginException e){
            System.out.println("Login error");
        }
    }

}
