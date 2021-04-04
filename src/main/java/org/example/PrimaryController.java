package org.example;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.domain.Drug;
import org.example.domain.Transaction;
import org.example.service.DrugService;
import org.example.service.TransactionService;
import org.example.service.UndoRedoManager;

public class PrimaryController {
    public Button btnAddTransaction;
    public Button btnUpdateTransaction;
    public Button btnDeleteSelectedTransaction;
    public TableColumn colIdTransaction;
    public TableColumn colIdDrugTransaction;
    public TableColumn colClientCard;
    public TableColumn colDateAndHour;
    public TableColumn colNumberOfPieces;
    public TextField txtTransactionId;
    public TextField txtIdDrug;
    public TextField txtClientCard;
    public TextField txtDateAndHour;
    public TextField txtSoldPiecesNumber;
    public TextField txtSearchText;
    public Button btnFullTextSearch;
    public TextField txtDateHourStart;
    public Button btnAddDrug;
    public Button btnUpdateDrug;
    public Button btnDeleteSelectedDrug;
    public TableColumn colName;
    public TableColumn colProducer;
    public TableColumn colPrice;
    public TableColumn colNeedsPrescription;
    public TableColumn colPiecesNumber;
    public TableColumn colIdDrug;
    private DrugService drugService;
    private TransactionService transactionService;
    private ObservableList<Drug> drugs = FXCollections.observableArrayList();
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private UndoRedoManager undoRedoManager;

    public TableView tblDrug;
    public TableView tblTransactions;

    public TextField txtDrugId;
    public TextField txtDrugName;
    public TextField txtProducer;
    public TextField txtDrugPrice;
    public CheckBox txtNeedsPrescription;
    public TextField txtNumberOfPieces;
    private TextField txtDateHourEnd;


    @FXML
    private void initialize() {


        Platform.runLater(() -> {
            drugs.addAll(drugService.getAll());
            transactions.addAll(transactionService.getAll());
            tblDrug.setItems(drugs);
            tblTransactions.setItems(transactions);
        });
    }

    public void setServices(DrugService drugService, TransactionService transactionService, UndoRedoManager undoRedoManager) {
        this.drugService = drugService;
        this.transactionService = transactionService;
        this.undoRedoManager = undoRedoManager;
    }

    public void btnAddDrugClick(ActionEvent actionEvent) throws Exception {
        try {

            int id = Integer.parseInt(txtDrugId.getText());
            String name = txtDrugName.getText();
            String producer = txtProducer.getText();
            float price = Float.parseFloat(txtDrugPrice.getText());
            boolean needsPrescription = txtNeedsPrescription.isSelected();
            int piecesNumber = Integer.parseInt(txtNumberOfPieces.getText());

            drugService.addDrug(id,name,producer,price,needsPrescription,piecesNumber);

            refreshDrugList();
        } catch (RuntimeException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnUpdateDrugClick(ActionEvent actionEvent) throws Exception {
        try {
            int id = Integer.parseInt(txtDrugId.getText());
            String name = txtDrugName.getText();
            String producer = txtProducer.getText();
            float price = Float.parseFloat(txtDrugPrice.getText());
            boolean needsPrescription = txtNeedsPrescription.isSelected();
            int piecesNumber = Integer.parseInt(txtNumberOfPieces.getText());

            drugService.updateDrug(id, name, producer, price,needsPrescription,piecesNumber);

            refreshDrugList();
        } catch (RuntimeException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnDeleteSelectedDrugClick(ActionEvent actionEvent) throws Exception {
        Drug selectedDrug = (Drug) tblDrug.getSelectionModel().getSelectedItem();
        if (selectedDrug != null) {
            drugService.delete(selectedDrug.getIdEntity());
            refreshDrugList();
        }
    }

    private void refreshDrugList() {
        drugs.clear();
        drugs.addAll(drugService.getAll());
    }

    private void refreshTransactionList() {
        transactions.clear();
        transactions.addAll(transactionService.getAll());
    }

    public void btnAddTransactionClick(ActionEvent actionEvent) {
        try {//int idEntity, int idDrug, int cardNumberClient, int piecesNumber, LocalDateTime dateAndHou
            int id = Integer.parseInt(txtTransactionId.getText());
            int idDrug = Integer.parseInt(txtIdDrug.getText());
            int clientCard = Integer.parseInt(txtClientCard.getText());
            int soldPiecesNumber = Integer.parseInt(txtSoldPiecesNumber.getText());
            String dateAndHour = txtDateAndHour.getText();
            transactionService.addTransaction(id, idDrug, clientCard,soldPiecesNumber,dateAndHour);
            refreshTransactionList();
        } catch (Exception ex) {
            Common.showErrorMessage(ex.getMessage());
        }
    }

    public void btnUpdateTransactionClick(ActionEvent actionEvent) throws Exception {
        try {
            int id = Integer.parseInt(txtTransactionId.getText());
            int idDrug = Integer.parseInt(txtDrugId.getText());
            int clientCard = Integer.parseInt(txtClientCard.getText());
            int piecesNumber = Integer.parseInt(txtSoldPiecesNumber.getText());

            String dateAndHour = txtDateAndHour.getText();

            transactionService.updateTransaction(id,idDrug,clientCard,piecesNumber,dateAndHour);

            refreshDrugList();
        } catch (RuntimeException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnDeleteSelectedTransactionClick(ActionEvent actionEvent) throws Exception {
        Transaction selectedDTransaction = (Transaction) tblTransactions.getSelectionModel().getSelectedItem();
        if (selectedDTransaction != null) {
            transactionService.deleteTransaction(selectedDTransaction.getIdEntity());
            refreshTransactionList();
        }
    }

    public void btnFullTextSearchClick(ActionEvent actionEvent) {
        List<Drug> drugResults = drugService.getByValue(txtSearchText.getText());
        List<Transaction> transactionResults = transactionService.getByValue(txtSearchText.getText());
        drugs.clear();
        drugs.addAll(drugResults);

        transactions.clear();
        transactions.addAll(transactionResults);
    }

    // 15.02.2021 20:00
    // 23.03.2021 16:00
    public void btnTransactionDateHourSearchClick(ActionEvent actionEvent) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime start = LocalDateTime.parse(txtDateHourStart.getText(), formatter);
            LocalDateTime end = LocalDateTime.parse(txtDateHourEnd.getText(), formatter);
            List<Transaction> searchResults = transactionService.getTransactionInInterval(start,end);

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("transactionDateSearchResults.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            TransactionDateSearchResultsController resultsController = fxmlLoader.getController();
            resultsController.setResultsList(searchResults);


            stage.setTitle("Transaction date search results");
            stage.setScene(scene);
            stage.showAndWait();

        } catch (RuntimeException | IOException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnShowDrugsWithNumberOfTransactionsClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("drugsWithNumberOfTransactions.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            DrugsWithNumberOfTransactions resultsController = fxmlLoader.getController();
            resultsController.setDrugService(this.drugService);

            stage.setTitle("Drugs with sales");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException iex) {
            Common.showErrorMessage(iex.getMessage());
        }
        }


    public void btnUndoClick(ActionEvent actionEvent) throws Exception {
        this.undoRedoManager.doUndo();

        this.refreshDrugList();
        this.refreshTransactionList();
    }

    public void btnRedoClick(ActionEvent actionEvent) throws Exception {
        this.undoRedoManager.doRedo();

        this.refreshDrugList();
        this.refreshTransactionList();
    }




}
