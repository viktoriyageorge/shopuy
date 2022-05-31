package com.soa.shop.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soa.shop.model.UserProfile;
import com.soa.shop.repository.UserProfileRepository;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(@Autowired UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public boolean save(UserProfile user) {
        String email = user.getEmail();
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (!EmailClient.verify(email)) {
            return false;
        }

        String password = user.getPassword();
        if (password == null || password.isEmpty()) {
            return false;
        }

        String firstName = user.getFirstName();
        if (firstName == null || firstName.isEmpty()) {
            return false;
        }

        String lastName = user.getLastName();
        if (lastName == null || lastName.isEmpty()) {
            return false;
        }

        String address = user.getAddress();
        if (address == null || address.isEmpty()) {
            return false;
        }

        String telephone = user.getTelephone();
        if (telephone == null || telephone.isEmpty()) {
            return false;
        }

        String bankId = user.getBankId();
        if (bankId == null || bankId.isEmpty()) {
            return false;
        }

        userProfileRepository.save(user);
        return true;
    }

    public UserProfile getUserByEmail(String email) throws UsernameNotFoundException {
        return userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
