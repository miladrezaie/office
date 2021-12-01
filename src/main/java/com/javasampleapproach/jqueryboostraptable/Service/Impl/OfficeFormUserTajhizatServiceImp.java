package com.javasampleapproach.jqueryboostraptable.Service.Impl;

import com.javasampleapproach.jqueryboostraptable.Service.OfficeFormUserTajhizatService;
import com.javasampleapproach.jqueryboostraptable.model.OfficeFormUserTajhizat;
import com.javasampleapproach.jqueryboostraptable.repository.OfficeFormUserTajhizatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeFormUserTajhizatServiceImp implements OfficeFormUserTajhizatService {
    private final OfficeFormUserTajhizatRepository officeFormUserTajhizatRepository;

    @Autowired
    public OfficeFormUserTajhizatServiceImp(OfficeFormUserTajhizatRepository officeFormUserTajhizatRepository) {
        this.officeFormUserTajhizatRepository = officeFormUserTajhizatRepository;
    }

    public List<OfficeFormUserTajhizat> getAllOfficeFormUserTajhizats() {
        return officeFormUserTajhizatRepository.findAll();
    }

    public Optional<OfficeFormUserTajhizat> findById(long officeFormUserTajhizat) {
        return officeFormUserTajhizatRepository.findById(officeFormUserTajhizat);
    }

    public void saveOfficeFormUserTajhizat(OfficeFormUserTajhizat officeFormUserTajhizat) {
        officeFormUserTajhizatRepository.save(officeFormUserTajhizat);
    }

    public void deleteOfficeFormUserTajhizat(Long id) {
        officeFormUserTajhizatRepository.deleteById(id);
    }

    public OfficeFormUserTajhizat getOfficeFormUserTajhizat(Long id) {
        return officeFormUserTajhizatRepository.findById(id).get();
    }


    public Optional<OfficeFormUserTajhizat> findByIdOfficeFormUserTajhizat(Long id) {
        return officeFormUserTajhizatRepository.findById(id);
    }
}
