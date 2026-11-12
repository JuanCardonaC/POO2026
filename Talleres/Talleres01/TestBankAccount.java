public class TestBankAccount {

    public static void main(String[] args) {
        BankAccount account = new BankAccount("001-123456", "Juan Perez", 500.0);

        System.out.println("Número de cuenta: " + account.getAccountNumber());
        System.out.println("Titular: " + account.getAccountHolder());
        System.out.println("Saldo inicial: " + account.getBalance());
        System.out.println();

        attemptDeposit(account, 200.0);
        attemptDeposit(account, -50.0);
        attemptDeposit(account, 0.0);
        
        attemptWithdrawal(account, 300.0);
        attemptWithdrawal(account, 10000.0);
        attemptWithdrawal(account, -20.0);
        attemptWithdrawal(account, 0.0);

        System.out.println("Saldo final: " + account.getBalance());
    }

    private static void attemptDeposit(BankAccount account, double amount) {
        boolean wasAccepted = account.deposit(amount);
        report("Depósito de " + amount, wasAccepted, account);
    }

    private static void attemptWithdrawal(BankAccount account, double amount) {
        boolean wasAccepted = account.withdraw(amount);
        report("Retiro de " + amount, wasAccepted, account);
    }

    private static void report(String operationDescription, boolean wasAccepted, BankAccount account) {
        if (wasAccepted) {
            System.out.println(operationDescription + " -> ACEPTADO");
        } else {
            System.out.println(operationDescription + " -> RECHAZADO");
        }
        System.out.println("Saldo actual: " + account.getBalance());
        System.out.println();
    }
}