package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.example.domain.Transaction;

import java.util.List;

public class TransactionDateSearchResultsController {
    public TableView tblTransactions;

    public void setResultsList(List<Transaction> searchResults) {
        ObservableList<Transaction> results = FXCollections.observableArrayList();
        results.addAll(searchResults);
        tblTransactions.setItems(results);
    }
}
