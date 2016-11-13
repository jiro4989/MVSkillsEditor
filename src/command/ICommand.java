package command;

public interface ICommand {
	public void invoke();
	public void undo();
	public void redo();
}
