package final_coursework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.Color;

public class GUI extends JFrame {
	private Transaction transferObject;
	private StringBuilder sbAllData;
	private List<String> csvData;
	private LinkedList<Account> globalAccounts;
	private JLabel showAllData;
	private JButton showAllButton, depositButton, withdrawButton, transferButton;
	private JTextField accDeposit, accWithdraw, acc1Transfer, acc2Transfer, depositInput, withdrawInput, transferAmount;

	public GUI(LinkedList<Account> accounts, List<String> csvData) {
		super("Banking System");
		getContentPane().setLayout(null);
		globalAccounts = accounts;
		this.csvData = csvData;
		transferObject = new Transaction();

		sbAllData = new StringBuilder();

		showAllData = new JLabel();
		showAllData.setHorizontalAlignment(JLabel.LEFT);
		showAllData.setVerticalAlignment(JLabel.TOP);
		showAllData.setVerticalTextPosition(JLabel.TOP);
		showAllData.setHorizontalTextPosition(JLabel.LEFT);

		showAllButton = new JButton("Show All");
		showAllButton.setBackground(Color.GREEN);
		depositButton = new JButton("Deposit");
		withdrawButton = new JButton("Withdraw");
		transferButton = new JButton("Transfer");
		accDeposit = new JTextField(10);
		accWithdraw = new JTextField(10);
		acc1Transfer = new JTextField(10);
		acc2Transfer = new JTextField(10);
		depositInput = new JTextField(10);
		withdrawInput = new JTextField(10);
		transferAmount = new JTextField(10);

		showAllData.setBounds(20, 20, 545, 200);
		showAllButton.setBounds(20, 240, 100, 30);
		depositButton.setBounds(20, 290, 100, 30);
		withdrawButton.setBounds(20, 340, 100, 30);
		transferButton.setBounds(20, 390, 100, 30);
		accDeposit.setBounds(140, 290, 100, 30);
		accWithdraw.setBounds(140, 340, 100, 30);
		acc1Transfer.setBounds(140, 390, 100, 30);
		acc2Transfer.setBounds(260, 390, 100, 30);
		depositInput.setBounds(260, 290, 100, 30);
		withdrawInput.setBounds(260, 340, 100, 30);
		transferAmount.setBounds(380, 390, 100, 30);

		getContentPane().add(showAllData);
		getContentPane().add(showAllButton);
		getContentPane().add(depositButton);
		getContentPane().add(withdrawButton);
		getContentPane().add(transferButton);
		getContentPane().add(accDeposit);
		getContentPane().add(accWithdraw);
		getContentPane().add(acc1Transfer);
		getContentPane().add(acc2Transfer);
		getContentPane().add(depositInput);
		getContentPane().add(withdrawInput);
		getContentPane().add(transferAmount);

		JButton resetButton = new JButton("Reset");
		resetButton.setBackground(Color.ORANGE);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accDeposit.setText("");
				accWithdraw.setText("");
				acc1Transfer.setText("");
				acc2Transfer.setText("");
				depositInput.setText("");
				withdrawInput.setText("");
				transferAmount.setText("");
			}
		});
		resetButton.setBounds(151, 244, 89, 23);
		getContentPane().add(resetButton);

		HandlerClass handler = new HandlerClass();
		showAllButton.addActionListener(handler);
		depositButton.addActionListener(handler);
		withdrawButton.addActionListener(handler);
		transferButton.addActionListener(handler);

		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private class HandlerClass implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == showAllButton) {
				StringBuilder htmlBuilder = new StringBuilder();
				htmlBuilder.append("<html><body>");
				for (String row : csvData) {
					htmlBuilder.append("<p>").append(row.replaceAll(",", "&nbsp;&nbsp;&nbsp;")).append("</p>");
				}
				htmlBuilder.append("</body></html>");
				String htmlData = htmlBuilder.toString();

				sbAllData = new StringBuilder();
				for (Account account : globalAccounts) {
					sbAllData.append("Account Number: ").append(account.getAccountNum()).append(", Name: ")
							.append(account.getFirstName()).append(" ").append(account.getLastName())
							.append(", Balance: ").append(account.getBalance()).append("\n\n");
				}

				showAllData.setText("<html>" + htmlData + "<br><br>" + sbAllData.toString() + "</html>");
			} else if (e.getSource() == depositButton) {
				try {
					int accountNum = Integer.parseInt(accDeposit.getText());
					int amount = Integer.parseInt(depositInput.getText());
					Account account = findAccount(accountNum);
					if (account != null) {
						account.deposit(amount);
						updateAccountData();
						showAllData.setText(sbAllData.toString());
					} else {
						showAllData.setText("Account not found.");
					}
				} catch (NumberFormatException ex) {
					showAllData.setText("Invalid input. Please enter a valid account number and amount.");
				}
			} else if (e.getSource() == withdrawButton) {
				try {
					int accountNum = Integer.parseInt(accWithdraw.getText());
					int amount = Integer.parseInt(withdrawInput.getText());
					Account account = findAccount(accountNum);
					if (account != null) {
						if (account.getBalance() >= amount) {
							account.withdraw(amount);
							updateAccountData();
							showAllData.setText(sbAllData.toString());
						} else {
							showAllData.setText("Insufficient balance.");
						}
					} else {
						showAllData.setText("Account not found.");
					}
				} catch (NumberFormatException ex) {
					showAllData.setText("Invalid input. Please enter a valid account number and amount.");
				}
			} else if (e.getSource() == transferButton) {
				try {
					int accountNum1 = Integer.parseInt(acc1Transfer.getText());
					int accountNum2 = Integer.parseInt(acc2Transfer.getText());
					int amount = Integer.parseInt(transferAmount.getText());
					Account account1 = findAccount(accountNum1);
					Account account2 = findAccount(accountNum2);
					if (account1 != null && account2 != null) {
						if (account1.getBalance() >= amount) {
							transferObject.transfer(account1, account2, amount);
							updateAccountData();
							showAllData.setText(sbAllData.toString());
						} else {
							showAllData.setText("Insufficient balance in account " + accountNum1);
						}
					} else {
						showAllData.setText("One or both accounts not found.");
					}
				} catch (NumberFormatException ex) {
					showAllData.setText("Invalid input. Please enter valid account numbers and amount.");
				}
			}
		}

		private Account findAccount(int accountNum) {
			for (Account account : globalAccounts) {
				if (account.getAccountNum() == accountNum) {
					return account;
				}
			}
			return null;
		}

		private void updateAccountData() {
			StringBuilder htmlBuilder = new StringBuilder();
			htmlBuilder.append("<html>");
			for (Account account : globalAccounts) {
				htmlBuilder.append("Account Number: ").append(account.getAccountNum()).append(", Name: ")
						.append(account.getFirstName()).append(" ").append(account.getLastName()).append(", Balance: ")
						.append(account.getBalance()).append("<br>");
			}
			htmlBuilder.append("</html>");
			sbAllData = htmlBuilder;
		}
	}
}