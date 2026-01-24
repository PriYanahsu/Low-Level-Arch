import java.util.HashMap;

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
}

public class Main {
    public static void main(String[] args) {

        ATMRepository DighwaBranch = new DighwaDubauliBranch();
        ATM atm = new ATM(1, 995523);

        DighwaBranch.saveATM(atm);
        System.out.println(DighwaBranch.getATM(1).getBalance());

        ATMAuthentification auth = new ATMAuthByPin();
        Card card = new Card(1265, 1);

        ATMService atmService = new ATMService(DighwaBranch, auth);

        atmService.deposite(card, 126, 5000);

    }
}
