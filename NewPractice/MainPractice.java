package NewPractice;
// class for ACCOUNT

import java.util.HashMap;
import java.util.Scanner;

class Account {

    private int accId;
    private int balance;
    private int pin;

    Account(int accId, int balance, int pin) {
        this.accId = accId;
        this.balance = balance;
        this.pin = pin;
    }

    public int getAccId() {
        return accId;
    }

    public int getBalance() {
        return balance;
    }

    public int getPin() {
        return pin;
    }

    void addAmount(int amount) {
        if (amount < 0) {
            throw new RuntimeException("Invalid amount please enter the correct amount");
        }
        this.balance += amount;
        System.out.println("Amount deposited successfully....................... " + amount);
    }

    void deductAmount(int amount) {
        if (balance < amount) {
            throw new RuntimeException("Insufficient Balance.......");
        }

        this.balance -= amount;
        System.out.println("Amount withrow successfully..... " + amount);
    }
}

// for card
class Card {

    private int accId;

    Card(int accId) {
        this.accId = accId;
    }

    public int getAccId() {
        return accId;
    }
}

// Account Repository
interface AccountRepository {
    void saveAccount(Account account);

    Account getAccount(int accId);
}

// implement Repo to DighwaDubauli Branch
class DighwaDubauliBranch implements AccountRepository {

    HashMap<Integer, Account> accMap = new HashMap<>();

    @Override
    public void saveAccount(Account account) {
        accMap.put(account.getAccId(), account);
    }

    @Override
    public Account getAccount(int accId) {
        return accMap.get(accId);
    }

}

// Interface for authentication
interface Authentication {
    boolean authenticate(Card card, int pin);
}

// implement aunthentication with the pin
class authByPin implements Authentication {

    private AccountRepository accountRepository;

    authByPin(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean authenticate(Card card, int pin) {
        Account acc = accountRepository.getAccount(card.getAccId());
        return acc.getPin() == pin;
    }
}

// Interface for ATM Service
interface ATMService {
    void deposite(Card card, int pin, int amount);

    void withdraw(Card card, int pin, int amount);
}

class ATMServiceImpl implements ATMService {

    private AccountRepository accountRepository;
    private Authentication accountAuthentication;

    ATMServiceImpl(AccountRepository accountRepo, Authentication auth) {
        this.accountRepository = accountRepo;
        this.accountAuthentication = auth;
    }

    @Override
    public void deposite(Card card, int pin, int amount) {
        if (!authenticate(card, pin)) {
            throw new RuntimeException("Invalid pin");
        }
        Account account = accountRepository.getAccount(card.getAccId());
        account.addAmount(amount);
    }

    @Override
    public void withdraw(Card card, int pin, int amount) {
        if (!authenticate(card, pin)) {
            throw new RuntimeException("Invalid pin");
        }
        Account account = accountRepository.getAccount(card.getAccId());
        account.deductAmount(amount);
    }

    // helper function for authentication
    private boolean authenticate(Card card, int pin) {
        return accountAuthentication.authenticate(card, pin);
    }

}

public class MainPractice {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        AccountRepository accountRepository = new DighwaDubauliBranch();
        Authentication authentication = new authByPin(accountRepository);
        ATMService atmService = new ATMServiceImpl(accountRepository, authentication);

        while (true) {
            System.out.println("Enter your choice");
            System.out.println("1. Registration");
            System.out.println("2. Login");
            System.out.println("3 Exit");

            int choice = sc.nextInt();

            switch (choice) {

                case 1 -> {
                    System.out.println("Enter your account id");
                    int accId = sc.nextInt();
                    System.out.println("Enter your Initial Balance");
                    int balance = sc.nextInt();
                    System.out.println("Enter your pin");
                    int pin = sc.nextInt();
                    Account account = new Account(accId, balance, pin);
                    accountRepository.saveAccount(account);
                    System.out.println("Account created Succesfully.......");
                }

                case 2 -> {
                    System.out.println("Enter your account id");
                    int accId = sc.nextInt();
                    System.out.println("Enter your pin");
                    int pin = sc.nextInt();
                    Card card = new Card(accId);
                    if (authentication.authenticate(card, pin)) {
                        System.out.println("Login successful");
                        while (true) {
                            System.out.println("Enter your choice");
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Exit");
                            int currChoice = sc.nextInt();
                            switch (currChoice) {
                                case 1 -> {
                                    System.out.println("Enter the amount to deposit");
                                    int amount = sc.nextInt();
                                    atmService.deposite(card, pin, amount);
                                }
                                case 2 -> {
                                    System.out.println("Enter the amount to withdraw");
                                    int amount = sc.nextInt();
                                    atmService.withdraw(card, pin, amount);
                                }
                                case 3 -> {
                                    System.out.println("Thank you for using our ATM");
                                    return;
                                }
                            }
                        }
                    } else {
                        System.out.println("Invalid pin");
                    }
                }
            }
        }
    }
}
