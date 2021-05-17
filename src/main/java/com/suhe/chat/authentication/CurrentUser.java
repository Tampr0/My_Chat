package com.suhe.chat.authentication;

import com.vaadin.flow.server.VaadinSession;

import java.util.HashSet;
import java.util.Set;

public class CurrentUser {

    public static final String USER_NAME = "username";

    private volatile static Set<String> currentUsersSet = new HashSet<>();

    public synchronized static boolean checkIfUsernameAvailable_thenAdd(String username,VaadinSession session) {
        return currentUsersSet.stream()
                .map(String::toLowerCase)
                .noneMatch(s -> s.equals(username.toLowerCase()))
                ? addUser(username, session)
                : false;
    }

    private synchronized static boolean addUser(String username, VaadinSession session) {
        session.setAttribute(USER_NAME, username);
        return currentUsersSet.add(username);
    }

    public static Set<String> getCurrentUsersSet() {
        return currentUsersSet;
    }

    public static void releaseLogoutUsername(String username) {
        if (currentUsersSet.remove(username)) {
        }
    }
}
