package org.example.service;
import jakarta.annotation.PostConstruct;
import org.example.entity.*;
import org.example.entity.Module;
import org.example.repository.*;
import org.example.vo.ModuleVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminModuleService {
    private final RolesRepository rolesRepository;
    private final ModuleRepository moduleRepository;
    private final ModuleAccessRepository moduleAccessRepository;
    private final UserModuleOverrideRespository overrideRepository;

    public AdminModuleService(RolesRepository rolesRepository,
                              ModuleRepository moduleRepository,
                              ModuleAccessRepository moduleAccessRepository,
                              UserModuleOverrideRespository overrideRepository){
        this.rolesRepository = rolesRepository;
        this.moduleRepository = moduleRepository;
        this.overrideRepository = overrideRepository;
        this.moduleAccessRepository = moduleAccessRepository;
    }

    public Module createModule(Module module){
        return moduleRepository.save(module);
    }

    public ModuleAccess assignModuleToRole(String roleName, String moduleName,boolean enabled,boolean requireAuth){
        System.out.println("Role name received: '" + roleName + "'");

        Role role = rolesRepository.findByRole(roleName).orElseThrow(()->new RuntimeException("ROLE NOT FOUND"));

        Module module = moduleRepository.findByName(moduleName).orElseThrow(()->new RuntimeException("MODULE NOT FOUND"));

        ModuleAccess access = moduleAccessRepository.findByRoleAndModule(role, module).orElse(new ModuleAccess());
        access.setRole(role);
        access.setModule(module);
        access.setEnabled(enabled);
        access.setRequireAuth(requireAuth);

        return moduleAccessRepository.save(access);

    }

    public List<ModuleAccess> getModuleByRole(String roleName){
        Role role = rolesRepository.findByRole(roleName).orElseThrow(()->new RuntimeException("ROLE NOT FOUND"));
        return moduleAccessRepository.findByRole(role);
    }

    public void initializeDefaultModules() {
        List<Module> defaultModules = List.of(
                createModuleIfNotExists("POS", "Point of Sale", "pos_icon", "#FF5722", true, true),
                createModuleIfNotExists("Dining", "Dining Management", "dining_icon", "#4CAF50", true, true),
                createModuleIfNotExists("PMS", "Property Management", "pms_icon", "#3F51B5", true, true)
        );
    }

    @PostConstruct
    public void onStartup() {
        initializeDefaultModules();
    }

    private Module createModuleIfNotExists(String name, String label, String icon, String themeColor, boolean enabled, boolean requireAuth) {
        return moduleRepository.findByName(name).orElseGet(() -> {
            Module module = new Module();
            module.setName(name);
            module.setLabel(label);
            module.setIcon(icon);
            module.setThemecolor(themeColor);
            module.setEnabled(enabled);
            module.setRequireAuth(requireAuth);
            return moduleRepository.save(module);
        });
    }

    public List<ModuleVO> getModuleVOsByRole(String roleName) {
        Role role = rolesRepository.findByRole(roleName).orElseThrow(() -> new RuntimeException("ROLE NOT FOUND"));
        List<ModuleAccess> accessList = moduleAccessRepository.findByRole(role);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User user)) {
            throw new RuntimeException("User not authenticated");
        }

        // 🔍 Load all overrides by user in advance (optimization)
        Map<Long, UserModuleOverride> overridesByModuleId = overrideRepository
                .findByUser(user)
                .stream()
                .collect(Collectors.toMap(
                        o -> o.getModule().getId(),
                        o -> o
                ));

        return accessList.stream().map(access -> {
            Module module = access.getModule();
            UserModuleOverride override = overridesByModuleId.get(module.getId());

            return new ModuleVO(
                    override != null && override.getName() != null ? override.getName() : module.getName(),
                    override != null && override.getLabel() != null ? override.getLabel() : module.getLabel(),
                    override != null && override.getIcon() != null ? override.getIcon() : module.getIcon(),
                    override != null && override.getThemeColor() != null ? override.getThemeColor() : module.getThemecolor(),
                    access.isEnabled(),
                    access.isRequireAuth()
            );
        }).collect(Collectors.toList());
    }
    // ✅ Returns all modules
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    // ✅ Returns module access by roleId
    public List<ModuleAccess> getModuleAccessByRoleId(Long roleId) {
        Role role = rolesRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));
        return moduleAccessRepository.findByRole(role);
    }
    public Optional<UserModuleOverride> getOverride(User user, Module module) {
        return overrideRepository.findByUserAndModule(user, module);
    }
    public UserModuleOverride saveOverride(User user, Module module, String name, String label, String icon, String themeColor) {
        UserModuleOverride override = overrideRepository
                .findByUserAndModule(user, module)
                .orElse(new UserModuleOverride());

        override.setUser(user);
        override.setModule(module);
        override.setName(name);
        override.setLabel(label);
        override.setIcon(icon);
        override.setThemeColor(themeColor);

        return overrideRepository.save(override);
    }

    public void saveUserModuleOverride(ModuleVO vo, User user) {
        Module module = moduleRepository.findByName(vo.getName())
                .orElseThrow(() -> new RuntimeException("Module not found: " + vo.getName()));

        UserModuleOverride override = overrideRepository
                .findByUserAndModule(user, module)
                .orElse(new UserModuleOverride());

        override.setUser(user);
        override.setModule(module);
        override.setName(vo.getName());
        override.setLabel(vo.getLabel());
        override.setIcon(vo.getIcon());
        override.setThemeColor(vo.getThemecolor());

        overrideRepository.save(override);
    }







}
