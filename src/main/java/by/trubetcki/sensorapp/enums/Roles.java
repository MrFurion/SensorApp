package by.trubetcki.sensorapp.enums;

public enum Roles {
    ADMIN("ROLE_ADMIN"),
    VIEWER("ROLE_VIEWER");

    private final String roleName;
    Roles(String roleName) {
        this.roleName = roleName;
    }
    public String getRoleName(){
        return roleName;
    }
}
