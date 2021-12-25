package com.javasampleapproach.jqueryboostraptable.Service.Impl;

import com.javasampleapproach.jqueryboostraptable.Service.UserCancelOfficeDescriptionService;
import com.javasampleapproach.jqueryboostraptable.model.OfficeFormUserTajhizat;
import com.javasampleapproach.jqueryboostraptable.model.UserCancelOfficeDescription;
import com.javasampleapproach.jqueryboostraptable.repository.OfficeFormUserTajhizatRepository;
import com.javasampleapproach.jqueryboostraptable.repository.UserCancelOfficeDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCancelOfficeDescriptionImp implements UserCancelOfficeDescriptionService {


    private final UserCancelOfficeDescriptionRepository userCancelOfficeDescriptionRepository;


    @Autowired
    public UserCancelOfficeDescriptionImp(UserCancelOfficeDescriptionRepository userCancelOfficeDescriptionRepository) {
        this.userCancelOfficeDescriptionRepository = userCancelOfficeDescriptionRepository;
    }

    public List<UserCancelOfficeDescription> getAllUserCancelOfficeDescription() {
        return userCancelOfficeDescriptionRepository.findAll();
    }

    public Optional<UserCancelOfficeDescription> findById(long userCancelOfficeDescription) {
        return userCancelOfficeDescriptionRepository.findById(userCancelOfficeDescription);
    }

    public void saveUserCancelOfficeDescription(UserCancelOfficeDescription officeFormUserTajhizat) {
        userCancelOfficeDescriptionRepository.save(officeFormUserTajhizat);
    }

    public void deleteUserCancelOfficeDescription(Long id) {
        userCancelOfficeDescriptionRepository.deleteById(id);
    }

    public UserCancelOfficeDescription getUserCancelOfficeDescription(Long id) {
        return userCancelOfficeDescriptionRepository.findById(id).get();
    }


    public Optional<UserCancelOfficeDescription> findByIdUserCancelOfficeDescription(Long id) {
        return userCancelOfficeDescriptionRepository.findById(id);
    }

}
