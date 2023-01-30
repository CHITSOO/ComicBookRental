package org.cmbookrental.prj.controller;

import org.cmbookrental.prj.comm.except.NoSuchComicBookException;
import org.cmbookrental.prj.dto.ComicBookDTO;

import java.util.Set;

public interface ComicBookController {
    Set<ComicBookDTO> findAll();
    ComicBookDTO findOne(int id) throws NoSuchComicBookException;
    String create(ComicBookDTO parameter);
    String update(ComicBookDTO parameter);
    void delete(ComicBookDTO parameter);
    boolean contains(int id);
}