package org.cmbookrental.prj.repository;

import org.cmbookrental.prj.dto.ComicBookDTO;

import java.util.Set;

public interface CommonRepository<T> {
    Set<T> findAll();
    T findOne(int id);
    boolean create(T parameter);
    void delete(T parameter);
    T findOneByBook(ComicBookDTO book);
}
