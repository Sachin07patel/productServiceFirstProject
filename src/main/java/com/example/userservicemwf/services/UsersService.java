package com.example.userservicemwf.services;
import com.example.userservicemwf.models.Token;
import com.example.userservicemwf.models.User;
import com.example.userservicemwf.repository.TokenRepository;
import com.example.userservicemwf.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bcryptPasswordEncoder;
    private TokenRepository tokenRepository;


    public UsersService(UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder, TokenRepository tokenRepository){
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }
    public User signUp(String fullName, String email, String password){
        User u = new User();
        u.setName(fullName);
        u.setEmail(email);
        u.setHashedPassword(bcryptPasswordEncoder.encode(password));

        User user = userRepository.save(u);
        return user;
    }

    public Token logIn(String email, String password){
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            //user not exist
            return null;
        }

        User user = userOptional.get();

        if(!bcryptPasswordEncoder.matches(password,user.getHashedPassword())){
            //throw password not match exception
            return null;
        }
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plus(30, ChronoUnit.DAYS);

        // Convert LocalDate to Date
        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setUser(user);
        token.setExpiryAt(expiryDate);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        Token savedToken = tokenRepository.save(token);

        return savedToken;
    }

    public void logout(String token){
        Optional<Token> token1 = tokenRepository.findByValueAndDeletedEquals(token, false);

        if(token1.isEmpty()){
            //throw Token not exist or already expired
        }

        Token tkn = token1.get();

        tkn.setDeleted(true);
        tokenRepository.save(tkn);
    }

    public User validateToken(String token){
        Optional<Token> tkn = tokenRepository.findByValueAndDeletedEqualsAndExpiryAtGreaterThan(token , false, new Date());
        if(tkn.isEmpty()){
            return null;
        }

        return tkn.get().getUser();
    }
}
