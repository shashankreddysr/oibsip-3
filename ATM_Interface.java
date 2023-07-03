import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;
    private String date;

    public Transaction(String type, double amount, String date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}

class Account {
    private String userId;
    private String userPin;
    private double balance;
    private List<Transaction> transactions;

    public Account(String userId, String userPin, double balance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPin() {
        return userPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        String date = java.time.LocalDate.now().toString();
        System.out.println("*********************************************");
        transactions.add(new Transaction("Deposit", amount, date));
        System.out.println("Deposit Completed. Your Current balance: " + balance);
        System.out.println("---------------------------------------------");
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            String date = java.time.LocalDate.now().toString();
            transactions.add(new Transaction("Withdrawal", amount, date));
            System.out.println("*********************************************");
            System.out.println("Withdrawal Completed. Your Current balance: " + balance);
            System.out.println("*********************************************");
        } else {
            System.out.println("*********************************************");
            System.out.println("Your Balance is Insufficient");
            System.out.println("*********************************************");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.balance += amount;
            String date = java.time.LocalDate.now().toString();
            System.out.println("*********************************************");
            transactions.add(new Transaction("Transfer to " + recipient.getUserId(), amount, date));
            recipient.transactions.add(new Transaction("Transfer from " + getUserId(), amount, date));
            System.out.println("Transfer Completed. Your Current balance: " + balance);
            System.out.println("*********************************************");
        } else {
            System.out.println("Your Balance is Insufficient");
        }
    }

    public void showTransactionHistory() {
        System.out.println("Transaction History:");
        System.out.println("*********************************************");
        for (Transaction transaction : transactions) {
            System.out.println("Type: " + transaction.getType() +
                    ", Amount: " + transaction.getAmount() +
                    ", Date: " + transaction.getDate());
        }
        System.out.println("*********************************************");
    }
}

public class ATM_Interface {
    private static Scanner scanner = new Scanner(System.in);
    private static Account currentAccount;

    public static void main(String[] args) {
        new Account("SHASHANK", "987654", 100000);
        new Account("OasisInfobyte", "345678", 85000);

        // Perform ATM operations
        login();
        if (currentAccount != null) {
            showMenu();
        }
    }

    private static void login() {
        System.out.println("*********************************************");
        System.out.println("Welcome to the ATM !! ");
        System.out.print("Enter Your USER ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter user PIN : ");
        String userPin = scanner.nextLine();
        System.out.println("*********************************************");

        // Authenticate user
        // In a real-world scenario, this would involve querying a database to validate
        // the credentials
        // For simplicity, we are using hard-coded credentials here
        if (userId.equals("SHASHANK") && userPin.equals("987654")) {
            currentAccount = new Account(userId, userPin, 100000);
            System.out.println("*********************************************");
            System.out.println("Login successful!");
            System.out.println("Welcome Soham Ray ");
        } else if (userId.equals("OasisInfobyte") && userPin.equals("345678")) {
            currentAccount = new Account(userId, userPin, 85000);
            System.out.println("Login successful!");
            System.out.println("Welcome Oasis Infobyte ");
            System.out.println("*********************************************");
        } else {
            System.out.println("Invalid credentials. Login failed.");
            System.out.println("*********************************************");
        }
    }

    private static void showMenu() {
        boolean quit = false;

        while (!quit) {
            System.out.println("\n--- ATM  ---");
            System.out.println("Press 1 to Check Balance");
            System.out.println("Press 2 to Make a Withdrawal");
            System.out.println("Press 3 to Make a Deposit");
            System.out.println("Press 4 to Make a Transfer");
            System.out.println("Press 5 to See Transaction History");
            System.out.println("Press 6 to Quit");
            System.out.println("*********************************************");
            System.out.print("Enter Your Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    makeWithdrawal();
                    break;
                case 3:
                    makeDeposit();
                    break;
                case 4:
                    makeTransfer();
                    break;
                case 5:
                    showTransactionHistory();
                    break;
                case 6:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("*********************************************");
        System.out.println("Thank you for using the ATM. Have a Nice Day");
        System.out.println("*********************************************");
    }

    private static void checkBalance() {
        double balance = currentAccount.getBalance();
        System.out.println("Current balance: " + balance);
        System.out.println("*********************************************");
    }

    private static void makeWithdrawal() {
        System.out.println("*********************************************");
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        System.out.println("*********************************************");
        currentAccount.withdraw(amount);
    }

    private static void makeDeposit() {
        System.out.println("*********************************************");
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        System.out.println("*********************************************");
        currentAccount.deposit(amount);
    }

    private static void makeTransfer() {
        System.out.println("*********************************************");
        System.out.print("Enter recipient's user ID: ");
        String recipientId = scanner.nextLine();
        System.out.print("Enter transfer amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        System.out.println("*********************************************");

        Account recipient = new Account(recipientId, "", 0);
        currentAccount.transfer(recipient, amount);
    }

    private static void showTransactionHistory() {
        currentAccount.showTransactionHistory();
    }
}