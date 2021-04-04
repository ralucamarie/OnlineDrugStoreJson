package org.example.service;

import org.example.domain.IUndoRedoOperation;
import org.example.domain.UndoRedoDeleteOperation;

import java.util.Stack;

public class UndoRedoManager {
    private final Stack<IUndoRedoOperation> undoOperations = new Stack<>();
    private final Stack<IUndoRedoOperation> redoOperations = new Stack<>();

    public void doUndo() throws Exception {
        if (!undoOperations.isEmpty()) {
            IUndoRedoOperation operation = undoOperations.pop();
            operation.doUndo();
            redoOperations.push(operation);
        }
    }

    public void doRedo() throws Exception {
        if (!redoOperations.isEmpty()) {
            IUndoRedoOperation operation = redoOperations.pop();
            operation.doRedo();
            undoOperations.push(operation);
        }
    }

    public void addToUndo(IUndoRedoOperation operation) {
        undoOperations.push(operation);
        redoOperations.clear();
    }

/*    public void addRedoOperation(IUndoRedoOperation operation) {
        redoOperations.push(operation);
    }*/

}
