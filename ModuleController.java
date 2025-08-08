package org.example.controller;

import org.example.entity.Module;
import org.example.entity.ModuleAccess;
import org.example.entity.User;
import org.example.service.AdminModuleService;
import org.example.vo.ModuleAssignmentRequest;
import org.example.vo.ModuleVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend to access these APIs
@RestController
@RequestMapping("/modules")
public class ModuleController {

    private final AdminModuleService adminModuleService;

    public ModuleController(AdminModuleService adminModuleService) {
        this.adminModuleService = adminModuleService;
    }

    // Create a new module (optional, used for initial setup)
    @PostMapping("/create")
    public ResponseEntity<Module> createModule(@RequestBody Module module) {
        return ResponseEntity.ok(adminModuleService.createModule(module));
    }

    // Get all available modules
    @GetMapping("/all")
    public ResponseEntity<List<Module>> getAllModules() {
        return ResponseEntity.ok(adminModuleService.getAllModules());
    }

    // Assign a module to a role
    @PostMapping("/access/assign")
    public ResponseEntity<ModuleAccess> assignModuleToRole(@RequestBody ModuleAssignmentRequest request) {
        ModuleAccess access = adminModuleService.assignModuleToRole(
                request.getRole(),
                request.getModule(),
                request.isEnabled(),
                request.isRequireAuth()
        );
        return ResponseEntity.ok(access);
    }

    // Get module access settings for a role
    @GetMapping("/access/role/{roleId}")
    public ResponseEntity<List<ModuleAccess>> getAccessByRole(@PathVariable Long roleId) {
        return ResponseEntity.ok(adminModuleService.getModuleAccessByRoleId(roleId));
    }

    // View module data for a role (as VO)
    @GetMapping("/role/{role}/view")
    public ResponseEntity<List<ModuleVO>> getModuleVOsByRole(@PathVariable String role) {
        return ResponseEntity.ok(adminModuleService.getModuleVOsByRole(role));
    }

    @PostMapping("/override/save")
    public ResponseEntity<?> saveUserModuleOverride(@RequestBody ModuleVO vo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }

        adminModuleService.saveUserModuleOverride(vo, user);
        return ResponseEntity.ok("Override saved");
    }



}
