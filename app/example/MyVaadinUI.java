package example;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

public class MyVaadinUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(new Button("Click me!", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Notification.show("Welcom to BookStore!!");
            }
        }));
    }
}