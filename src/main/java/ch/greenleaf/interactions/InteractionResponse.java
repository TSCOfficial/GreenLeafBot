package ch.greenleaf.interactions;

import ch.greenleaf.components.button.Button;
import ch.greenleaf.components.message.Message;

public record InteractionResponse(
	Message message,
	Button button
) {
	public InteractionResponse(Builder builder) {
		this(
			builder.message,
			builder.button
		);
	}
	
	/**
	 * Build the Response with different attributes such as a message, embeds, etc.
	 */
	public static class Builder {
		private Message message;
		private Button button;
		
		/**
		 * Handle a message
		 * @param message Message object
		 * @return Interaction response
		 */
		public Builder message(Message message) {
			this.message = message;
			return this;
		}
		
		/**
		 * Handle a button
		 * @param button Button object
		 * @return Interaction response
		 */
		public Builder button(Button button) {
			this.button = button;
			return this;
		}
		
		
		public InteractionResponse build() {
			return new InteractionResponse(this);
		}
	}
}
