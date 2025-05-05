package ch.greenleaf.features.commands;

import ch.greenleaf.ICommand;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloWorld implements ICommand{

    @Override
    public String getName() {
        return "helloworld";
    }

    @Override
    public String getDescription() {
        return "Prints a Hello World with your name in the chat.";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.STRING, "text", "The message.", true, true));
        data.add(new OptionData(OptionType.CHANNEL, "channel", "The cannel it should get sent to.", true));
        data.add(new OptionData(OptionType.STRING, "emoji", "Add an emoji in front of the message.")
                         .setAutoComplete(true));
        //data.add(new OptionData(OptionType.INTEGER, "ephemeral", "Set if only the user can see it.")
        //                 .addChoice("True", 1)
        //                 .addChoice("False", 0)
        //        );
        return data;
    }


    @Override
    public Map<String, List<?>> getAutocomplete() {
        Map<String, List<?>> autocompleteValues = new HashMap<>();

        List<String> emojis = new ArrayList<>();
        emojis.add("🔒");
        emojis.add("✅");
        emojis.add("🔰");
        emojis.add("🍃");

        autocompleteValues.put("text", List.of("Hello", "Hi", "Hey", "Greetings"));
        autocompleteValues.put("emoji", emojis);
        return autocompleteValues;
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        Channel channel = event.getOption("channel").getAsChannel();
        String emoji = event.getOption("emoji").getAsString();
        String textMessage = event.getOption("text").getAsString();
        //Boolean isEphemeral = event.getOption("ephemeral").getAsBoolean();

        String response = emoji + "You want to send \"" + textMessage + "\" in " + channel.getAsMention();
//        if (isEphemeral){
//            event.reply(response).setEphemeral(true).queue();
//        } else{
//            event.reply(response).queue();
//        }
        event.reply(response).queue();

    }
}
