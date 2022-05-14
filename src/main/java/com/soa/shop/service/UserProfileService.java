package com.soa.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soa.shop.model.UserProfile;
import com.soa.shop.repository.UserProfileRepository;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(@Autowired UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public void save(UserProfile user) {

        userProfileRepository.save(user);

    }
}
