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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


/**
 *  Ways to exchange messages inside component (UI)
 *  - set chat component into field, so passing pojo would be available.
 *  - binder
 *  - custom events
 *
 */

@Route("")
@CssImport("./styles/styles.css")
public class ChatView extends VerticalLayout {
    private String username = "ADMIN";


    ChatMessage chatMessage;
    MessageList messageList;        // for test's purpose

    public ChatView() {
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        addClassName("main-view");

        H1 header = new H1("Simple Chat");
        header.getElement().getThemeList().add("dark");
        
        add(header);

//        singInLayout();
        chatLayout();
    }

    private void singInLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        TextField usernameField = new TextField();
        Button startButton = new Button("Start chat");
        layout.add(usernameField, startButton);
        add(layout);

        startButton.addClickListener(click -> {
            this.username = usernameField.getValue();
            remove(layout);
            chatLayout();
        });
        startButton.addClickShortcut(Key.ENTER);
    }

    private void chatLayout() {

        // need to implement a subscriber so messageList could be updated whenever new message occur.
        // messageList.add(new Paragraph(message.getFrom() + " " + message.getMessage()));
        messageList = new MessageList();
        add(messageList, inputLayout());

        expand(messageList);

    }

    private Component inputLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        TextField messageField = new TextField();


        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layout.add(messageField, sendButton);

        layout.expand(messageField);

        sendButton.addClickListener(click -> {
            // I need to publish a message here, or send it to the publisher
            ChatMessage message = new ChatMessage(username, messageField.getValue());
            // this one is for tests if UI works properly
            messageList.add(new Paragraph(message.getFrom() + " " + message.getMessage()));
            messageField.clear();
            messageField.focus();
        });
        sendButton.addClickShortcut(Key.ENTER);
        messageField.focus();
        return layout;
    }

    public static class ChatMessageEvent extends ComponentEvent<ChatView> {

        public ChatMessageEvent(ChatView source, boolean fromClient) {
            super(source, false);
        }
    }

}
