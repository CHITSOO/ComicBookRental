package org.cmbookrental.prj.controller;

import org.cmbookrental.prj.comm.except.NoSuchRentalException;
import org.cmbookrental.prj.comm.except.NotReturnedException;
import org.cmbookrental.prj.dto.ComicBookDTO;
import org.cmbookrental.prj.dto.RentalDTO;

public interface RentalController {
    RentalDTO findOneByBook(ComicBookDTO book) throws NoSuchRentalException;
    boolean isOnRent(ComicBookDTO book);
    //RentalDTO findOne(int id);
    void create(RentalDTO parameter);
    boolean rentable(int customerID) throws NotReturnedException;
    void delete(RentalDTO parameter);
}
