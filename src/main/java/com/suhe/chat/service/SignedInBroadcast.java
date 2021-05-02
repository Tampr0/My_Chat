package com.suhe.chat.service;

import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * This class is used strictly in one reason. To update asynchronously other UI's (other browser tabs or windows),
 * that were already opened before user chose his nick name. Session scoped.
 */

@Service
@VaadinSessionScope
public class SignedInBroadcast {

    private boolean isUserLoggedIn = false;
    private String username;

    private LinkedList<Consumer<String>> listeners = new LinkedList<>();
    private Executor executor = Executors.newSingleThreadExecutor();

    public synchronized Registration register(Consumer<String> listener) {
        listeners.add(listener);
        return () -> {
            synchronized (SignedInBroadcast.class) {
                listeners.remove(listener);
            }
        };
    }

    public synchronized void broadcast(String username) {
        this.isUserLoggedIn = true;
        this.username = username;
        for (Consumer<String> listener : listeners) {
            executor.execute(() -> listener.accept(username));
        }
    }

    public String getUsername() {
        return this.username;
    }

    /**
     * This condition is checked within ChatView onAttach() method. If false, listeners are added,
     * if true, no additional listeners will be added.
     */
    public boolean isUserLoggedIn() {
        return isUserLoggedIn;
    }
}

