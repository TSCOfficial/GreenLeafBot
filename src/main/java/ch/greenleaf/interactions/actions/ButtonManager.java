//package ch.greenleaf.interaction.actions;
//
//import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
//import net.dv8tion.jda.api.hooks.ListenerAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ButtonManager extends ListenerAdapter {
//
//    private List<ch.greenleaf.component.Button> buttons = new ArrayList<>();
//
//    @Override
//    public void onButtonInteraction(ButtonInteractionEvent event) {
//        System.out.println("Button pressed: " + event.getButton().getId());
//        System.out.println("Current saved buttons: " + buttons);
//        String buttonId = event.getButton().getId();
//        for (ch.greenleaf.component.Button button : buttons){ // TODO fetch data directly from database (DTO mapping)
//            System.out.println(buttonId);
//            System.out.println(event.getButton().getId());
//            if (buttonId.equals(event.getButton().getId())){
//                System.out.println("same ID");
//                button.action(event);
//                return;
//            }
//        }
//    }
//
//    public void add(ch.greenleaf.component.Button button) {
//        buttons.add(button);
//    }
//
//}
