package org.example.service;

import org.example.domain.*;
import org.example.repository.IRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TransactionService {
    private IRepository<Transaction> repositoryTransaction;
    private IRepository<Drug> repositoryDrug;
    private TransactionValidator transactionValidator;
    private UndoRedoManager undoRedoManager;

    public TransactionService (IRepository<Transaction> repositoryTransaction, IRepository<Drug> repositoryDrug, TransactionValidator transactionValidator, UndoRedoManager undoRedoManager) {
        this.repositoryTransaction = repositoryTransaction;
        this.repositoryDrug = repositoryDrug;
        this.transactionValidator = transactionValidator;
        this.undoRedoManager = undoRedoManager;
    }

    /**
     * Adds a transaction.
     * @param id The id of the transaction.
     * @param idDrug The id of the drug.
     * @param cardNumberClient The clients card number.
     * @param soldPiecesNumber The number of pieces (drugs)
     * @param dateAndHour The date of the transaction.
     * @throws Exception If there are validation errors.
     */
    public void addTransaction(int id, int idDrug, int cardNumberClient, int soldPiecesNumber, String dateAndHour) throws Exception {
        Transaction transaction = new Transaction(id, idDrug, cardNumberClient, soldPiecesNumber, dateAndHour);
        this.transactionValidator.validate(transaction, this.repositoryDrug);
        this.repositoryTransaction.create(transaction);
        undoRedoManager.addToUndo(new UndoRedoAddOperation<Transaction>(this.repositoryTransaction, transaction));
    }

    /**
     * Updates an existing transaction.
     * @param id The id of trancsanction to be updated.
     * @param idDrug The id of the drug.
     * @param cardNumberClient The clients card number.
     * @param soldPiecesNumber The number of pieces (drugs)
     * @param dateAndHour The date of the transaction.
     * @throws Exception If there are validation errors.
     */
    public void updateTransaction(int id, int idDrug, int cardNumberClient, int soldPiecesNumber, String dateAndHour) throws Exception {
        Transaction transaction = new Transaction(id, idDrug, cardNumberClient, soldPiecesNumber, dateAndHour);
        this.transactionValidator.validate(transaction, this.repositoryDrug);
        this.repositoryTransaction.update(transaction);
        // TODO: Add update undo redo operation to undoRedoManager
    }

    /**
     * Returns all the transactions.
     * @return A list of transactions
     */
    public List<Transaction> getAll() {
        return this.repositoryTransaction.read();
    }

    /**
     * Deletes a transaction with a given id.
     * @param id The id of transaction to delete.
     * @throws Exception If validation errors occurs.
     */
    public void deleteTransaction(int id) throws Exception {
        Transaction transactionToDelete = this.repositoryTransaction.readOne(id);
        this.repositoryTransaction.delete(id);
        this.undoRedoManager.addToUndo(new UndoRedoDeleteOperation<>(repositoryTransaction, transactionToDelete));
    }

    /**
     * Gets a list of drugs with a given name.
     * @param value The string value to search in transaction fields.
     * @return The list of transactions.
     */

    public List<Transaction> getByValue(String value) {

        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : this.getAll()) {

            if (Integer.toString(transaction.getIdEntity()).toLowerCase().contains(value) ||
                    Integer.toString(transaction.getSoldPiecesNumber()).contains(value) ||
                    Integer.toString(transaction.getIdDrug()).contains(value) ||
                    Integer.toString(transaction.getCardNumberClient()).contains(value) ||
                    transaction.getDateAndHour().contains(value)) {
                result.add(transaction);
            }
        }return result;
    }
    /**
     * Gets a list of all transaction of a given interval.
     * @param startDate The start date of the interval.
     * @param endDate The finish date of the interval.
     * @return
     */
    public List<Transaction> getTransactionInInterval(LocalDateTime startDate, LocalDateTime endDate) {
        List<Transaction> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Transaction transaction:this.repositoryTransaction.read()){
            LocalDateTime dateTime = LocalDateTime.parse(transaction.getDateAndHour(), formatter);
            if (dateTime.equals(startDate)|| dateTime.equals(endDate)||(dateTime.isAfter(startDate) && dateTime.isBefore(endDate))){
                result.add(transaction);
            }
        }
        return result;
    }

    /**
     * Gets a list of the card number by sales.
     * @return The list with card of card number.
     */
    public List<CardClientWithSalesNumber> getCardNumbersBySales() {
        List<CardClientWithSalesNumber> result = new ArrayList<>();
        for (Transaction transaction : this.getAll()) {
            boolean found = false;
            for (CardClientWithSalesNumber cardClientWithSalesNumber : result) {
                if (transaction.getCardNumberClient() == cardClientWithSalesNumber.getCardNumber()) {
                    found = true;
                    int salesNumber = cardClientWithSalesNumber.getSalesNumber();
                    cardClientWithSalesNumber.setSalesNumber(++salesNumber);
                    break;
                }
            }
            if(!found) {
                result.add(new CardClientWithSalesNumber(transaction.getCardNumberClient(),1));
            }
        }
        result.sort(Comparator.comparing(CardClientWithSalesNumber::getSalesNumber).reversed());
        return result;
    }

    /**
     * Delete all transaction of a given interval;
     * @param startDate The interval start date for transaction to delete.
     * @param endDate The interval end date for transaction to delete.
     * @throws Exception If there are any eception;
     */
    public void deleteTransactionInInterval(LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Transaction transaction : this.getAll()) {
            LocalDateTime dateTime = LocalDateTime.parse(transaction.getDateAndHour(), formatter);
            if (dateTime.equals(startDate) || dateTime.isAfter(startDate) && (dateTime.equals(endDate) || dateTime.isBefore(endDate))) {
                this.deleteTransaction(transaction.getIdEntity());
            }
        }
    }


}

