package org.cmbookrental.prj.comm.except;

public class NoSuchCustomerException extends Exception{
    public NoSuchCustomerException(String message){
        super(message);
    }
}
