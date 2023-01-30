package org.cmbookrental.prj.repository;

import org.cmbookrental.prj.dto.CollectionDB;
import org.cmbookrental.prj.dto.ComicBookDTO;
import org.cmbookrental.prj.dto.RentalDTO;

import java.util.Set;

public class RentalRepositoryImpl implements CommonRepository<RentalDTO>{
    @Override
    public Set<RentalDTO> findAll() {
        return null;
    }

    @Override
    public RentalDTO findOne(int id) {
        return CollectionDB.getInstance().getRental(id);
    }

    @Override
    public RentalDTO findOneByBook(ComicBookDTO book) {
        return CollectionDB.getInstance().getRentalByBook(book);
    }

    @Override
    public boolean create(RentalDTO parameter) {
        return CollectionDB.getInstance().setRental(parameter);
    }

    @Override
    public void delete(RentalDTO parameter) {
        CollectionDB.getInstance().removeRental(parameter);
    }

}
