package final_coursework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ReadAccounts reader = new ReadAccounts("account.csv");

        LinkedList<Account> accounts = new LinkedList<>();

        LinkedList<String> firstNames = reader.getFirstNames();
        LinkedList<String> lastNames = reader.getLastNames();
        LinkedList<Integer> accountNums = reader.getAccounts();
        LinkedList<Integer> balances = reader.getBalances();

        for (int i = 0; i < accountNums.size(); i++) {
            accounts.add(new Account(firstNames.get(i), lastNames.get(i), accountNums.get(i), balances.get(i)));
        }

        List<String> csvData = readCsvData("account.csv");

        new GUI(accounts, csvData);
    }

    private static List<String> readCsvData(String fileName) {
        List<String> csvData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                csvData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvData;
    }
}