package org.example.repository;

import org.example.entity.Module;
import org.example.entity.User;
import org.example.entity.UserModuleOverride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserModuleOverrideRespository extends JpaRepository<UserModuleOverride,Long> {
    Optional<UserModuleOverride> findByUserAndModule(User user, Module module);
    List<UserModuleOverride> findByUser(User user);
}
