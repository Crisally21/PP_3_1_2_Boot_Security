package ru.kata.spring.boot_security.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class RegistrationService {

    @Autowired
    private RoleService roleService;
    private UserRepository userRepository;

    @Autowired
    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

@Transactional
public void save(User user) {
    user.getRoles().add(roleService.findByName("ROLE_USER"));
    userRepository.save(user);
}

    @PostConstruct
    public void initAdminUser() {
        User adminUser = userRepository.findByUsername("admin");


        if (adminUser == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setName("Administrator");
            admin.setAge(30);

            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findByName("ROLE_ADMIN"));
            admin.setRoles(roles);
            userRepository.save(admin);
        }
    }

}
