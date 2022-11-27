package com.example.demo;

import com.example.demo.models.User;
import com.example.demo.models.UserAdapter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller_change_data {
    private User user;
    private MainController main;

    public void setUser(User user, MainController main) {
        this.user = user;
        this.main = main;
    }

    @FXML
    private Button confirm_button;

    @FXML
    private TextField name_of_group;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField username_field;

    @FXML
    void initialize() {
        assert confirm_button != null : "fx:id=\"confirm_button\" was not injected: check your FXML file 'changing.fxml'.";
        assert name_of_group != null : "fx:id=\"name_of_group\" was not injected: check your FXML file 'changing.fxml'.";
        assert password_field != null : "fx:id=\"password_field\" was not injected: check your FXML file 'changing.fxml'.";
        assert username_field != null : "fx:id=\"username_field\" was not injected: check your FXML file 'changing.fxml'.";
        confirm_button.setOnAction(event -> {
                    if (!username_field.getText().isEmpty()) {
                        user = UserAdapter.updateLogin(user, username_field.getText());
                    }
                    if (!password_field.getText().isEmpty()) {
                        user = UserAdapter.updatePassword(user, password_field.getText());
                    }
                    if (!name_of_group.getText().isEmpty()) {
                        user = UserAdapter.updateGroup(user, Integer.parseInt(name_of_group.getText()));
                    }
                    main.newUser(user);
                }
        );
    }

}
