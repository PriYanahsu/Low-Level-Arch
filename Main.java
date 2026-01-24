import java.util.HashMap;
import java.util.Scanner;

class ATM {
    private int accId;
    private int balance;
    private int pin;

    ATM(int accId, int balance, int pin) {
        this.accId = accId;
        this.balance = balance;
        this.pin = pin;
    }

    public int getAccId() {
        return accId;
    }

    public int getPin() {
        return pin;
    }

    int getBalance() {
        return balance;
    }

    void addAmount(int amount) {

        if (amount < 0) {
            System.out.println("Invalid amount please enter the correct amount......................");
            return;
        }
        this.balance += amount;
        System.out.println("Amount deposited successfully...................... " + amount);
    }

    void withdrawAmount(int amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            System.out.println("Amount withdrawn successfully...................... " + amount);
            return;
        } else {
            System.out.println("Insufficient balance......................");
        }
    }
}

class Card {
    private int accId;

    Card(int accId) {
        this.accId = accId;
    }

    int getAccId() {
        return accId;
    }
}

interface ATMRepository {
    void saveAcc(ATM atm);

    ATM getAccId(int id);
}

class DighwaDubauliBranch implements ATMRepository {

    HashMap<Integer, ATM> atmMap = new HashMap<>();

    @Override
    public void saveAcc(ATM atm) {
        atmMap.put(atm.getAccId(), atm);
    }

    @Override
    public ATM getAccId(int id) {
        return atmMap.get(id);
    }
}

interface ATMAuthentification {
    boolean authenticate(Card card, int enteredPin);
}

class ATMAuthByPin implements ATMAuthentification {

    private final ATMRepository atmRepository;

    ATMAuthByPin(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Override
    public boolean authenticate(Card card, int enteredPin) {
        ATM atm = atmRepository.getAccId(card.getAccId());
        return atm.getPin() == enteredPin;
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
        ATM atm = atmRepository.getAccId(card.getAccId());
        atm.addAmount(amount);
    }

    public void withdraw(Card card, int pin, int amount) {
        if (!atmAuthentification.authenticate(card, pin)) {
            System.out.println("Wrong Pin please enter the correct pin......................");
            return;
        }
        ATM atm = atmRepository.getAccId(card.getAccId());
        atm.withdrawAmount(amount);
    }
}

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ATMRepository atmRepo = new DighwaDubauliBranch(); // ONE INSTANCE
        ATMAuthentification auth = new ATMAuthByPin(atmRepo); // ONE INSTANCE
        ATMService atmService = new ATMService(atmRepo, auth); // ONE INSTANCE

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
                    boolean isLogin = false;

                    Card card = new Card(atmId);
                    if (auth.authenticate(card, pin)) {
                        System.out.println("You have successFully Login...");
                        isLogin = true;
                        while (isLogin) {
                            System.out.println("1, Deposite");
                            System.out.println("2, Withdraw");
                            System.out.println("3, Exit");
                            int choice2 = sc.nextInt();
                            switch (choice2) {

                                case 1 -> {
                                    System.out.println("Enter the amount you want to deposite");
                                    int amount = sc.nextInt();
                                    atmService.deposite(card, pin, amount);
                                }
                                case 2 -> {
                                    System.out.println("Enter the amount you want to withdraw");
                                    int amount = sc.nextInt();
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
                case 2 -> {
                    System.out.println("Enter you ATM id first");
                    int atmId = sc.nextInt();
                    System.out.println("Enter your pin for login");
                    int pin = sc.nextInt();

                    Card card = new Card(atmId);
                    System.out.println("please enter initial amount : ");
                    int intialAmount = sc.nextInt();

                    atmRepo.saveAcc(new ATM(atmId, intialAmount, pin));
                    boolean isRegistered = false;

                    if (auth.authenticate(card, pin)) {
                        System.out.println("You have successfully registered with initial amount of " + intialAmount);
                        isRegistered = true;

                        while (isRegistered) {
                            System.out.println("1, Deposite");
                            System.out.println("2, Withdraw");
                            System.out.println("3, Exit");
                            int choice2 = sc.nextInt();
                            switch (choice2) {

                                case 1 -> {
                                    System.out.println("Enter the amount you want to deposite");
                                    int amount = sc.nextInt();
                                    atmService.deposite(card, pin, amount);
                                }
                                case 2 -> {
                                    System.out.println("Enter the amount you want to withdraw");
                                    int amount = sc.nextInt();
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
}
