package org.example.domain;

import org.example.repository.IRepository;

public class TransactionValidator {
    /**
     * Validates a trnsaction.
     * @param transaction The transaction to validate.
     * @param repositoryDrug  The drug repository, for validating the transaction's drug id.
     * @throws Exception If there are any validation errors.
     */
    //Tranzacția se poate face doar dacă nr. bucăți din tranzacție < nr. bucăți din medicament.
    public void validate(Transaction transaction, IRepository<Drug> repositoryDrug) throws Exception {
        Drug drug = repositoryDrug.readOne(transaction.getIdDrug());
        if (drug == null) {
            throw new Exception("There is no drug with given id!");
        }
        else if (transaction.getPiecesNumber() >= drug.getPiecesNumber()) {
            throw new Exception("The given number of pieces transaction is greater or equal than number pieces of drug!");
        }
    }
}
