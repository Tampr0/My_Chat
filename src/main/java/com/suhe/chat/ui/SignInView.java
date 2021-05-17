package com.suhe.chat.ui;

import com.suhe.chat.authentication.AccessService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;


@Route(value = "join", layout = MainView.class)
@PageTitle("J o i n | C H A T")
public class SignInView extends VerticalLayout {

    private H2 header = new H2("Welcome");
    private TextField usernameTextField;
    private Button startButton;

    private AccessService accessService;
    private Registration registration;

    public SignInView(AccessService accessService) {
        this.accessService = accessService;
        this.usernameTextField = buildUsernameTextField();
        this.startButton = buildStartButton();
        setSizeFull();
        setSpacing(false);
        setAlignItems(Alignment.CENTER);

        setClassName("sign-in-layout");
        header.setClassName("join-chat");
        startButton.setClassName("start-button");
        usernameTextField.setClassName("username");

        add(header, usernameTextField, startButton);
    }


    private Button buildStartButton() {
        Button join = new Button("Join chat");
        join.setEnabled(false);
        join.addClickShortcut(Key.ENTER);
        join.addClickListener(click -> {
            String username = usernameTextField.getValue();
            if (!accessService.tryAddUsername(username)) {
                Notification.show(username + " is already in use. Pick another one.",
                        3000,
                        Notification.Position.MIDDLE);
            }
        });
        return join;
    }

    private TextField buildUsernameTextField() {
        TextField username = new TextField();
        username.setPlaceholder("Your nick...");
        username.setLabel("Username");
        username.setClearButtonVisible(true);
        username.setValueChangeMode(ValueChangeMode.EAGER);
        // validation
        username.setMinLength(2);
        username.setMaxLength(15);
        username.setRequired(true);
        username.setErrorMessage("Minimum 2 characters");

        username.addValueChangeListener(event -> {
            if (!username.isInvalid()) {
                startButton.setEnabled(true);
            } else {
                startButton.setEnabled(false);
            }
        });
        return username;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        registration = accessService.registerJoinedListener(username -> {
            ui.access(() -> {
                ui.navigate("chat");
            });
        });

    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        registration.remove();
    }
}
