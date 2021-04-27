package com.suhe.chat.ui;

import com.suhe.chat.backend.ChatMessage;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;


/**
 *  Ways to exchange messages inside component (UI) within one session
 *  - set chat component into field, so passing pojo would be available.
 *  - binder
 *  - custom events
 *
 *
 */

@Route("")
@CssImport("./styles/styles.css")
@Push
public class ChatView extends VerticalLayout {
    private String username;
    private final UnicastProcessor<ChatMessage> publisher;
    private final Flux<ChatMessage> messages;


    public ChatView(UnicastProcessor<ChatMessage> publisher, Flux<ChatMessage> messages) {
        this.publisher = publisher;
        this.messages = messages;
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        addClassName("main-view");

        H1 header = new H1("Simple Chat");
        header.getElement().getThemeList().add("dark");
        
        add(header);

        singInLayout();
    }

    private void singInLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        TextField usernameField = new TextField();
        Button startButton = new Button("Start chat");
        layout.add(usernameField, startButton);
        add(layout);

        startButton.addClickShortcut(Key.ENTER);
        startButton.addClickListener(click -> {
            this.username = usernameField.getValue();
            remove(layout);
            chatLayout();
        });

    }

    private void chatLayout() {
        MessageList messageList = new MessageList();
        add(messageList, inputLayout());

        messages.subscribe(message -> {
            getUI().ifPresent(ui ->
                    ui.access(() ->
                            messageList.add(new Paragraph(
                    message.getFrom() + " " + message.getMessage()))
            ));
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
            publisher.onNext(new ChatMessage(username, messageField.getValue()));
            messageField.clear();
            messageField.focus();
        });
        messageField.focus();

        layout.add(messageField, sendButton);
        layout.setWidthFull();
        layout.expand(messageField);
        return layout;
    }

}
