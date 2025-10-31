package com.example.Bank_Anisha.Controller;

import com.example.Bank_Anisha.Entity.Account;
import com.example.Bank_Anisha.Security.JwtUtil;
import com.example.Bank_Anisha.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private BankRepository bankRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // POST: /auth/login
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {

        Account user = bankRepo.findByAccountHolderName(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // generate JWT
            String token = jwtUtil.generateToken(username);
            return "Login successful! Your token: " + token;
        } else {
            return "Invalid username or password!";
        }
    }
    @PostMapping("/register")
    public String register(@RequestParam String username ,@RequestParam String password){
      Account user= bankRepo.findByAccountHolderName(username);
      if(user != null){
          return "User already exits";
      }
        Account account= new Account();
        account.setBalance(1000);
        account.setAccountHolderName(username);
        account.setStatus("ACTIVE");
        account.setDeleted(false);
        account.setPassword(password);
        return "Registration successful!";


    }

}