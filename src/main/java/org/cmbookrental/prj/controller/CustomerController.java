package org.cmbookrental.prj.controller;

import org.cmbookrental.prj.comm.except.NoSuchCustomerException;
import org.cmbookrental.prj.dto.CustomerDTO;

import java.util.Set;

public interface CustomerController {
    Set<CustomerDTO> findAll();
    CustomerDTO findOne(int id) throws NoSuchCustomerException;
    String create(CustomerDTO parameter);
    String update(CustomerDTO parameter);
    void delete(CustomerDTO parameter);
    boolean contains(int id);
}
