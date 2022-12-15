package oh_heaven.game;
// workshop 15 team 10

/**
 * An exception thrown when a player breaks a rule
 */
@SuppressWarnings("serial")
public class BrokeRuleException extends Exception {
	public BrokeRuleException(String violation) {
		super(violation);
	}
}
