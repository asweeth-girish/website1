package org.example.controller;

import org.example.entity.Role;
import org.example.repository.RolesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "http://localhost:4200")
public class RoleController {

    private final RolesRepository rolesRepository;

    public RoleController(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(rolesRepository.findAll());
    }
}
