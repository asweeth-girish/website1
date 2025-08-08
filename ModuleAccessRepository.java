package org.example.repository;
import org.example.entity.Module;
import org.example.entity.ModuleAccess;
import org.example.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModuleAccessRepository extends JpaRepository<ModuleAccess,Long> {
    List<ModuleAccess> findByRole(Role role);
    Optional<ModuleAccess> findByRoleAndModule(Role role,Module module);

}
