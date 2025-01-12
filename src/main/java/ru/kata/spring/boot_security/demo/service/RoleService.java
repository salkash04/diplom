package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.security.Permission;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void updateRoleName(Long roleId, String newName) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        role.setName(newName);
        roleRepository.save(role);
    }

    public Role createRole(String name) {
        Role newRole = new Role();
        newRole.setName(name);
        return roleRepository.save(newRole);
    }

    public void deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }
}

