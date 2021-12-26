package com.example.todopome.service;

import com.example.todopome.entity.Account;
import com.example.todopome.enumEntity.RoleType;
import com.example.todopome.repository.AccountRepository;
import com.example.todopome.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AccountService  {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder encoder;

    private static final Logger logger = Logger.getLogger(AccountService.class.getName());

    public Account loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        return Account.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(RoleType.ROLE_USER.toString())
                .build();
    }

    public boolean signup(SignupRequest signupRequest){
        if (accountRepository.existsByUsername(signupRequest.getUsername()))
            return false;
        Account account = Account.builder()
                .username(signupRequest.getUsername())
                .password(signupRequest.getPassword())
                .roles(RoleType.ROLE_USER.toString())
                .build();
        accountRepository.save(account);
        return true;
    }

}
