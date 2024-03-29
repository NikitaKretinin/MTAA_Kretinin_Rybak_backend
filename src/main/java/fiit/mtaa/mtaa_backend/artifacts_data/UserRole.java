package fiit.mtaa.mtaa_backend.artifacts_data;

public enum UserRole {

    GUEST("guest", 0), MANAGER("manager", 1);

    private final String key;
    private final Integer value;

    UserRole(String str, int i) {
        this.key = str;
        this.value = i;
    }

    public static boolean contains(String test) {

        for (UserRole c : UserRole.values()) {
            if (c.getKey().equals(test)) {
                return true;
            }
        }
        return false;
    }

    public Integer getValue(){
        return value;
    }

    public String getKey() {
        return key;
    }
}
