package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 1.06.2021
 * не блокируюший кеш использую атомарные методы putifabsent , remove, computeifpresent
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentCache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public int getSize() {
        return memory.size();
    }

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        Base oldModel = memory.get(model.getId());
        BiFunction<Integer, Base, Base>  biFunction = (integer, base) -> {
           if (oldModel.getVersion() != model.getVersion()) {
               throw new OptimisticException("The version is not equals");
           }
           int version = oldModel.getVersion();
           Base newModel = new Base(oldModel.getId(), ++version);
           newModel.setName(model.getName());
           return newModel;
        };
        return memory.computeIfPresent(oldModel.getId(), biFunction) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}
