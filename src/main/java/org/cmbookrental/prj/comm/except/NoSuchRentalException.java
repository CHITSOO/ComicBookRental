package org.cmbookrental.prj.comm.except;

public class NoSuchRentalException extends Exception{
    public NoSuchRentalException(String message){
        super(message);
    }
}
