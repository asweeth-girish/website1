package org.example.vo;

public class ModuleAssignmentRequest {
    private String role;
    private String module;
    private boolean enabled;
    private boolean requireAuth;

    public ModuleAssignmentRequest() {}

    public ModuleAssignmentRequest(String role, String module, boolean enabled, boolean requireAuth) {
        this.role = role;
        this.module = module;
        this.enabled = enabled;
        this.requireAuth = requireAuth;
    }

    // Getters and Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(boolean requireAuth) {
        this.requireAuth = requireAuth;
    }
}
