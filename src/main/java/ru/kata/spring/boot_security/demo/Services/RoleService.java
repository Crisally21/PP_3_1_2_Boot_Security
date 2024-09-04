package ru.kata.spring.boot_security.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        List<Role> roles = roleRepository.findAll();
        if (roles.stream().noneMatch(role -> role.getName().equals("ADMIN"))) {
            roleRepository.save(new Role("ADMIN"));
        }
        if (roles.stream().noneMatch(role -> role.getName().equals("USER"))) {
            roleRepository.save(new Role("USER"));
        }
    }

    public Role findByName(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            roleRepository.save(role);
        }

        return role;
    }
}
