package com.javasampleapproach.jqueryboostraptable.Service.Impl;

import com.javasampleapproach.jqueryboostraptable.Service.RoleService;
import com.javasampleapproach.jqueryboostraptable.model.Location;
import com.javasampleapproach.jqueryboostraptable.model.Role;
import com.javasampleapproach.jqueryboostraptable.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<Role> getAllRoles(Pageable page) {
        return roleRepository.findAll(page);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role getRole(Long id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public Optional<Role> findByIdRole(Long id) {
        return roleRepository.findById(id);
    }
}
