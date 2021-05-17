package com.suhe.chat.ui;

import com.suhe.chat.authentication.AccessService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;

import static com.suhe.chat.authentication.CurrentUser.*;

@CssImport("./styles/styles.css")
@Push
public class MainView extends AppLayout implements AfterNavigationObserver {

    private H1 logo;
    private Button logoutButton;
    private AccessService accessService;

    private Button testButton = new Button("test");


    public MainView(AccessService accessService) {
        this.accessService = accessService;     // todo for tests purpose
        addToNavbar(header());
    }
    private Component header() {
        HorizontalLayout header = new HorizontalLayout();
        header.setSizeFull();
        header.setSpacing(false);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setClassName("main-header");
        header.getElement().getThemeList().add("dark");
        logo = new H1("Simple Chat ");

//        VerticalLayout testLayout = new VerticalLayout();
//        testLayout.setSpacing(false);
//        testLayout.setPadding(false);
//        testLayout.add(new Button("TEST", e -> {
//            getCurrentUsersSet().forEach(s -> System.out.println(s));
//        }));

        logoutButton = new Button("Log out", e -> {
            String currentUserName = (String) VaadinSession.getCurrent().getAttribute(USER_NAME);
            releaseLogoutUsername(currentUserName);
            VaadinSession.getCurrent().getSession().invalidate();
        });
        logoutButton.setClassName("logout-button");

        header.add(logo, testLayout, logoutButton);
        logoutButton.setVisible(false);

        return header;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        if (event.getLocation().getFirstSegment() == "chat") {
            logoutButton.setVisible(true);
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        if (VaadinSession.getCurrent().getAttribute(USER_NAME) != null) {
            logoutButton.setVisible(true);
        }
    }
}

