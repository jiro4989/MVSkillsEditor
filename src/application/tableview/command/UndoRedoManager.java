package application.tableview.command;

import java.util.Stack;

public class UndoRedoManager {
  private Stack<ICommand> undoStack = new Stack<>();
  private Stack<ICommand> redoStack = new Stack<>();

  public void invokeNonStack(ICommand command) {
    command.invoke();
  }

  public void invoke(ICommand command) {
    command.invoke();
    redoStack.clear();
    undoStack.push(command);
  }

  public void undo() {
    if (!undoStack.isEmpty()) {
      ICommand command = undoStack.pop();
      command.undo();
      redoStack.push(command);
    }
  }

  public void redo() {
    if (!redoStack.isEmpty()) {
      ICommand command = redoStack.pop();
      command.redo();
      undoStack.push(command);
    }
  }

  @Override
  public String toString() {
    String sep = System.getProperty("line.separator");

    StringBuilder undoSb = new StringBuilder();
    undoStack.forEach(u -> undoSb.append(u.toString() + sep));

    StringBuilder redoSb = new StringBuilder();
    redoStack.forEach(u -> redoSb.append(u.toString() + sep));

    return "UndoRedoManager: " + sep + "undoStack: " + sep + undoSb.toString() + "redoStack: " + sep
        + redoSb.toString();
  }
}
