package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entity.Module;

import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module,Long> {
    Optional<Module> findByName(String name);
}
