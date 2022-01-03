package com.javasampleapproach.jqueryboostraptable.Service;

import com.javasampleapproach.jqueryboostraptable.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface BrandService {

    Page<Brand> getAllBrands(Pageable page);

    List<Brand> findAll();

    Optional<Brand> findById(long brand);

    void saveBrand(Brand brand);

    void deleteBrand(Long id);

    Brand getBrand(Long id);

    Optional<Brand> findByIdBrand(Long id);

}
