package com.example.demo;

import com.example.demo.models.User;
import com.example.demo.models.UserAdapter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class RegistrationController {
    //Controller_registing regist = FXMLLoader.getController();

    @FXML
    private Button confirm_button;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField username_field;


    @FXML
    public void initialize()
    {
        Connecting setting_connect = new Connecting();
        confirm_button.setOnAction(Event -> {
                    String enter_username_text = username_field.getText();
                    String enter_password_text = password_field.getText();
                    confirm_button.getScene().getWindow().hide();
                    if ((!enter_username_text.isEmpty()) &&
                            (!enter_password_text.isEmpty()))
                    {
                        User user = UserAdapter.createUser(username_field.getText(),
                                password_field.getText(), 1
                        );
                        callMainScreen(user);
                    } else {
                        System.out.println("Не все поля заполнены");
                    }
                }
        );

    }

    private void callMainScreen(User user)
    {
        FXMLLoader load1 = new FXMLLoader();
        load1.setLocation(getClass().getResource("main-screen.fxml"));

        try {
            load1.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainController main = load1.getController();
        main.initialize(user);
        Parent root1 = load1.getRoot();
        Stage stage1 = new Stage();
        stage1.setScene(new Scene(root1));
        stage1.showAndWait();
    }
}
