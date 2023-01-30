package org.cmbookrental.prj.controller;

import org.cmbookrental.prj.comm.CommonCode;
import org.cmbookrental.prj.comm.except.NoSuchComicBookException;
import org.cmbookrental.prj.dto.ComicBookDTO;
import org.cmbookrental.prj.factory.Factory;
import org.cmbookrental.prj.repository.CommonRepository;

import java.util.Set;

public class ComicBookControllerImpl implements ComicBookController{

    private final CommonRepository<ComicBookDTO> repository;

    @SuppressWarnings("unchecked")
    public ComicBookControllerImpl(){
        repository = (CommonRepository<ComicBookDTO>) Factory.getInstance().
                getRepository(CommonCode.REPOSITORY_COMIC_BOOK);
    }

    @Override
    public Set<ComicBookDTO> findAll() {
        return repository.findAll();
    }

    @Override
    public ComicBookDTO findOne(int id) throws NoSuchComicBookException {
        ComicBookDTO result = repository.findOne(id);
        if(result == null) {
            throw new NoSuchComicBookException(id + " 만화책 정보가 없습니다.");
        }
        return result;
    }

    @Override
    public String create(ComicBookDTO parameter) {
        String result;
        if(repository.create(parameter)){
            result = "만화책이 등록되었습니다.";
        }else{
            result = "이미 등록된 만화책입니다.";
        }
        return result;
    }

    @Override
    public String update(ComicBookDTO parameter) {
        String result;
        if(repository.create(parameter)){
            result = "만화책 정보가 수정되었습니다.";
        }else{
            result = "오류가 발생되어 만화책 정보가 수정되지 못했습니다.";
        }
        return result;
    }

    @Override
    public void delete(ComicBookDTO parameter) {

        repository.delete(parameter);
    }

    @Override
    public boolean contains(int id) {
        return repository.findOne(id) != null;
    }
}
