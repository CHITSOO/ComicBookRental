package org.cmbookrental.prj.comm.except;

public class NoSuchComicBookException extends Exception{
    public NoSuchComicBookException(String message){
        super(message);
    }
}
