package ch.greenleaf;

import ch.greenleaf.commands.*;
import ch.greenleaf.common.listeners.EventListener;
import ch.greenleaf.interactions.ButtonContext;
import ch.greenleaf.interactions.SlashContext;
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
        URI backendUri = new URI(getConfig().get("WS_URI"));
        webSocket = new WebSocketClientHandler(backendUri);
        webSocket.connectBlocking();

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(getConfig().get("TOKEN"));

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
		SlashCommandManager.add(new HelloWorld());
		SlashCommandManager.add(new Message());
		SlashCommandManager.add(new UserInfo());
		SlashCommandManager.add(new TicketCommand());
		SlashCommandManager.add(new Trigger()); // TODO remove when finished testing

        shardManager.addEventListener(new SlashCommandManager());

        // Register Buttons
        //buttonManager = new ButtonManager();
        //buttonManager.add(new ButtonOpenTicket());
        shardManager.addEventListener(new ButtonContext());
		shardManager.addEventListener(new SlashContext());





        System.out.println("Bot is now online!");
    }

    public static Dotenv getConfig(){
        return Dotenv.configure().load();
    }

    public ShardManager getShardManager(){
        return shardManager;
    }

}
