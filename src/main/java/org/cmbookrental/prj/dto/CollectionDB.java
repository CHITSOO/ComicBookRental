package org.cmbookrental.prj.dto;

import java.util.*;

public class CollectionDB {
    private static CollectionDB instance = new CollectionDB();

    private static final String COMIC_BOOK_DATA = "ComicBook";
    private static final String RENTAL_DATA = "Rental";
    private static final String CUSTOMER_DATA = "Customer";

    private static final Map<String, HashSet> mDatabase = new HashMap<>();

    private CollectionDB(){}

    static{
        mDatabase.put("ComicBook", new HashSet<ComicBookDTO>());
        mDatabase.put("Rental", new HashSet<RentalDTO>());
        mDatabase.put("Customer", new HashSet<CustomerDTO>());
    }

    public static CollectionDB getInstance(){
        if(instance == null)
            instance = new CollectionDB();
        return instance;
    }

    public HashSet<ComicBookDTO> getComicBookTable(){
        return mDatabase.get("ComicBook");
    }

    public boolean setComicBook(ComicBookDTO parameter){
        return mDatabase.get(CollectionDB.COMIC_BOOK_DATA).add(parameter);
    }

    public ComicBookDTO getComicBook(int id) {
        HashSet<ComicBookDTO> set = mDatabase.get(CollectionDB.COMIC_BOOK_DATA);
        ComicBookDTO comicBookDTO = null;
        Iterator<ComicBookDTO> iterator = set.iterator();

        while (iterator.hasNext()){
            ComicBookDTO element = iterator.next();
            int elementId = element.getBookID();

            if(elementId == id){
                comicBookDTO = element;
                break;
            }
        }

        return comicBookDTO;
    }

    public void removeComicBook(ComicBookDTO parameter) {
        mDatabase.get(CollectionDB.COMIC_BOOK_DATA).remove(parameter);
    }

    public RentalDTO getRental(int id) {
        HashSet<RentalDTO> set = mDatabase.get(CollectionDB.RENTAL_DATA);
        RentalDTO rentalDTO = null;
        Iterator<RentalDTO> iterator = set.iterator();

        while (iterator.hasNext()){
            RentalDTO element = iterator.next();
            int elementId = element.getRentalID();

            if(elementId == id){
                rentalDTO = element;
                break;
            }
        }
        return rentalDTO;
    }

    public RentalDTO getRentalByBook(ComicBookDTO parameter) {
        HashSet<RentalDTO> set = mDatabase.get(CollectionDB.RENTAL_DATA);
        RentalDTO rentalDTO = null;
        Iterator<RentalDTO> iterator = set.iterator();

        while (iterator.hasNext()){
            RentalDTO element = iterator.next();
            ComicBookDTO book = element.getComicBookDTO();

            if(book.equals(parameter)){
                rentalDTO = element;
                break;
            }
        }
        return rentalDTO;
    }

    public boolean setRental(RentalDTO parameter) {
        return mDatabase.get(CollectionDB.RENTAL_DATA).add(parameter);
    }

    public void removeRental(RentalDTO parameter) {
        mDatabase.get(CollectionDB.RENTAL_DATA).remove(parameter);
    }

    public HashSet<CustomerDTO> getCustomerTable(){
        return mDatabase.get("Customer");
    }

    public boolean setCustomer(CustomerDTO parameter){

        return mDatabase.get(CollectionDB.CUSTOMER_DATA).add(parameter);
    }

    public CustomerDTO getCustomer(int id) {
        HashSet<CustomerDTO> set = mDatabase.get(CollectionDB.CUSTOMER_DATA);
        CustomerDTO customerDTO = null;
        Iterator<CustomerDTO> iterator = set.iterator();

        while (iterator.hasNext()){
            CustomerDTO element = iterator.next();
            int elementId = element.getCustomerID();

            if(elementId == id){
                customerDTO = element;
                break;
            }
        }
        return customerDTO;
    }

    public void removeCustomer(CustomerDTO parameter) {
        mDatabase.get(CollectionDB.CUSTOMER_DATA).remove(parameter);
    }

}
