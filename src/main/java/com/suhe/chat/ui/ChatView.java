package com.suhe.chat.ui;

import com.suhe.chat.components.MessageList;
import com.suhe.chat.domain.ChatMessage;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import static com.suhe.chat.authentication.CurrentUser.USER_NAME;

@Route(value = "chat", layout = MainView.class)
@PageTitle("W e l c o m e | C H A T") // todo - maybe pass nick here...
public class ChatView extends VerticalLayout implements BeforeLeaveObserver {

    private Flux<ChatMessage> messages;
    private Sinks.Many<ChatMessage> publisher;
    private MessageList messageList;

    private Disposable chatMessageReceiveRegistration;

    public ChatView(Flux<ChatMessage> messages, Sinks.Many<ChatMessage> publisher) {
        this.messages = messages;
        this.publisher = publisher;
        setSizeFull();

        buildChatLayout();
    }

    private void buildChatLayout() {
        messageList = new MessageList();
        HorizontalLayout layout = new HorizontalLayout();

        TextField messageField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.addClickShortcut(Key.ENTER);

        sendButton.addClickListener(click -> {
            publisher.tryEmitNext((new ChatMessage(
                    (String) VaadinSession.getCurrent().getAttribute(USER_NAME),
                    messageField.getValue())));
            messageField.clear();
            messageField.focus();
        });
        messageField.focus();

        layout.add(messageField, sendButton);
        layout.setWidthFull();
        layout.expand(messageField);

        add(messageList, layout);
        expand(messageList);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        chatMessageReceiveRegistration = messages.subscribe(message -> {
            attachEvent.getUI().access(() -> {
                messageList.addMessage(message);
            });
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        chatMessageReceiveRegistration.dispose();
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        BeforeLeaveEvent.ContinueNavigationAction action = event.postpone();
        Dialog confirmDialog = new Dialog();
        Button confirmButton = new Button("Confirm", e -> {
            action.proceed();
            confirmDialog.close();
        });
        confirmDialog.add(confirmButton);
        confirmDialog.open();
    }
}
