package com.mySampleApplication.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class LoginDialog extends DialogBox {

    public LoginDialog(){
        FlexTable table = new FlexTable();
        this.setWidget(table);
        setText("Login");
        table.setText(0,0, "Login: ");
        table.setText(1,0, "Password: ");
        TextBox login = new TextBox();
        table.setWidget(0,1, login);
        PasswordTextBox password = new PasswordTextBox();
        table.setWidget(1,1, password);
        Button btnLogin = new Button("Login");
        btnLogin.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                LoginDialog.this.hide();
            }
        });
        table.setWidget(2,0, btnLogin);
    }
}
