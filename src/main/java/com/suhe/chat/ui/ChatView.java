package com.suhe.chat.ui;

import com.suhe.chat.domain.ChatMessage;
import com.suhe.chat.service.SignedInBroadcast;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.UnicastProcessor;

import java.util.Objects;

import static com.suhe.chat.service.ChatConfig.*;

@Route("")
@CssImport("./styles/styles.css")
@Push
public class ChatView extends VerticalLayout {

    private final Sinks.Many<ChatMessage> publisher;
    private final Flux<ChatMessage> messages;
    private String username;
    private HorizontalLayout singInLayout = new HorizontalLayout();

    private Registration registration;
    private Disposable chatMessageReceiveRegistration;
    private SignedInBroadcast signedInBroadcast;

    public ChatView(Sinks.Many<ChatMessage> publisher,
                    Flux<ChatMessage> messages,
                    SignedInBroadcast signedInBroadcast) {
        this.publisher = publisher;
        this.messages = messages;
        this.signedInBroadcast = signedInBroadcast;

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        addClassName("main-view");
        H1 header = new H1("Simple Chat");
        header.getElement().getThemeList().add("dark");
        add(header);
    }

    private void buildSingInLayout() {
        this.singInLayout = new HorizontalLayout();
        TextField usernameField = new TextField();
        usernameFieldSetting(usernameField);
        Button startButton = new Button("Start chat");

        startButton.setEnabled(false);
        singInLayout.add(usernameField, startButton);
        add(singInLayout);

        // sets startButton enable if name is valid
        usernameField.addValueChangeListener(event -> {
            if (!usernameField.isInvalid()) {
                startButton.setEnabled(true);
            } else {
                startButton.setEnabled(false);
            }
        });

        startButton.addClickShortcut(Key.ENTER);
        startButton.addClickListener(click -> {
            if (addUsername(usernameField.getValue())) {
                signedInBroadcast.broadcast(usernameField.getValue());
                this.username = usernameField.getValue();
            } else {
                Notification.show(usernameField.getValue() + " is already in use. Pick another one.",
                        3000,
                        Notification.Position.MIDDLE);
            }
        });
    }

    private void chatLayout() {
        MessageList messageList = new MessageList();
        add(signOut(), messageList, inputLayout());

        chatMessageReceiveRegistration = messages.subscribe(message -> {
            getUI().ifPresent(ui -> {
                ui.access(() -> messageList.addMessage(message, username, message.getRandom()));
            });
        });

        expand(messageList);
    }

    private Component inputLayout() {
        HorizontalLayout layout = new HorizontalLayout();

        TextField messageField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.addClickShortcut(Key.ENTER);

        sendButton.addClickListener(click -> {
            publisher.tryEmitNext((new ChatMessage(username, messageField.getValue())));
            messageField.clear();
            messageField.focus();
        });
        messageField.focus();

        layout.add(messageField, sendButton);
        layout.setWidthFull();
        layout.expand(messageField);
        return layout;
    }

    private Component signOut() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setJustifyContentMode(JustifyContentMode.END);

        layout.add(new Button("Sign out", e -> {
            VaadinSession.getCurrent().getSession().invalidate();
        }));

        return layout;
    }

    private void usernameFieldSetting(TextField usernameField) {
        // usual settings
        usernameField.setPlaceholder("Your nick...");
        usernameField.setClearButtonVisible(true);
        usernameField.setValueChangeMode(ValueChangeMode.EAGER);

        // validation arguments
        usernameField.setMinLength(2);
        usernameField.setMaxLength(15);
        usernameField.setRequired(true);
        usernameField.setErrorMessage("Minimum 3 characters");
    }

    /**
     *  IsUserLoggedIn session scoped, static field is checked.
     *  (It turns to true after startButton is clicked with positive username validation)
     *      If false - listener is registered.
     *          Listener - checks if isDone() - it allows to automatically removes listener after it is done.
     *      If true - no additional listeners will be added.
     */

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        if (!signedInBroadcast.isUserLoggedIn())   {
            buildSingInLayout();    //added
            registration = signedInBroadcast.register(username -> {

                attachEvent.getUI().access(() -> {
                    this.username = username;
                    remove(singInLayout);
                    chatLayout();
                });
            });

        } else {
            this.username = signedInBroadcast.getUsername();
            chatLayout();
        }

        attachEvent.getSession();

    }

    /** In case implement inner routing in future. */
    @Override
    protected void onDetach(DetachEvent detachEvent) {
        if (!Objects.isNull(registration)) {
            registration.remove();
            registration = null;
        }
        chatMessageReceiveRegistration.dispose();
    }
}

