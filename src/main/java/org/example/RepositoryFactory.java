package org.example;

import org.example.domain.Drug;
import org.example.domain.Transaction;
import org.example.repository.GsonFileRepository;
import org.example.repository.IRepository;
import org.example.repository.InMemoryRepository;

public class RepositoryFactory {

    public static final String JSON_REPOSITORY = "JSONRepository";
    public static final String INMEMORY_REPOSITORY = "InMemoryRepository";

    private String repositoryType;

    public RepositoryFactory(String repositoryType) {
        this.repositoryType = repositoryType;
    }

    public IRepository<Drug> getDrugRepository() {
        switch (repositoryType) {
            case JSON_REPOSITORY: return new GsonFileRepository<>("drugs.txt", Drug.class);
            case INMEMORY_REPOSITORY: return new InMemoryRepository<>();
            default: return null;
        }
    }

    public IRepository<Transaction> getTransactionRepository() {
        switch (repositoryType) {
            case JSON_REPOSITORY: return new GsonFileRepository<>("transactions.txt", Transaction.class);
            case INMEMORY_REPOSITORY: return new InMemoryRepository<>();
            default: return null;
        }
    }
}
