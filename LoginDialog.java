package com.mySampleApplication.client.GXTClient;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

public class LoginDialog extends Dialog {
    public LoginDialog(BorderLayoutContainer container){
        VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
        TextField loginField = new TextField();
        PasswordField passwordField = new PasswordField();
        FieldLabel loginLabel = new FieldLabel(loginField, "Login");
        FieldLabel passLabel = new FieldLabel(passwordField, "Password");
        verticalLayoutContainer.add(loginLabel);
        verticalLayoutContainer.add(passLabel);
//        ContentPanel contentPanel = new ContentPanel();
//        contentPanel.add(verticalLayoutContainer);
        setWidget(verticalLayoutContainer);
        TextButton loginButton = new TextButton("Login");
        loginButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                MainEntry.appServiceAsync.checkUser(loginField.getValue(), passwordField.getValue(),
                        new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {

                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        if(result){
                            container.enable();
                            hide();
                        }
                    }
                });
            }
        });
        setPredefinedButtons();
        addButton(loginButton);
    }
}
