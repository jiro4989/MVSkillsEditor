package application.tableview.command;

import java.util.LinkedList;

public class UndoRedoManager {
  private LinkedList<ICommand> undoStack = new LinkedList<>();
  private LinkedList<ICommand> redoStack = new LinkedList<>();

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

  public void clear() {
    undoStack.clear();
    redoStack.clear();
  }
}
