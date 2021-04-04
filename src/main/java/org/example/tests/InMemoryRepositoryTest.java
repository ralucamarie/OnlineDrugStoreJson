package org.example.tests;

import org.example.domain.Drug;
import org.example.repository.IRepository;
import org.example.repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;



class InMemoryRepositoryTest {

 /**   @org.junit.jupiter.api.Test
    void createShouldValidateAnIdAndAddTheObject() throws Exception {
        //set up(arrange) cream lucrurile necesare pt test
        InMemoryRepository<Drug> inMemoryRepository = new InMemoryRepository<>();
        Drug drug1 = new Drug(1, "test1", "Terapia", 5.62f, true, 4);
        Drug drug2 = new Drug(2, "test2", "Farmacia", 10.8f, true, 4);
        Drug drug3 = new Drug(1, "test3", "Farmacia", 34.6f, true, 4);

        //act:Facem actiunile necesare testului, niste adaugari.
        inMemoryRepository.create(drug1);


        //assert: Sa verifice gaca obiectul a fost adugat.
        assertEquals(1, inMemoryRepository.read().size(), "Dupa daugarea unui medicament, read().size != 1");
        inMemoryRepository.create(drug2);
        assertEquals(2, inMemoryRepository.read().size(), "Dupa daugarea a doua medicamente, read().size != 2");

        //todo:assertThrows();
        try {
            inMemoryRepository.create(drug3);
            fail("Adaugarea unui medicament cu id existent nu da exceptie");
        } catch (Exception exception) {
            assertEquals(2, inMemoryRepository.read().size(), "S-a adaugat un medicament cu id existent");
        }
    }

    @org.junit.jupiter.api.Test
    void readOneShouldCorrectlyReturnDrug() throws Exception {
        //set up(arrange) cream lucrurile pentru teeste
        InMemoryRepository<Drug> inMemoryRepository = new InMemoryRepository<>();

        for (int i = 1; i <= 20; i++) {
            Drug drug = new Drug(i, "Drug" + i, "Farmacia", 5.62f, true, 4);
            inMemoryRepository.create(drug);
        }

        for (int i = 1; i <= 20; i++) {
            Drug foundById = inMemoryRepository.readOne(i);
            assertEquals(i, foundById.getIdEntity());
        }
        assertNull(inMemoryRepository.readOne(100), "Nu afost adaugat nici medicament cu id-ul 100");
    }

    @org.junit.jupiter.api.Test
    void deleteShouldEraseADrugObjectAndValidateId() throws Exception {
        InMemoryRepository<Drug> inMemoryRepository = new InMemoryRepository<>();

        for (int i = 1; i <= 20; i++) {
            Drug drug = new Drug(i, "drug" + 1, "Farmacia", 5.62f, true, 4);
            inMemoryRepository.create(drug);
        }

        for (int i = 1; i <= 20; i++) {
            inMemoryRepository.delete(i);
            Drug foundById = inMemoryRepository.readOne(i);
            assertNull(foundById);
            assertEquals(20 - i,inMemoryRepository.read().size());
        }

        try {
            inMemoryRepository.delete(5);
            fail("Stergerea unui medicament cu id existent nu da exceptie");
        } catch (Exception exception) {
            assertEquals(0, inMemoryRepository.read().size(), "Exista medicamente nesterse!");
        }
    }
    @org.junit.jupiter.api.Test
    void updateShouldValidateTheIdAndUpdateTheObject() throws Exception {
        InMemoryRepository<Drug> inMemoryRepository = new InMemoryRepository<>();
        Drug drug1 = new Drug(1, "drug1", "Terapia", 5.62f, true, 4);
        Drug drug2 = new Drug(1, "drugssss", "Terapiasss", 6.62f, false, 8);
        Drug drug3 = new Drug(5, "drugssss", "Terapiasss", 6.62f, false, 8);
        inMemoryRepository.create(drug1);

        Drug initialDrug = inMemoryRepository.readOne(1);
        assertEquals(drug1.toString(), initialDrug.toString());
        inMemoryRepository.update(drug2);

        Drug drugAfterUpdate = inMemoryRepository.readOne(1);
        assertEquals(drug2.toString(), drugAfterUpdate.toString());

        try {
            inMemoryRepository.update(drug3);
            fail("Updatarea unui medicament cu id inexistent nu da exceptie");
        } catch (Exception exception) {
            Drug notExistingDrug = inMemoryRepository.readOne(5);
            assertNull(notExistingDrug, "Exista un medicament care nu a fost inserat");
        }

    }**/
}
