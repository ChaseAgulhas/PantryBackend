package com.pantry.app.service;

import java.util.Set;

/**
 * Created by cfebruary on 2016/09/03.
 */
public interface Service<E, ID> {

    E create(E entity);

    E readById(ID id);

    Set<E> readAll();

    E update(E entity);

    void delete(E entity);
}