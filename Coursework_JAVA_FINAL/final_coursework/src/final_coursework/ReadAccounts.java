package final_coursework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ReadAccounts {
	private String URL;

	public ReadAccounts(String URL) {
		this.URL = URL;
	}

	private List<String[]> readData() {
		List<String[]> data = new LinkedList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(URL))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				data.add(values);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public LinkedList<String> getFirstNames() {
		List<String[]> data = readData();
		LinkedList<String> firstNames = new LinkedList<>();
		for (String[] row : data) {
			firstNames.add(row[0]);
		}
		return firstNames;
	}

	public LinkedList<String> getLastNames() {
		List<String[]> data = readData();
		LinkedList<String> lastNames = new LinkedList<>();
		for (String[] row : data) {
			lastNames.add(row[1]);
		}
		return lastNames;
	}

	public LinkedList<Integer> getAccounts() {
	    List<String[]> data = readData();
	    LinkedList<Integer> accounts = new LinkedList<>();
	    for (String[] row : data) {
	        try {
	            int accountNum = Integer.parseInt(row[2]); // Convert String to Integer
	            accounts.add(accountNum);
	        } catch (NumberFormatException e) {
	            System.err.println("Invalid account number: " + row[2]);
	        }
	    }
	    return accounts;
	}



	public LinkedList<Integer> getBalances() {
		List<String[]> data = readData();
		LinkedList<Integer> balances = new LinkedList<>();
		for (String[] row : data) {
			balances.add(Integer.parseInt(row[3]));
		}
		return balances;
	}
}
