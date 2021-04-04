package org.example.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.example.domain.Entity;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


    public class GsonFileRepository<T extends Entity> implements IRepository<T> {

        private String filename;
        private Class<T> classType;

        public GsonFileRepository(String filename, Class<T> classType) {
            this.filename = filename;
            this.classType = classType;
        }

        private List<T> readFile() {
            Gson gson = new Gson();

            try {
                JsonReader reader = new JsonReader(new FileReader(this.filename));
//            Type listType = new TypeToken<ArrayList<T>>() {}.getType();
                // https://stackoverflow.com/questions/14139437/java-type-generic-as-argument-for-gson
                // after google search: typetoken generic type
                Type listType = TypeToken.getParameterized(List.class, classType).getType();
                List<T> result = gson.fromJson(reader, listType);

                if (result == null) {
                    return new ArrayList<>();
                }
                return result;
            } catch (FileNotFoundException e) {
                return new ArrayList<>();
            }
        }

        private void writeFile(List<T> entities) throws RepositoryException {
            Gson gson = new Gson();
            try (JsonWriter writer = new JsonWriter(new FileWriter(this.filename))) {
                gson.toJson(entities, List.class, writer);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RepositoryException("IO Exception!");
            }
        }

        @Override
        public void create(T entity) throws RepositoryException {
            List<T> entities = this.readFile();
            for (T existingEntity : entities) {
                if (existingEntity.getIdEntity() == entity.getIdEntity()) {
                    throw new RepositoryException("The id already exists!");
                }
            }

            entities.add(entity);
            writeFile(entities);
        }

        @Override
        public T readOne(int id) {
            List<T> entities = this.readFile();
            for (T existingEntity : entities) {
                if (existingEntity.getIdEntity() == id) {
                    return existingEntity;
                }
            }

            return null;
        }

        @Override
        public List<T> read() {
            return readFile();
        }

        @Override
        public void update(T entity) throws RepositoryException {
            List<T> entities = this.readFile();
        /*
        i = 9;
        afisare(i++); -> afiseaza 9
        afisare(i); -> afiseaza 10

        -------------------------------

        i = 9;
        afisare(++i); -> afiseaza 10
        afisare(i); -> afiseaza 10
         */
            for (int i = 0; i < entities.size(); ++i) {
                if (entities.get(i).getIdEntity() == entity.getIdEntity()) {
                    entities.set(i, entity);
                    writeFile(entities);
                    return;
                }
            }

            throw new RepositoryException("The given id does not exist!");
        }

        @Override
        public void delete(int id) throws RepositoryException {
            List<T> entities = this.readFile();
            for (int i = 0; i < entities.size(); ++i) {
                if (entities.get(i).getIdEntity() == id) {
                    entities.remove(i);
                    writeFile(entities);
                    return;
                }
            }

            throw new RepositoryException("The given id does not exist!");
        }
    }
