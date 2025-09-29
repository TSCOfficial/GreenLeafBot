package ch.greenleaf;

import ch.greenleaf.common.listeners.EventListener;
import ch.greenleaf.features.commands.HelloWorld;
import ch.greenleaf.features.commands.Message;
import ch.greenleaf.features.commands.TicketCommand;
import ch.greenleaf.features.commands.UserInfo;
import ch.greenleaf.interactions.ButtonContext;
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

    public final Dotenv config; // todo SETUP .env FILE !!!

    public final ShardManager shardManager;

    public static Client client;

    public static void main(String[] args) {
        try {
            client = new Client();
        } catch (LoginException | URISyntaxException | InterruptedException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public Client() throws LoginException, URISyntaxException, InterruptedException {
        config = Dotenv.configure().load();

        URI backendUri = new URI("ws://localhost:8080/ws/bot");
        webSocket = new WebSocketClientHandler(backendUri);
        webSocket.connectBlocking();

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(config.get("TOKEN"));

        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setActivity(Activity.customStatus("Updating..."));
        builder.enableIntents(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_PRESENCES
                                );

        shardManager = builder.build();

        // Register event listeners
        shardManager.addEventListener(new EventListener());

        // Register Slashcommands
        CommandManager manager = new CommandManager();
        manager.add(new HelloWorld());
        manager.add(new Message());
        manager.add(new UserInfo());
        manager.add(new TicketCommand());

        BackendDispatcher.setCommandManager(manager);

        shardManager.addEventListener(manager);

        // Register Buttons
        //buttonManager = new ButtonManager();
        //buttonManager.add(new ButtonOpenTicket());
        shardManager.addEventListener(new ButtonContext());





        System.out.println("Bot is now online!");
    }

    public Dotenv getConfig(){
        return config;
    }

    public ShardManager getShardManager(){
        return shardManager;
    }

}
