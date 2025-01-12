package ru.kata.spring.boot_security.demo.service;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        // Проверка на существование пользователя с таким именем
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        // Проверка на существование пользователя с таким email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        // Установка роли "ROLE_USER"
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            throw new RuntimeException("Роль ROLE_USER не найдена в базе данных");
        }

        // Устанавливаем роль для пользователя
        user.setRole(userRole); // Устанавливаем единственную роль

        // Хэшируем пароль
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Сохраняем пользователя в базе данных
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден с ID: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll(); // Предполагается, что у вас есть метод findAll() в UserRepository
    }

    public void changeUserRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    public void changeUserStatus(Long userId, boolean blocked) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role newRole = roleRepository.findByName(blocked ? "ROLE_BLOCKED" : "ROLE_USER");
        if (newRole == null) {
            throw new RuntimeException("Role not found");
        }
        user.setRole(newRole);
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user); // Сохранение или обновление пользователя
    }

    public void deleteById(long id) {
        userRepository.deleteById(id); // Удаление пользователя по ID
    }

    public List<User> searchUsers(String keyword) {
        return userRepository.findByUsernameContainingOrEmailContaining(keyword, keyword);
    }

    public boolean isAccountNonExpired() {
        return true; // Измените на вашу логику
    }

    public boolean isAccountNonLocked() {
        return true; // Измените на вашу логику
    }

    public boolean isCredentialsNonExpired() {
        return true; // Измените на вашу логику
    }

    public boolean isEnabled() {
        return true; // Измените на вашу логику
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found: " + email));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Добавьте логику для получения ролей пользователя и добавления их в authorities
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return authorities;
    }


    public List<Role> findByRoleName(String role) {
        return List.of();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
