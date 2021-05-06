package com.suhe.chat.service;

import com.vaadin.flow.server.*;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;

import java.util.HashSet;
import java.util.Set;

@SpringComponent
public class ChatConfig implements VaadinServiceInitListener {

    private static Set<String> usernameSet = new HashSet<>();

    public static synchronized boolean addUsername(String username) {
        return usernameSet.add(username.toLowerCase());
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {


    }
}
