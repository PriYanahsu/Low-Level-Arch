import java.util.Scanner;

/*
    LISKOV SUBSTITUTION PRINCIPLE (LSP)
    OPEN CLOSED PRINCIPLE (OCP)
*/

/* -------------------- Interfaces -------------------- */

interface Withdrawable {
    void withdraw(double amount);
}

interface Depositable {
    void deposit(double amount);
}

/* -------------------- Abstract Base Class -------------------- */

abstract class BankAccount {
    protected double balance;

    public double getBalance() {
        return balance;
    }
}

/* -------------------- Saving Account -------------------- */

class SavingAccount extends BankAccount
        implements Withdrawable, Depositable {

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Saving Account - Deposited: " + amount);
    }

    @Override
    public void withdraw(double amount) {
        if (balance < amount) {
            System.out.println("Saving Account - Insufficient Balance: " + balance);
            return;
        }
        balance -= amount;
        System.out.println("Saving Account - Withdrawn: " + amount);
    }
}

/* -------------------- Current Account -------------------- */

class CurrentAccount extends BankAccount
        implements Withdrawable, Depositable {

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Current Account - Deposited: " + amount);
    }

    @Override
    public void withdraw(double amount) {
        if (balance < amount) {
            System.out.println("Current Account - Insufficient Balance: " + balance);
            return;
        }
        balance -= amount;
        System.out.println("Current Account - Withdrawn: " + amount);
    }
}

/* -------------------- Fixed Deposit Account -------------------- */

class FixedDepositAccount extends BankAccount
        implements Depositable {

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Fixed Deposit Account - Deposited: " + amount);
    }
}

/* -------------------- Main Class -------------------- */

public class Liskcov {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        SavingAccount savingAccount = new SavingAccount();
        CurrentAccount currentAccount = new CurrentAccount();
        FixedDepositAccount fdAccount = new FixedDepositAccount();

        while (true) {
            System.out.println("\n==== BANK MENU ====");
            System.out.println("1. Deposit to Saving Account");
            System.out.println("2. Withdraw from Saving Account");
            System.out.println("3. Deposit to Current Account");
            System.out.println("4. Withdraw from Current Account");
            System.out.println("5. Deposit to Fixed Deposit Account");
            System.out.println("6. Check Balances");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1 -> {
                    System.out.print("Enter amount: ");
                    savingAccount.deposit(sc.nextDouble());
                }

                case 2 -> {
                    System.out.print("Enter amount: ");
                    savingAccount.withdraw(sc.nextDouble());
                }

                case 3 -> {
                    System.out.print("Enter amount: ");
                    currentAccount.deposit(sc.nextDouble());
                }

                case 4 -> {
                    System.out.print("Enter amount: ");
                    currentAccount.withdraw(sc.nextDouble());
                }

                case 5 -> {
                    System.out.print("Enter amount: ");
                    fdAccount.deposit(sc.nextDouble());
                }

                case 6 -> {
                    System.out.println("\n--- BALANCES ---");
                    System.out.println("Saving Account: " + savingAccount.getBalance()); //  we can also use varibale here no issue
                    System.out.println("Current Account: " + currentAccount.getBalance());
                    System.out.println("Fixed Deposit Account: " + fdAccount.getBalance());
                }

                case 7 -> {
                    System.out.println("Thank you! Exiting...");
                    sc.close();
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
