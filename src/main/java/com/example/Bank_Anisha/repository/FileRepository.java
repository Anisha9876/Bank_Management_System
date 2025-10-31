package com.example.Bank_Anisha.repository;

import com.example.Bank_Anisha.Entity.Account;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FileRepository {
    private static final String FILE_NAME = "bank_data.txt";
    public void saveDetails(Account account){
        try(FileWriter fw=new FileWriter(FILE_NAME,true)){
            fw.write(account.getId() +","+account.getAccountHolderName()
                    +","+ account.getBalance()+","+account.getInterestEarned()
                    +" ," +account.getStatus()+ "\n");
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    private Account parseAccount(String line) {
        String[] parts = line.split(",");
        if (parts.length < 6) {
            throw new IllegalArgumentException("Invalid account data: " + line);
        }

        Account account = new Account();
        account.setId(Long.parseLong(parts[0].trim()));
        account.setAccountHolderName(parts[1].trim());
        account.setBalance(Double.parseDouble(parts[2].trim()));
        account.setInterestEarned(Double.parseDouble(parts[3].trim()));
        account.setStatus(parts[4].trim());
        return account;
    }
    public List<Account> readDetails(){
        List<Account> accounts= new ArrayList<>();
        try {
            List<String> accs = Files.readAllLines(Paths.get(FILE_NAME));

            for(String acc:accs){
                if (!acc.trim().isEmpty()) { // skip empty lines
                    accounts.add(parseAccount(acc));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;


    }
}
