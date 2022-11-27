package com.example.demo;

import com.example.demo.helpers.Constants;
import com.example.demo.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthController {
    @FXML
    private Button confirm_button;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button regist_button;

    @FXML
    private TextField username_field;

    @FXML
    private Button quest_button;

    @FXML
    public void initialize()
    {

        confirm_button.setOnAction(Event ->
                {
                    String enter_username_text = username_field.getText().trim();
                    String enter_password_text = password_field.getText().trim();

                    if ((!enter_username_text.isEmpty()) &&
                            (!enter_password_text.isEmpty())
                    ) {
                        User user = enter_user(enter_username_text, enter_password_text);
                        if (user == null)
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Неверный логин или пароль", ButtonType.OK);
                            alert.showAndWait();
                        }
                        else
                        {
                            System.out.println("Button 'Confirm' is chosen");
                            confirm_button.getScene().getWindow().hide();

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
                    } else {
                        System.out.println("A field is empty");
                    }
                }
        );

        quest_button.setOnAction(Event ->
                {
                    String enter_username_text = "guest";
                    String enter_password_text = "guest";

                    System.out.println("Button 'Guest' is chosen");
                    confirm_button.getScene().getWindow().hide();

                    FXMLLoader load1 = new FXMLLoader();
                    load1.setLocation(getClass().getResource("main-screen.fxml"));

                    try {
                        load1.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MainController main = load1.getController();
                    main.initialize(enter_user(enter_username_text, enter_password_text));
                    Parent root1 = load1.getRoot();
                    Stage stage1 = new Stage();
                    stage1.setScene(new Scene(root1));
                    stage1.showAndWait();

                }
        );

        regist_button.setOnAction(Event -> {
            System.out.println("Button 'Registration' is chosen");
            regist_button.getScene().getWindow().hide();

            FXMLLoader load2 = new FXMLLoader();
            load2.setLocation(getClass().getResource("registration.fxml"));

            try {
                load2.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root2 = load2.getRoot();
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root2));
            stage2.showAndWait();

        });
    }

    private User enter_user(String enter_username_text,
                            String enter_password_text
    ) {
        try {
            System.out.println(enter_username_text + ' ' + enter_password_text);
            Connection connection = Connecting.getDb_connect();
            String query = "SELECT * FROM " + Constants.USER_TABLE.table_name +
                    " WHERE " + Constants.USER_TABLE.login_columm + " = ? AND " + Constants.USER_TABLE.password_columm + " = ?";
            System.out.println(query);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, enter_username_text);
            ps.setString(2, enter_password_text);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next())
            {
                String username = resultSet.getString(Constants.USER_TABLE.login_columm);
                String password = resultSet.getString(Constants.USER_TABLE.password_columm);
                int id = resultSet.getInt(Constants.USER_TABLE.id_column);
                Integer level = resultSet.getInt(Constants.USER_TABLE.level_columm);
                User user = new User(id, username, password, level);
                System.out.println(user);
                return user;
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
}
