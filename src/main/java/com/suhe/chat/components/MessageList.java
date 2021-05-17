package com.suhe.chat.components;

import com.suhe.chat.domain.ChatMessage;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

import static com.suhe.chat.authentication.CurrentUser.USER_NAME;

public class MessageList extends VerticalLayout {

    public MessageList() {
        addClassName("message-list");
    }

    public void addMessage(ChatMessage message) {
        Paragraph messageParagraph = new Paragraph();
        Div messageDiv = new Div(messageParagraph);
        messageDiv.addClassName("simple-message");
        messageDiv.getElement().getThemeList().add("dark");

        // todo instead of comparing usernames try with VaadinSessions
        if (VaadinSession.getCurrent().getAttribute(USER_NAME).equals(message.getFrom())) {
            messageParagraph.setText(message.getMessage());
            setAlignSelf(Alignment.END, messageDiv);
        } else {
            messageParagraph.setText("[" + message.getFrom() + "] "+ message.getMessage());
            setAlignSelf(Alignment.START, messageDiv);
        }

        super.addComponentAsFirst(messageDiv);
        messageParagraph.getElement().callJsFunction("scrollIntoView");
    }
}
