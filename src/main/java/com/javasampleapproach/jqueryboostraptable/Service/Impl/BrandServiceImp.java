package com.javasampleapproach.jqueryboostraptable.Service.Impl;

import com.javasampleapproach.jqueryboostraptable.Service.BrandService;
import com.javasampleapproach.jqueryboostraptable.model.Brand;
import com.javasampleapproach.jqueryboostraptable.model.Location;
import com.javasampleapproach.jqueryboostraptable.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
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

    @Override
    public Page<Brand> getAllBrands(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> findById(long brand) {
        return brandRepository.findById(brand);
    }

    @Override
    public void saveBrand(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public Brand getBrand(Long id) {
        return brandRepository.findById(id).get();
    }

    @Override
    public Optional<Brand> findByIdBrand(Long id) {
        return brandRepository.findById(id);
    }

}
