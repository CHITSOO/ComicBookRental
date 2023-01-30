package org.cmbookrental.prj.repository;

import org.cmbookrental.prj.dto.CollectionDB;
import org.cmbookrental.prj.dto.ComicBookDTO;
import org.cmbookrental.prj.dto.CustomerDTO;

import java.util.Set;

public class CustomerRepositoryImpl implements CommonRepository<CustomerDTO>{
    @Override
    public Set<CustomerDTO> findAll() {
        return CollectionDB.getInstance().getCustomerTable();
    }

    @Override
    public CustomerDTO findOne(int id) {
        return CollectionDB.getInstance().getCustomer(id);
    }

    @Override
    public CustomerDTO findOneByBook(ComicBookDTO book) { return null; }

    @Override
    public boolean create(CustomerDTO parameter) {
        return CollectionDB.getInstance().setCustomer(parameter);
    }

    @Override
    public void delete(CustomerDTO parameter) {
        CollectionDB.getInstance().removeCustomer(parameter);
    }
}
