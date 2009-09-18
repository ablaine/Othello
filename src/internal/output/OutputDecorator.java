package internal.output;

/**
 *
 * @author Andrew Blaine
 */
public abstract class OutputDecorator implements IOutput {
	protected final IOutput output;

	public OutputDecorator(IOutput output) {
		this.output = output;
	}

}
