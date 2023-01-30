package org.cmbookrental.prj.controller;

import org.cmbookrental.prj.comm.CommonCode;
import org.cmbookrental.prj.comm.except.NoSuchCustomerException;
import org.cmbookrental.prj.dto.CustomerDTO;
import org.cmbookrental.prj.factory.Factory;
import org.cmbookrental.prj.repository.CommonRepository;

import java.util.Set;

public class CustomerControllerImpl implements CustomerController{

    private final CommonRepository<CustomerDTO> repository;

    @SuppressWarnings("unchecked")
    public CustomerControllerImpl(){
        repository = (CommonRepository<CustomerDTO>) Factory.getInstance().
                getRepository(CommonCode.REPOSITORY_CUSTOMER);
    }

    @Override
    public Set<CustomerDTO> findAll() {
        return repository.findAll();
    }

    @Override
    public CustomerDTO findOne(int id) throws NoSuchCustomerException {
        CustomerDTO result = repository.findOne(id);
        if(result == null) {
            throw new NoSuchCustomerException(id + " 가 존재하지 않습니다.");
        }
        return result;
    }

    @Override
    public String create(CustomerDTO parameter) {
        String result;
        if(repository.create(parameter)){
            result = "고객이 등록되었습니다.";
        }else{
            result = "이미 등록된 고객입니다.";
        }
        return result;
    }

    @Override
    public String update(CustomerDTO parameter) {
        String result;
        if(repository.create(parameter)){
            result = "고객 정보가 수정되었습니다.";
        }else{
            result = "오류가 발생되어 고객 정보가 수정되지 못했습니다.";
        }
        return result;
    }

    @Override
    public void delete(CustomerDTO parameter) {
        repository.delete(parameter);
    }

    @Override
    public boolean contains(int id) {
        return repository.findOne(id) != null;
    }
}
