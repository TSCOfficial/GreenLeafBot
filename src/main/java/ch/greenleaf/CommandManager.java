package ch.greenleaf;

import ch.greenleaf.features.commands.ICommand;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manage slash commands
 */
public class CommandManager extends ListenerAdapter {
	
	// Existing slash commands
    private List<ICommand> commands = new ArrayList<>();
	
	/**
	 * Load all existing slash commands
	 * @param event Ready event firing at Client start-up
	 */
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        for (ICommand command : commands){
            if(command.getOptions() == null) {
                event.getJDA().upsertCommand(
                        command.getName(),
                        command.getDescription()).queue();
            } else {
                event.getJDA().upsertCommand(
                        command.getName(),
                        command.getDescription()
                                            )
                     .addOptions(command.getOptions()).queue();
            }
//            event.getJDA().updateCommands().queue();

            System.out.println("Loading " + command.getName() + " slashcommand.");
        }

    }

    /**
     * Gets triggered as soon as a slash command is executed
     * @param event SlashCommandInteractionEvent
     */
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for (ICommand command : commands){
            if (command.getName().equals(event.getName())){
                command.execute(event);
                return;
            }
        }
    }
	
	/**
	 * Execute the autocompletion for a slash command
	 * @param event When an autocompletion is triggered by discord
	 */
    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {
        for (ICommand command : commands) {
            if (command.getName().equals(event.getName())) {
                String focusedOptionName = event.getFocusedOption().getName();
                List<?> choices = command.getAutocomplete().getOrDefault(focusedOptionName, List.of());

                List<Command.Choice> options = choices.stream()
                        .filter(
                                choice -> choice.toString().startsWith(event.getFocusedOption().getValue()))
                        .map(choice -> {
                            if (choice instanceof String) {
                                return new Command.Choice((String) choice, (String) choice);
                            } else if (choice instanceof Integer) {
                                return new Command.Choice(choice.toString(), (Integer) choice);
                            } else if (choice instanceof Double) {
                                return new Command.Choice(choice.toString(), (Double) choice);
                            }
                            return null;
                        })
                        .filter(choice -> choice != null)
                        .collect(Collectors.toList());

                event.replyChoices(options).queue();
            }
        }
    }
	
	
	/**
	 * Add a slashcommand
	 * @param command
	 */
    public void add(ICommand command) {
        commands.add(command);
    }
}
