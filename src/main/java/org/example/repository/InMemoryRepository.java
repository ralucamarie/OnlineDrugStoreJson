package org.example.repository;

import org.example.domain.Drug;
import org.example.domain.Entity;
import org.example.repository.IRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<T extends Entity> implements IRepository<T> {
    private Map<Integer, T> storage = new HashMap<>();

    //CRUD Methods(Create, Read, Update, Delete);

    /**
     * Adds a entity in to repository.
     * @param entity The entity to be added.
     * @throws Exception if id exists.
     *
     */
    public void create(T entity) throws Exception{
        if (this.storage.containsKey(entity.getIdEntity())) {
            throw new Exception("This id already exists!");
        }
        storage.put(entity.getIdEntity(),entity);
    }

    /**
     * A list with all entity.
     * @return all entity.
     */
    public List<T> read() {
        return new ArrayList<>(this.storage.values());
    }

    /**
     * Delete a entity with a given id.
     * @param id The id of the entity to delete.
     * @throws Exception If there is no entity with a given id.
     */
   public void delete(int id) throws Exception {
        if (!this.storage.containsKey(id)) {
            throw new Exception("There is no drugs with given id");
        }
        this.storage.remove(id);
    }

    /**
     * Updates a given entity by its id.
     * @param entity The given entity.
     * @throws Exception If entity does not exist.
     *
     */
    public void update(T entity) throws Exception {
        if (!this.storage.containsKey(entity.getIdEntity())) {
            throw new Exception("There is no drug with given id to be update");
        }
        this.storage.put(entity.getIdEntity(), entity);
    }

    /**
     * Gets a entity with given id;
     * @param id the id.
     * @return The entity with given id, or null if not exist;
     */
    public T readOne(int id) {
       return this.storage.get(id);
    }
}

