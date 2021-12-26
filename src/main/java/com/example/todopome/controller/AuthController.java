package com.example.todopome.controller;

import com.example.todopome.entity.Account;
import com.example.todopome.enumEntity.RoleType;
import com.example.todopome.jwt.JwtUtils;
import com.example.todopome.repository.AccountRepository;
import com.example.todopome.request.LoginRequest;
import com.example.todopome.request.SignupRequest;
import com.example.todopome.response.JwtResponse;
import com.example.todopome.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/{id}")
    public Account getAccountByUsername(@PathVariable("id") String username){
        return accountRepository.findByUsername(username);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest) {

        if (accountRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Tài khoản đã tồn tại!"));
        }
        Account account = new Account();
        account.setPassword(encoder.encode(signupRequest.getPassword()));
        account.setUsername(signupRequest.getUsername());
        account.setRoles(RoleType.ROLE_USER.toString());
        accountRepository.save(account);

        return ResponseEntity.ok(new MessageResponse("Đăng ký thành công!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {
        Authentication authentication;
//       try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        } catch (AuthenticationException e) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Đăng nhập thất bại"));
//        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Account account = (Account) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt, account));
    }

}
