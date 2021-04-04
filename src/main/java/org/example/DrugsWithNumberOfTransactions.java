package org.example;
import org.example.domain.DrugWithSalesNumber;
import org.example.service.DrugService;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.example.service.DrugService;

public class DrugsWithNumberOfTransactions {
    public TableView tblDrugsWithTransactions;
    private DrugService drugService;

    public void setDrugService(DrugService drugService) {
        this.drugService = drugService;
    }

    @FXML
    private void initialize() {

        Platform.runLater(() -> {

            ObservableList<DrugWithSalesNumber> drugsWithNumberOfTransactions = FXCollections.observableArrayList();
            drugsWithNumberOfTransactions.addAll(drugService.getDrugsWithSalesNumber());
            tblDrugsWithTransactions.setItems(drugsWithNumberOfTransactions);
        });
    }
}
