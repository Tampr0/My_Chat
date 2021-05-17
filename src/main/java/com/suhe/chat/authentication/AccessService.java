package com.suhe.chat.authentication;

import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static com.suhe.chat.authentication.CurrentUser.*;

@Service
@VaadinSessionScope
public class AccessService {

    private LinkedList<Consumer<String>> listeners = new LinkedList<>();
    private Executor executor = Executors.newSingleThreadExecutor();

    public List<Consumer<String>> getListeners() {
        return this.listeners;
    }

    public synchronized boolean tryAddUsername(String username) {
        if (checkIfUsernameAvailable_thenAdd(username, VaadinSession.getCurrent())) {
            joinChat(username);
            return true;
        } else {
            return false;
        }
    }

    private synchronized void joinChat(String username) {
        for (Consumer<String> listener : listeners) {
            executor.execute(() -> listener.accept(username));
        }
    }

    private synchronized void announceNewUser() {

    }

    public synchronized Registration registerJoinedListener(Consumer<String> listener) {
        listeners.add(listener);
        return () -> {
            synchronized (AccessService.class) {
                listeners.remove(listener);
            }
        };
    }
}

