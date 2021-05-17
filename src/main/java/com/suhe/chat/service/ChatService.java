package com.suhe.chat.service;

import com.suhe.chat.ui.SignInView;
import com.vaadin.flow.server.*;
import org.springframework.stereotype.Service;

import static com.suhe.chat.authentication.CurrentUser.USER_NAME;

@Service
public class ChatService implements VaadinServiceInitListener {


    @Override
    public void serviceInit(ServiceInitEvent event) {

        event.getSource().addUIInitListener(uiInit -> {
            uiInit.getUI().addBeforeEnterListener(enterEvent -> {
                if (VaadinSession.getCurrent().getAttribute(USER_NAME) == null
                        && !SignInView.class.equals(enterEvent.getNavigationTarget())) {
                    enterEvent.forwardTo("join");
                }
                else if (VaadinSession.getCurrent().getAttribute(USER_NAME) != null) {
                    enterEvent.forwardTo("chat");
                }
            });
        });
    }

    private VaadinRequest getCurrentRequest() {
        VaadinRequest request = VaadinService.getCurrentRequest();
        if (request == null) {
            throw new IllegalStateException(
                    "No request bound to current thread.");
        }
        return request;
    }

}
