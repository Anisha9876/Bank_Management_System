package com.example.Bank_Anisha.Controller;

import com.example.Bank_Anisha.Entity.Account;
import com.example.Bank_Anisha.Security.JwtUtil;
import com.example.Bank_Anisha.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private BankRepository bankRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;  // ← ADD THIS

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        Account user = bankRepo.findByAccountHolderName(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // ✅ BCrypt comparison
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful!",
                    "token", token
            ));
        } else {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "Invalid username or password!"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        Account user = bankRepo.findByAccountHolderName(username);
        if (user != null) {
            return ResponseEntity.status(400)
                    .body(Map.of("message", "User already exists!"));
        }

        Account account = new Account();
        account.setBalance(1000);
        account.setAccountHolderName(username);
        account.setStatus("ACTIVE");
        account.setDeleted(false);
        account.setPassword(passwordEncoder.encode(password));  // ✅ BCrypt encoding

        bankRepo.save(account);
        return ResponseEntity.ok(Map.of("message", "Registration successful!"));
    }
}