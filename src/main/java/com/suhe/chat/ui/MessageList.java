package com.suhe.chat.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

public class MessageList extends Div {
    public MessageList() {
        addClassName("message-list");
    }

    @Override
    public void add(Component... components) {
        super.add(components);

        components[components.length - 1]   // getting the last added component
                .getElement()
                .callJsFunction("scrollIntoView");

        // scroll enabling is in styles.css
    }
}
