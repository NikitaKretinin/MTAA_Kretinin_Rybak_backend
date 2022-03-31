package fiit.mtaa.mtaa_backend.services;

import fiit.mtaa.mtaa_backend.models.User;

import java.io.*;
import java.util.*;

public class TokenManager {
    private static class UserData {
        public String role;
        public String login;
        public long id;

        public UserData(String role, String login, long id) {
            this.id = id;
            this.role = role;
            this.login = login;
        }

    }

    static Map<String, UserData> tokens = new HashMap<>();

    static public String createToken(User user) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i<20; i++) {
            key.append((char) (random.nextInt() % 25 + 97));
        }
        UserData data = new UserData(user.getUser_role(), user.getLogin(), user.getId());
        tokens.put(key.toString().toString(), data);
        return key.toString();
    }

    static public boolean validToken(String token, String requiredRole) {
        if (Objects.equals(requiredRole, "guest")) {
            return true;
        }
        return (Objects.equals(tokens.get(token).role, requiredRole));
    }
}
