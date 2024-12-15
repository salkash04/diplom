package ru.kata.spring.boot_security.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        // �������� �� ������������� ������������ � ����� ������
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("������������ � ����� ������ ��� ����������");
        }

        // �������� �� ������������� ������������ � ����� email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("������������ � ����� email ��� ����������");
        }

        // ��������� ���� "ROLE_USER"
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            throw new RuntimeException("���� ROLE_USER �� ������� � ���� ������");
        }

        // ������������� ���� ��� ������������
        user.setRole(userRole); // ������������� ������������ ����

        // �������� ������
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // ��������� ������������ � ���� ������
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("������������ �� ������ � ID: " + id));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll(); // ��������������, ��� � ��� ���� ����� findAll() � UserRepository
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user); // ���������� ��� ���������� ������������
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id); // �������� ������������ �� ID
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // �������� �� ���� ������
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // �������� �� ���� ������
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // �������� �� ���� ������
    }

    @Override
    public boolean isEnabled() {
        return true; // �������� �� ���� ������
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found: " + email));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // �������� ������ ��� ��������� ����� ������������ � ���������� �� � authorities
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return authorities;
    }


    @Override
    public List<Role> findByRoleName(String role) {
        return List.of();
    }

    @Override
    public List<Role> getAllRoles() {
        return List.of();
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
