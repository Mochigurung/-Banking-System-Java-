package final_coursework;

public class Account extends Customer {
		private int balance;
		private int accountNumber;

		public Account(String fName, String lName, int accountNumber, int balance) {
			setFirstName(fName);
			setLastName(lName);
			this.balance = balance;
			this.accountNumber = accountNumber;
		}

		public int getBalance() {
			return balance;
		}

		public int getAccountNum() {
			return accountNumber;
		}

		public void deposit(int amount) {
			balance += amount;
		}

		public void withdraw(int amount) {
			balance -= amount;
		}

	}

