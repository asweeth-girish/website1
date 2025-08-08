package org.example.security;

import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.RolesRepository;
import org.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RolesRepository rolesRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            Role userRole = rolesRepository.findByRole("USER").orElseGet(() -> {
                Role r = new Role();
                r.setRole("USER");
                return rolesRepository.save(r);
            });

            Role adminRole = rolesRepository.findByRole("ADMIN").orElseGet(() -> {
                Role r = new Role();
                r.setRole("ADMIN");
                return rolesRepository.save(r);
            });


            if (userRepository.findByUsername("admin").isEmpty()) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin123")); // default password
                adminUser.setRoles(Set.of(adminRole));
                userRepository.save(adminUser);
                System.out.println(" Admin user created: username=admin, password=admin123");
            }
        };
    }
}
