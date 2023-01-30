package org.cmbookrental.prj.factory;

import org.cmbookrental.prj.comm.CommonCode;
import org.cmbookrental.prj.controller.*;
import org.cmbookrental.prj.repository.ComicBookRepositoryImpl;
import org.cmbookrental.prj.repository.CommonRepository;
import org.cmbookrental.prj.repository.CustomerRepositoryImpl;
import org.cmbookrental.prj.repository.RentalRepositoryImpl;

/**
 * 팩토리 클래스
 * 싱글톤 패턴
 * 조건에 맞는 객체 생성 후 반환
 * (객체 생성하는 역할, 연결하는 역할)
 */
public class Factory {
    private static Factory instance;

    public static Factory getInstance(){
        if(instance == null)
            instance = new Factory();
        return instance;
    }
    private Factory(){}

    public final ComicBookController getComicBookController() {
        return new ComicBookControllerImpl();
    }

    public final RentalController getRentalController(){
        return new RentalControllerImpl();
    }

    public final CustomerController getCustomerController() {
        return new CustomerControllerImpl();
    }

    public CommonRepository<?> getRepository(int type){
        CommonRepository<?> repository = null;
        if(type == CommonCode.REPOSITORY_COMIC_BOOK){
            repository = new ComicBookRepositoryImpl();
        }
        else if(type == CommonCode.REPOSITORY_RENTAL){
            repository = new RentalRepositoryImpl();
        }
        else if(type == CommonCode.REPOSITORY_CUSTOMER){
            repository = new CustomerRepositoryImpl();
        }
        return repository;
    }

}
