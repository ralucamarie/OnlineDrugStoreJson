package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.domain.Drug;
import org.example.domain.DrugValidator;
import org.example.domain.Transaction;
import org.example.domain.TransactionValidator;
import org.example.repository.IRepository;
import org.example.repository.InMemoryRepository;
import org.example.service.DrugService;
import org.example.service.TransactionService;
import org.example.service.UndoRedoManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene mainScene;

    //private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        Parent parentfxml = fxmlLoader.load();
        this.mainScene = new Scene(parentfxml,640,480);

        RepositoryFactory repositoryFactory = new RepositoryFactory(RepositoryFactory.INMEMORY_REPOSITORY);
        IRepository<Drug> drugRepository = repositoryFactory.getDrugRepository();
        IRepository<Transaction> transactionRepository = repositoryFactory.getTransactionRepository();

        TransactionValidator transactionValidator = new TransactionValidator();
        DrugValidator drugValidator = new DrugValidator();
        UndoRedoManager undoRedoManager = new UndoRedoManager();

        DrugService drugService = new DrugService(drugRepository,drugValidator,transactionRepository,undoRedoManager);
        TransactionService transactionService = new TransactionService(transactionRepository,drugRepository, transactionValidator, undoRedoManager);
        drugService.addDrug(1, "Paracetamol", "Bayern", 14.5f, false, 5);
        drugService.addDrug(2, "Paracetamol", "Terapia", 10.5f, true, 2);
        drugService.addDrug(3, "Nurofen", "Bayern", 40.8f, false, 10);
        drugService.addDrug(4, "Coldrex", "Bayern", 37.3f, false, 3);
        drugService.addDrug(5, "Piafen", "Bayern", 35.8f, true, 7);

    try {
    transactionService.addTransaction(1, 1, 23454545, 4,"21/01/2021 10:00");
    transactionService.addTransaction(5, 1, 23454545, 4, "21/01/2021 10:00");
    transactionService.addTransaction(6, 1, 23454545, 4, "21/01/2021 10:00");
    transactionService.addTransaction(2, 2, 12343786, 1, "21/02/2021 13:00");
    transactionService.addTransaction(3, 3, 49877654, 4, "21/03/2021 12:00");
    transactionService.addTransaction(4, 4, 23124545, 2, "15/01/2021 05:00");
    transactionService.addTransaction(7, 4, 23124545, 2, "15/01/2021 05:00");

    } catch (Exception exception){

    exception.printStackTrace();
    }

        PrimaryController primaryController = fxmlLoader.getController();
        primaryController.setServices(drugService, transactionService, undoRedoManager);

        stage.setTitle("Drug Store");
        stage.setScene(this.mainScene);
        stage.show();

    }



    public static void main(String[] args) {
        launch();
    }

}