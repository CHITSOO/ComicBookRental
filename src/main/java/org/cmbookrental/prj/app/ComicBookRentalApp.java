package org.cmbookrental.prj.app;

import org.cmbookrental.prj.comm.except.NoSuchComicBookException;
import org.cmbookrental.prj.comm.except.NoSuchCustomerException;
import org.cmbookrental.prj.comm.except.NoSuchRentalException;
import org.cmbookrental.prj.comm.except.NotReturnedException;
import org.cmbookrental.prj.controller.ComicBookController;
import org.cmbookrental.prj.controller.CustomerController;
import org.cmbookrental.prj.controller.RentalController;
import org.cmbookrental.prj.dto.ComicBookDTO;
import org.cmbookrental.prj.dto.CustomerDTO;
import org.cmbookrental.prj.dto.RentalDTO;
import org.cmbookrental.prj.factory.Factory;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ComicBookRentalApp {
    private final Scanner sc = new Scanner(System.in);
    private final ComicBookController comicBookController =
            Factory.getInstance().getComicBookController();
    private final RentalController rentalController =
            Factory.getInstance().getRentalController();
    private final CustomerController customerController =
            Factory.getInstance().getCustomerController();

    public static void main(String[] args) {
        ComicBookRentalApp app = new ComicBookRentalApp();
        app.mainMenu();
        System.out.println("프로그램 종료");
    }

    private void mainMenu() {
        boolean run = true;
        while (run){
            System.out.println("<메뉴 선택>");
            System.out.println("1.만화책 조회/등록/수정/삭제");
            System.out.println("2.만화책 대여");
            System.out.println("3.만화책 반납");
            System.out.println("4.고객 조회/등록/수정/삭제");
            System.out.println("0.프로그램 종료");
            System.out.println("====================");
            System.out.print("메뉴입력: ");
            int menu = sc.nextInt();

            switch (menu){
                case 1 :
                    comicBookMenu();
                    break;
                case 2 :
                    rentalMenu();
                    break;
                case 3 :
                    returnMenu();
                    break;
                case 4 :
                    customerMenu();
                    break;
                case 0 :
                    run = false;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    break;
            }
        }
    }

    private void comicBookMenu() {
        int cMenu;
        do {
            System.out.println("<메뉴 선택>");
            System.out.println("1.만화책 등록");
            System.out.println("2.만화책 수정");
            System.out.println("3.만화책 삭제");
            System.out.println("4.만화책 조회");
            System.out.println("0.이전 메뉴");
            System.out.println("==========");
            System.out.print("메뉴입력: ");
            cMenu = sc.nextInt();

            switch (cMenu) {
                case 1:
                    registerComicBook();
                    break;
                case 2:
                    modifyComicBook();
                    break;
                case 3:
                    deleteComicBook();
                    break;
                case 4:
                    lookUpComicBooks();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        } while (cMenu != 0);
    }

    private void rentalMenu() {
        int bookID;
        ComicBookDTO book = null;
        boolean retry = false;

        System.out.println("===========");
        System.out.println("<만화책 대여 (0: 이전메뉴)>");

        do{
            //재입력 받을 때만 출력되는 문구
            if(retry){
                System.out.println(" 다시 입력해주세요.");
            }
            //만화책의 ID 입력받기
            System.out.print("-대여할 만화책의 ID: ");
            bookID = sc.nextInt();
            //0이면 이전 메뉴로 이동
            if(bookID == 0) {return;}

            //등록되지 않은 책이면 재입력 받아야 함.
            try{
                book = comicBookController.findOne(bookID);
                retry = false;
            }catch (NoSuchComicBookException e){
                retry = true;
                String message = e.getMessage();
                System.out.print(message);
            }
        } while(retry);

        //만화책이 대여중인지 확인
        if(rentalController.isOnRent(book)) {
            System.out.println("만화책이 대여중입니다.");
        } else {
            int customerID;
            boolean isReady = false;

            //고객 ID 입력받기
            do {
                System.out.print("-대여할 고객의 ID: ");
                customerID = sc.nextInt();
                //0이면 이전 메뉴로 이동
                if (customerID == 0) {return;}

                try {
                    //등록된 고객인지 확인. 등록된 고객이면 반복문 빠져나감
                    customerController.findOne(customerID);
                    isReady = true;
                } catch (NoSuchCustomerException e) {
                    //등록된 고객이 없으면 고객 등록
                    String message = e.getMessage();
                    System.out.print(message);
                    System.out.println("고객 등록 메뉴로 이동합니다.");
                    registerCustomer();
                }
            }while(!isReady);

            //고객의 대여 건이 있는지 확인
            boolean isFinish = false;
            do {
                try {
                    //대여건이 없으면 대출가능.
                    if (rentalController.rentable(customerID)) {
                        //대출 가능하면 지금까지 받은 정보로 만화책을 대여한다.
                        rentalController.create(new RentalDTO(customerID, LocalDate.now(), book));
                        System.out.println(customerID + " 님이 " + bookID + " 만화책을 대여했습니다.");
                        //만화책을 대여했으니 반복문을 빠져나가고 끝.
                        isFinish = true;
                    }
                } catch (NotReturnedException e) {
                    //아직 반납해야하는 책이 있다면
                    String message = e.getMessage();
                    System.out.println(message);

                    int select;
                    do {
                        //반납의사 물어보고 반납처리.
                        System.out.print("반납 처리 후 대여 하려면 1 입력, 이전 메뉴로 이동하려면 0 입력 : ");
                        select = sc.nextInt();
                        switch (select) {
                            case 1:
                                System.out.println("반납 처리 메뉴로 이동합니다.");
                                returnMenu();
                                break;
                            case 0:
                                //0을 입력하면 이전 메뉴로 이동.
                                isFinish = true;
                                break;
                            default:
                                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                                break;
                        }
                    } while (select != 0 && select != 1);
                }
            }while(!isFinish);
        }
    }

    private void returnMenu() {
        int bookID;
        ComicBookDTO book;
        RentalDTO rental = null;
        boolean retry = false;

        System.out.println("===========");
        System.out.println("<만화책 반납 (0: 이전메뉴)>");

        do{
            //다시 입력 받을 때만 출력
            if(retry){
                System.out.println(" 다시 입력해주세요.");
            }
            //만화책 ID 입력받기
            System.out.print("-반납할 만화책의 ID: ");
            bookID = sc.nextInt();
            //0이 입력되면 이전 메뉴로 이동
            if(bookID == 0) {return;}

            //만화책 ID를 이용해서 반납할 대여정보 찾기
            try{
                book = comicBookController.findOne(bookID);
                rental = rentalController.findOneByBook(book);
                retry = false;
            }catch (NoSuchComicBookException e){
                //등록되지 않은 만화책이면
                retry = true;
                String message = e.getMessage();
                System.out.print(message);
            }catch (NoSuchRentalException e){
                //대여하지 않은 만화책이면
                retry = true;
                String message = e.getMessage();
                System.out.print(message);
            }
        } while(retry);

        rentalController.delete(rental);
        System.out.println("반납이 처리되었습니다.");
    }

    private void customerMenu() {
        /*comicBookMenu 와 같다*/
        int cMenu;
        do {
            System.out.println("<메뉴 선택>");
            System.out.println("1.고객 등록");
            System.out.println("2.고객 수정");
            System.out.println("3.고객 삭제");
            System.out.println("4.고객 조회");
            System.out.println("0.이전 메뉴");
            System.out.println("==========");
            System.out.print("메뉴입력: ");
            cMenu = sc.nextInt();

            switch (cMenu) {
                case 1:
                    registerCustomer();
                    break;
                case 2:
                    modifyCustomer();
                    break;
                case 3:
                    deleteCustomer();
                    break;
                case 4:
                    lookUpCustomers();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        } while (cMenu != 0);
    }

    private void registerComicBook() {
        System.out.println("===========");
        System.out.println("<만화책 등록 (0: 이전메뉴)>");
        int bookID;
        do{
            System.out.print("-ID: ");
            bookID= sc.nextInt();
            if(bookID == 0) {return;}
            if(comicBookController.contains(bookID)){
                System.out.println("같은 ID가 있습니다. 다시 입력해주세요.");
            } else { break; }
        }while(true);

        sc.nextLine();
        System.out.print("-만화책명: ");
        String title = sc.nextLine();
        if(title.equals("0")) {return;}

        System.out.print("-작가: ");
        String author = sc.nextLine();
        if(author.equals("0")) {return;}

        System.out.println("===========");
        String result = comicBookController.create(new ComicBookDTO(bookID, title, author));
        System.out.println(result);
    }

    private void modifyComicBook() {
        int oldBookID;
        ComicBookDTO oldBook = null;
        boolean retry = false;

        System.out.println("===========");
        System.out.println("<만화책 수정 (0: 이전메뉴)>");

        do{
            //처음에는 출력되지 않는 문구
            if(retry){
                System.out.println(" 다시 입력해주세요.");
            }

            //만화책의 ID 입력받기
            System.out.print("-수정할 만화책의 ID: ");
            oldBookID = sc.nextInt();
            //0입력시 이전 메뉴로 이동
            if(oldBookID == 0) {return;}

            try{
                //만화책 ID 로부터 만화책 정보 가져옴. 찾으면 반복문 그만.
                oldBook = comicBookController.findOne(oldBookID);
                retry = false;
            }catch (NoSuchComicBookException e){
                //없으면 다시 만화책 ID 입력받음.
                retry = true;
                String message = e.getMessage();
                System.out.print(message);
            }
        } while(retry);

        System.out.println("수정할 만화책의 새로운 정보를 입력해주세요.");
        int bookID;
        do{
            System.out.print("-ID: ");
            bookID= sc.nextInt();
            if(bookID == 0) {return;}
            if(comicBookController.contains(bookID) && bookID != oldBookID){
                System.out.println("같은 ID가 있습니다. 다시 입력해주세요.");
            } else { break; }
        }while(true);

        sc.nextLine();
        System.out.print("-만화책명: ");
        String title = sc.nextLine();
        if(title.equals("0")) {return;}

        System.out.print("-작가: ");
        String author = sc.nextLine();
        if(author.equals("0")) {return;}

        System.out.println("===========");
        //수정을 위해 이전에 있던 건 삭제.
        comicBookController.delete(oldBook);
        //갱신 - 새로 저장하는 것임.
        String result = comicBookController.update(new ComicBookDTO(bookID, title, author));
        System.out.println(result);
    }

    private void deleteComicBook() {
        int bookID;
        ComicBookDTO book = null;
        boolean retry = false;

        System.out.println("===========");
        System.out.println("<만화책 삭제 (0: 이전메뉴)>");

        do{
            //처음에는 출력되지 않는 문구
            if(retry){
                System.out.println(" 다시 입력해주세요.");
            }

            //만화책의 ID를 입력받음
            System.out.print("-삭제할 만화책의 ID: ");
            bookID = sc.nextInt();
            //0입력시 이전 메뉴로 이동
            if(bookID == 0) {return;}

            try{
                //bookID로 삭제할 ComicBookDTO 객체를 찾는다. 찾으면 반목문 나감
                book = comicBookController.findOne(bookID);
                retry = false;
            }catch (NoSuchComicBookException e){
                //못찾으면 다시 만화책 ID 입력받을 예정.
                retry = true;
                String message = e.getMessage();
                System.out.print(message);
            }
        } while(retry);

        comicBookController.delete(book);
        System.out.println(bookID + " 만화책이 삭제되었습니다.");
    }

    private void lookUpComicBooks() {
        Set<ComicBookDTO> set = comicBookController.findAll();
        if(set.size() == 0) {
            System.out.println("등록된 만화책이 없습니다.");
        } else {
            Iterator<ComicBookDTO> iterator = set.iterator();
            while(iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        }
    }

    private void registerCustomer() {
        /*deleteComicBook 와 같다*/
        System.out.println("===========");
        System.out.println("<고객 등록 (0: 이전메뉴)>");
        int customerID;
        do{
            System.out.print("-ID: ");
            customerID = sc.nextInt();
            if(customerID == 0) {return;}
            if(customerController.contains(customerID)){
                System.out.println("같은 ID가 있습니다. 다시 입력해주세요.");
            } else { break; }
        }while(true);

        sc.nextLine();
        System.out.print("-고객명: ");
        String name = sc.nextLine();
        if(name.equals("0")) {return;}


        System.out.println("===========");
        String result = customerController.create(new CustomerDTO(customerID, name));
        System.out.println(result);
    }

    private void modifyCustomer() {
        /*modifyComicBook 와 같다*/
        int oldCustomerID;
        CustomerDTO oldCustomer = null;
        boolean retry = false;

        System.out.println("===========");
        System.out.println("<고객 정보 수정 (0: 이전메뉴)>");

        do{
            if(retry){
                System.out.println(" 다시 입력해주세요.");
            }
            System.out.print("-수정할 고객의 ID: ");
            oldCustomerID = sc.nextInt();
            if(oldCustomerID == 0) {return;}
            try{
                oldCustomer = customerController.findOne(oldCustomerID);
                retry = false;
            }catch (NoSuchCustomerException e){
                retry = true;
                String message = e.getMessage();
                System.out.print(message);
            }
        } while(retry);

        customerController.delete(oldCustomer);

        System.out.println("수정할 고객의 새로운 정보를 입력해주세요.");
        int customerID;
        do{
            System.out.print("-ID: ");
            customerID = sc.nextInt();
            if(customerID == 0) {return;}
            if(customerController.contains(customerID) && customerID != oldCustomerID){
                System.out.println("같은 ID가 있습니다. 다시 입력해주세요.");
            } else { break; }
        }while(true);

        sc.nextLine();
        System.out.print("-고객명: ");
        String name = sc.nextLine();
        if(name.equals("0")) {return;}

        System.out.println("===========");
        String result = customerController.update(new CustomerDTO(customerID, name));
        System.out.println(result);
    }

    private void deleteCustomer() {
        /*deleteComicBook 와 같다*/
        int customerID;
        CustomerDTO customer = null;
        boolean retry = false;

        System.out.println("===========");
        System.out.println("<고객 삭제 (0: 이전메뉴)>");
        do{
            if(retry){
                System.out.println(" 다시 입력해주세요.");
            }
            System.out.print("-삭제할 고객의 ID: ");
            customerID = sc.nextInt();
            if(customerID == 0) {return;}
            try{
                customer = customerController.findOne(customerID);
                retry = false;
            }catch (NoSuchCustomerException e){
                retry = true;
                String message = e.getMessage();
                System.out.print(message);
            }
        } while(retry);

        customerController.delete(customer);
        System.out.println(customerID + " 고객이 삭제되었습니다.");
    }

    private void lookUpCustomers() {
        Set<CustomerDTO> set = customerController.findAll();
        if(set.size() == 0) {
            System.out.println("등록된 고객이 없습니다.");
        } else {
            Iterator<CustomerDTO> iterator = set.iterator();
            while(iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        }
    }
}

