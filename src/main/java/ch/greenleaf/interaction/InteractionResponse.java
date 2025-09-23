package ch.greenleaf.interaction;

import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public class InteractionResponse {
    private final String message;
    private final Long channelId;
    private final List<MessageEmbed> embeds;
    private final boolean ephemeral;

    private InteractionResponse(Builder builder) {
        this.message = builder.message;
        this.channelId = builder.channelId;
        this.embeds = builder.embeds;
        this.ephemeral = builder.ephemeral;
    }

    public String getMessage() { return message; }
    public Long getChannelId() { return channelId; }
    public List<MessageEmbed> getEmbeds() { return embeds; }
    public boolean isEphemeral() { return ephemeral; }

    /**
     * Build the Response with different attributes such as a message, embeds, etc.
     */
    public static class Builder {
        private String message = null;
        private Long channelId = null;
        private List<MessageEmbed> embeds = null;
        private boolean ephemeral = false;

        /**
         * Empty Builder constructor
         */
        public Builder (){

        }

        /**
         * Builder constructor for a quick message
         * @param message The message to be added to the response
         */
        public Builder(String message) {
            this.message = message;
        }

        public Builder sendInChannel(Long channelId){
            this.channelId = channelId;
            return this;
        }

        /**
         * Set the embeds for a response
         * @param embeds The list of embeds
         * @return response Builder
         */
        public Builder setEmbeds(List<MessageEmbed> embeds) {
            this.embeds = embeds;
            return this;
        }

        /**
         * Add an embed to a response
         * @param embeds The embed to be added
         * @return response Builder
         */
        public Builder addEmbed(MessageEmbed embeds) {
            this.embeds.add(embeds);
            return this;
        }

        /**
         * Set a reply as ephemeral.
         * [i] This does not work if you don't reply to an interaction
         * @param ephemeral
         * @return
         */
        public Builder isEphemeral(boolean ephemeral) {
            this.ephemeral = ephemeral;
            return this;
        }

        public InteractionResponse build() {
            return new InteractionResponse(this);
        }
    }
}
