package final_coursework;
public class Transaction {
	public void transfer(Account sourceAccount, Account destinationAccount, int amount) {
		if (sourceAccount.getBalance() >= amount) {
			sourceAccount.withdraw(amount);
			destinationAccount.deposit(amount);
			System.out.println("Transfer successful!");
		} else {
			System.out.println("Insufficient funds for transfer.");
		}

	}
}
