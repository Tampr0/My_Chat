package com.suhe.chat.ui;

import com.suhe.chat.domain.ChatMessage;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 *  Added two extra features.
 *  1) new messages appears at the bottom of the view.
 *      - in .css file, I've set message-list "flex-direction" property to "column-reverse"
 *      - components are passed via addComponentsAsFirst
 *  2) messages are displayed on both sides of the view, depends on who sends it.
 *      - simple if statement compared user names.
 */

public class MessageList extends VerticalLayout {

    public MessageList() {
        addClassName("message-list");
    }

    public void addMessage(ChatMessage message, String userName) {
        Paragraph messageParagraph = new Paragraph();
        Div messageDiv = new Div(messageParagraph);
        messageDiv.addClassName("simple-message");
        messageDiv.getElement().getThemeList().add("dark");

        if (message.getFrom().equals(userName)) {
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
