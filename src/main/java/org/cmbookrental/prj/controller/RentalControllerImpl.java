package org.cmbookrental.prj.controller;

import org.cmbookrental.prj.comm.CommonCode;
import org.cmbookrental.prj.comm.except.NoSuchRentalException;
import org.cmbookrental.prj.comm.except.NotReturnedException;
import org.cmbookrental.prj.dto.ComicBookDTO;
import org.cmbookrental.prj.dto.RentalDTO;
import org.cmbookrental.prj.factory.Factory;
import org.cmbookrental.prj.repository.CommonRepository;

public class RentalControllerImpl implements RentalController{

    private final CommonRepository<RentalDTO> repository;

    @SuppressWarnings("unchecked")
    public RentalControllerImpl(){
        repository = (CommonRepository<RentalDTO>) Factory.getInstance().
                getRepository(CommonCode.REPOSITORY_RENTAL);
    }
    @Override
    public RentalDTO findOneByBook(ComicBookDTO book) throws NoSuchRentalException {
        RentalDTO result = repository.findOneByBook(book);
        if(result == null) {
            throw new NoSuchRentalException(book.getBookID() + "대여 정보가 없습니다.");
        }
        return result;
    }

    @Override
    public void create(RentalDTO parameter) {
        repository.create(parameter);
    }

    @Override
    public void delete(RentalDTO parameter) {
        repository.delete(parameter);
    }

    @Override
    public boolean isOnRent(ComicBookDTO book) {
        RentalDTO result = repository.findOneByBook(book);
        return result != null;
    }

    @Override
    public boolean rentable(int customerID) throws NotReturnedException {
        RentalDTO rental = repository.findOne(customerID);
        if(rental != null) {
            throw new NotReturnedException(rental.getComicBookDTO().getBookID()
                    + " 대여된 만화책이 반납되지 않았습니다");
        }
        return true;
    }
}
