import java.util.HashMap;
import java.util.Scanner;

class ATM {
    private int id;
    private int balance;

    ATM(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    int getBalance() {
        return balance;
    }

    void addAmount(int amount) {

        this.balance += amount;
    }

    void withdrawAmount(int amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            System.out.println("Amount withdrawn successfully...................... " + amount);
        } else {
            System.out.println("Insufficient balance......................");
        }
    }
}

class Card {
    private int pin;
    private int atmId;

    Card(int pin, int atmId) {
        this.pin = pin;
        this.atmId = atmId;
    }

    int getPin() {
        return pin;
    }

    int getAtmPin() {
        return atmId;
    }
}

interface ATMRepository {
    void saveATM(ATM atm);

    ATM getATM(int id);
}

class DighwaDubauliBranch implements ATMRepository {

    HashMap<Integer, ATM> atmMap = new HashMap<>();

    @Override
    public void saveATM(ATM atm) {
        atmMap.put(atm.getId(), atm);
    }

    @Override
    public ATM getATM(int id) {
        return atmMap.get(id);
    }
}

interface ATMAuthentification {
    boolean authenticate(Card card, int enteredPin);
}

class ATMAuthByPin implements ATMAuthentification {

    @Override
    public boolean authenticate(Card card, int enteredPin) {
        return card.getPin() == enteredPin;
    }

}

class ATMService {

    private ATMRepository atmRepository;
    private ATMAuthentification atmAuthentification;

    ATMService(ATMRepository atmRepository, ATMAuthentification atmAuthentification) {
        this.atmRepository = atmRepository;
        this.atmAuthentification = atmAuthentification;
    }

    public void deposite(Card card, int pin, int amount) {
        if (!atmAuthentification.authenticate(card, pin)) {
            System.out.println("Wrong Pin please enter the correct pin......................");
            return;
        }
        ATM atm = atmRepository.getATM(card.getAtmPin());
        atm.addAmount(amount);
        System.out.println("Amount deposited successfully...................... " + amount);
    }

    public void withdraw(Card card, int pin, int amount) {
        if (!atmAuthentification.authenticate(card, pin)) {
            System.out.println("Wrong Pin please enter the correct pin......................");
            return;
        }
        ATM atm = atmRepository.getATM(card.getAtmPin());
        atm.withdrawAmount(amount);
        System.out.println("Amount withdrawn successfully...................... " + amount);
    }
}

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ATMRepository atmRepo = new DighwaDubauliBranch(); // ✅ ONE INSTANCE
        ATMAuthentification auth = new ATMAuthByPin(); // ✅ ONE INSTANCE

        while (true) {
            System.out.println("Would you like to get you account login or you already have count to Login");
            System.out.println("1, Login");
            System.out.println("2, Register");
            System.out.println("3, Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter you ATM id first");
                    int atmId = sc.nextInt();
                    System.out.println("Enter your pin for login");
                    int pin = sc.nextInt();

                    Card card = new Card(pin, atmId);
                    if (auth.authenticate(card, pin)) {
                        System.out.println("You have successFully Login...");
                        System.out.println("1, Deposite");
                        System.out.println("2, Withdraw");
                        System.out.println("3, Exit");
                        int choice2 = sc.nextInt();
                        switch (choice2) {

                            case 1 -> {
                                System.out.println("Enter the amount you want to deposite");
                                int amount = sc.nextInt();
                                ATMService atmService = new ATMService(atmRepo, auth);
                                atmService.deposite(card, pin, amount);
                            }
                            case 2 -> {
                                System.out.println("Enter the amount you want to withdraw");
                                int amount = sc.nextInt();
                                ATMService atmService = new ATMService(atmRepo, auth);
                                atmService.withdraw(card, pin, amount);
                            }
                            case 3 -> {
                                System.out.println("You have successfully logged out...");
                                break;
                            }
                        }
                    }
                }
                case 2 -> {
                    System.out.println("Enter you ATM id first");
                    int atmId = sc.nextInt();
                    System.out.println("Enter your pin for login");
                    int pin = sc.nextInt();

                    Card card = new Card(pin, atmId);
                    System.out.println("please enter initial amount : ");
                    int intialAmount = sc.nextInt();
                    ATM atm = new ATM(atmId, intialAmount);

                    atmRepo.saveATM(atm);
                    System.out.println("You have successfully registered with initial amount of " + intialAmount);

                    if (auth.authenticate(card, pin)) {
                        System.out.println("You have successFully Login...");
                        System.out.println("1, Deposite");
                        System.out.println("2, Withdraw");
                        System.out.println("3, Exit");
                        int choice2 = sc.nextInt();
                        switch (choice2) {

                            case 1 -> {
                                System.out.println("Enter the amount you want to deposite");
                                int amount = sc.nextInt();
                                ATMService atmService = new ATMService(atmRepo, auth);
                                atmService.deposite(card, pin, amount);
                            }
                            case 2 -> {
                                System.out.println("Enter the amount you want to withdraw");
                                int amount = sc.nextInt();
                                ATMService atmService = new ATMService(atmRepo, auth);
                                atmService.withdraw(card, pin, amount);
                            }
                            case 3 -> {
                                System.out.println("You have successfully logged out...");
                                break;
                            }
                        }
                    }
                }
            }

        }

    }
}
