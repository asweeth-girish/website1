package org.example.vo;



public class ModuleVO {

    private String name;
    private String label;
    private String icon;
    private String themecolor;
    private boolean enabled;
    private boolean requireAuth;
    public ModuleVO(){}
    public ModuleVO(String name, String label, String icon, String themecolor, boolean enabled, boolean requireAuth) {
        this.name = name;
        this.label = label;
        this.icon = icon;
        this.themecolor = themecolor;
        this.enabled = enabled;
        this.requireAuth = requireAuth;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getThemecolor() {
        return themecolor;
    }

    public void setThemecolor(String themecolor) {
        this.themecolor = themecolor;
    }

    public boolean isRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(boolean requireAuth) {
        this.requireAuth = requireAuth;
    }
}
