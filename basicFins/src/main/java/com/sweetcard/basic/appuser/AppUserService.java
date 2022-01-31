package com.sweetcard.basic.appuser;

import com.sweetcard.basic.dao.jdbc.FinsprojectJdbc;
import com.sweetcard.basic.model.Finsprojectform;
import com.sweetcard.basic.registration.token.ConfirmationToken;
import com.sweetcard.basic.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private static String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    private AppUserRepository appUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }

    public String signUpUser(AppUser appUser){
        boolean userExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();
        if(userExists){
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();
        // TODO: Send confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

        // TODO: SEND EMAIL

        return  token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
