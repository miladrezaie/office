package com.javasampleapproach.jqueryboostraptable.Service.Impl;

import com.javasampleapproach.jqueryboostraptable.Service.BrandService;
import com.javasampleapproach.jqueryboostraptable.model.Brand;
import com.javasampleapproach.jqueryboostraptable.model.Location;
import com.javasampleapproach.jqueryboostraptable.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImp implements BrandService {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImp(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Optional<Brand> findById(long brand) {
        return brandRepository.findById(brand);
    }

    public void saveBrand(Brand brand) {
        brandRepository.save(brand);
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    public Brand getBrand(Long id) {
        return brandRepository.findById(id).get();
    }


    public Optional<Brand> findByIdBrand(Long id) {
        return brandRepository.findById(id);
    }
}
