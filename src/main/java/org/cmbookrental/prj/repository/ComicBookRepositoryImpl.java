package org.cmbookrental.prj.repository;

import org.cmbookrental.prj.dto.CollectionDB;
import org.cmbookrental.prj.dto.ComicBookDTO;

import java.util.Set;

public class ComicBookRepositoryImpl implements CommonRepository<ComicBookDTO>{
    @Override
    public Set<ComicBookDTO> findAll() {
        return CollectionDB.getInstance().getComicBookTable();
    }

    @Override
    public ComicBookDTO findOne(int id) {

        return CollectionDB.getInstance().getComicBook(id);
    }

    @Override
    public ComicBookDTO findOneByBook(ComicBookDTO book) { return null; }

    @Override
    public boolean create(ComicBookDTO parameter) {

        return CollectionDB.getInstance().setComicBook(parameter);
    }

    @Override
    public void delete(ComicBookDTO parameter) {

        CollectionDB.getInstance().removeComicBook(parameter);
    }

}
