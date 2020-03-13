public class TextMessage {
	private String name;
    private String message;

	/**
	 * Default TextMessage constructor.
	 * @param name - name of the user that created this message.
	 * @param message - text of the message
	 */
    public TextMessage(String name, String message) {
    	this.name = name;
    	this.message = message;
    }

	/**
	 * Copy constructor for TextMessage objects.
	 * @param message - TextMessage to copy.
	 */
    public TextMessage(TextMessage message) {
    	this(message.name, message.message);
    }

    public String getName() {
    	return name;
    }

    public String getMessage() {
    	return message;
    }
}
