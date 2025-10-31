package com.example.Bank_Anisha.Security;

import com.example.Bank_Anisha.Entity.Account;
import com.example.Bank_Anisha.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private BankRepository bankRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = bankRepo.findByAccountHolderName(username);

        if (account == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Build Spring Security user
        return User.builder()
                .username(account.getAccountHolderName())
                .password(account.getPassword())
                .roles("USER") // You can replace with real roles later
                .build();
    }
}

