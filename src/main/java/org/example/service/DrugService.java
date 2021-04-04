package org.example.service;

import org.example.DrugsWithNumberOfTransactions;
import org.example.domain.*;
import org.example.repository.IRepository;

import java.util.*;

public class DrugService {
    private IRepository<Drug> repository;
    private DrugValidator drugValidator;
    private IRepository<Transaction> transactionRepository;
    private UndoRedoManager undoRedoManager;

    /**
     * Instantiate a drug
     * @param repository The repository for drug.
     * @param drugValidator The validator for drug.
     * @param transactionRepository The transaction repository;
     */
    public DrugService(IRepository<Drug> repository, DrugValidator drugValidator, IRepository<Transaction> transactionRepository, UndoRedoManager undoRedoManager) {
        this.repository = repository;
        this.drugValidator = drugValidator;
        this.transactionRepository = transactionRepository;
        this.undoRedoManager = undoRedoManager;
    }

    /**
     * Adds drug.
     * @param id The drug id, must be unique;
     * @param name The name of drug.
     * @param producer The producer of the drug.
     * @param price The price of the drug, must be > 0.
     * @param needsPrescription If set to true, the drug needs prescription.
     * @param piecesNumber The number of drug pieces.
     */
    public  void  addDrug(int id, String name, String producer, float price, boolean needsPrescription, int piecesNumber) throws Exception {
        //Cream medicamentul(drug),facem un obiect nou de tipul drug.

        Drug drug = new Drug(id, name, producer, price, needsPrescription, piecesNumber);
        this.drugValidator.validate(drug);
        this.repository.create(drug);
        this.undoRedoManager.addToUndo(new UndoRedoAddOperation<>(this.repository, drug));
    }

    /**
     * Update a given drug by id.
     * @param id The id of drug witch will be update.
     * @param name The name of drug.
     * @param producer The producer of the drug.
     * @param price The price of drug.
     * @param needsPrescription
     * @param piecesNumber The number of drug pieces.
     * @throws Exception
     */
    public void updateDrug(int id, String name, String producer, float price, boolean needsPrescription, int piecesNumber) throws Exception {
        Drug drug = new Drug(id, name, producer, price, needsPrescription, piecesNumber);
        this.drugValidator.validate(drug);
        this.repository.update(drug);
        // TODO: Add update undo redo operation to undoRedoManager
    }

    /**
     * Gets a list of all drugs.
     * @return A list of drugs.
     */
    public List<Drug> getAll() {
        return this.repository.read();
    }

    /**
     * Delete a drug with a given id.
     * @param id The id of drug to delete.
     * @throws Exception If drogs does not exist.
     */
    public void delete(int id) throws Exception {
        Drug drugToDelete = this.repository.readOne(id);
        this.repository.delete(id);
        this.undoRedoManager.addToUndo(new UndoRedoDeleteOperation<>(this.repository, drugToDelete));
    }

    /**
     * Gets a list of drugs with a given name.
     * @param value The string value to search of drugs;
     * @return The list of drugs.
     */
    public List<Drug> getByValue(String value) {
        List<Drug> result = new ArrayList<>();
        for (Drug drug : this.getAll()) {
            if (drug.getName().toLowerCase().contains(value) ||
                    Integer.toString(drug.getPiecesNumber()).contains(value) ||
                    Integer.toString(drug.getIdEntity()).contains(value) ||
                    Float.toString(drug.getPrice()).contains(value) ||
                    drug.getProducer().contains(value)) {
                result.add(drug);
            }
        }
        return result;
    }
            /** original method
            if (value.toLowerCase().equals(drug.getName().toLowerCase()) ||
                    value.toLowerCase().equals(drug.getProducer().toLowerCase()) ||
                    value.toLowerCase().equals(Integer.toString(drug.getPiecesNumber()).toLowerCase()) ||
                    value.toLowerCase().equals(Float.toString(drug.getPrice()).toLowerCase())) {
                result.add(drug);
            }
        }**/



    /**
     * Gets the list of drug ordered descending by sales number
     * @return The list or ordered drug;
     */
    public List<DrugWithSalesNumber> getOrderedBySalesNumber() {
        List<DrugWithSalesNumber> result = new ArrayList<>();
        for (Drug drug : this.getAll()) {
            int transactionCount = 0;
            for (Transaction transaction : this.transactionRepository.read()) {
                if(drug.getIdEntity() == transaction.getIdDrug()) {
                    transactionCount++;
                }
            }
            DrugWithSalesNumber drugWithSalesNumber = new DrugWithSalesNumber(drug.getIdEntity(), drug.getName(), transactionCount);
            result.add(drugWithSalesNumber);
        }
        result.sort(Comparator.comparing(DrugWithSalesNumber::getSalesNumber).reversed());
        return result;
    }

    /**
     * The drugs after raise percent is added.
     * @param raisePercent The percent to be added to the drug price.
     * @param minPrice Only the drugs with price lower than this value will be updated
     */
    public void riseDrugPriceByPercent(float raisePercent, float minPrice) {
        for (Drug drug : this.getAll()) {
            float priceBeforeRaise = drug.getPrice();
            if (priceBeforeRaise < minPrice) {
                // drug.price = drug.price + raisePercent/100 * drug.getPrice();
                drug.setPrice(priceBeforeRaise + raisePercent / 100 * priceBeforeRaise);
            }
        }
    }

    public List<DrugWithSalesNumber> getDrugsWithSalesNumber() {
        Map<Integer, Integer> drugsWithNumberOfTransactions = new HashMap<>();

        for (Transaction t : this.transactionRepository.read()) {
            int idDrug = t.getIdDrug();
            if (!drugsWithNumberOfTransactions.containsKey(idDrug)) {
                drugsWithNumberOfTransactions.put(idDrug, 1);
            } else {
                drugsWithNumberOfTransactions.put(idDrug, drugsWithNumberOfTransactions.get(idDrug) + 1);
            }
        }

        List<DrugWithSalesNumber> results = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : drugsWithNumberOfTransactions.entrySet()) {
            int idDrug = entry.getKey();
            int numberOfTransactions = entry.getValue();

            Drug drug = this.repository.readOne(idDrug);

            results.add(new DrugWithSalesNumber(idDrug, drug.getName(), numberOfTransactions));
        }

//        results.sort(Comparator.comparing(PrajituraWithNumberOfTransactions::getNumberOfTransactions).reversed());

//        results.sort((x, y) -> x.getNumberOfTransactions() - y.getNumberOfTransactions());
        results.sort((x, y) -> {
            return -(x.getSalesNumber() - y.getSalesNumber());
        });
        return results;
    }
}
