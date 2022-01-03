package com.javasampleapproach.jqueryboostraptable.Service;

import com.javasampleapproach.jqueryboostraptable.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Page<Role> getAllRoles(Pageable page);

    List<Role> findAll();

    void saveRole(Role role);

    void deleteRole(Long id);

    Role getRole(Long id);

    Optional<Role> findByIdRole(Long id);
}
