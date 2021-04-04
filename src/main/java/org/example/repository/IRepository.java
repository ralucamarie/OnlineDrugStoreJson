package org.example.repository;

import org.example.domain.Drug;
import org.example.domain.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IRepository<T extends Entity> {
    //CRUD Methods(Create, Read, Update, Delete);

    /**
     * Adds a entity in to repository.
     * @param entity The entity to be added.
     * @throws Exception if id exists.
     *
     */
    void create(T entity) throws Exception;

    /**
     * A list with all entity.
     * @return all entity.
     */
     List<T> read();


    /**
     * Delete a entity with a given id.
     * @param id The id of the entity to delete.
     * @throws Exception If there is no entity with a given id.
     */
     void delete(int id) throws Exception;

    /**
     * Updates a given entity by its id.
     * @param entity The given entity.
     * @throws Exception If entity does not exist.
     *
     */
     void update(T entity) throws Exception;

    /**
     * Gets a entity with given id;
     * @param id the id.
     * @return The entity with given id, or null if not exist;
     */
     T readOne(int id);

}
