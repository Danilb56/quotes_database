package com.example.demo.models;

import com.example.demo.Connecting;
import com.example.demo.helpers.Constants;
import com.example.demo.helpers.PasswordHelper;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

public class UserAdapter {
    public static User createUser(String login,
                                  String password,
                                  int level
    ) {
        String insert_data =
                "INSERT INTO " +
                        Constants.USER_TABLE.table_name + " ( "
                        + Constants.USER_TABLE.login_columm + ", "
                        + Constants.USER_TABLE.password_columm + ", "
                        + Constants.USER_TABLE.level_columm + ") "
                        + "VALUES ( ?, ?, ?)";
        System.out.println(insert_data);

        try {
            Connection connect = Connecting.getDb_connect();
            PreparedStatement prSt = connect.prepareStatement(insert_data, Statement.RETURN_GENERATED_KEYS);
            prSt.setString(1, login);
            prSt.setString(2, password);
            prSt.setInt(3, level);
            prSt.execute();
            ResultSet resultSet = prSt.getGeneratedKeys();
            Integer generatedKey = -1;
            if (resultSet.next()) {
                generatedKey = resultSet.getInt(1);
            }
            connect.close();
            return new User(
                    generatedKey,
                    login,
                    password,
                    level
            );
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static User updateLogin(User oldUser, String login) {
        String insert_data =
                "UPDATE " +
                        Constants.USER_TABLE.table_name + " SET "
                        + Constants.USER_TABLE.login_columm + " = ? " +
                        " WHERE id = ?";
        System.out.println(insert_data);
        try {
            Connection connect = Connecting.getDb_connect();
            PreparedStatement prSt = connect.prepareStatement(insert_data);
            prSt.setString(1, login);
            prSt.setInt(2, oldUser.getId());
            prSt.execute();
            connect.close();
            return new User(oldUser, login);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User updatePassword(User oldUser, String password) {
        String insert_data =
                "UPDATE " +
                        Constants.USER_TABLE.table_name + " SET "
                        + Constants.USER_TABLE.password_columm + " = ? " +
                        " WHERE id = ?";

        String passwordHash = null;
        try {
            passwordHash = PasswordHelper.generateStorngPasswordHash(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
        try {
            Connection connect = Connecting.getDb_connect();
            PreparedStatement prSt = connect.prepareStatement(insert_data);
            prSt.setString(1, passwordHash);
            prSt.setInt(2, oldUser.getId());
            prSt.execute();
            connect.close();
            return new User(
                    oldUser,
                    passwordHash,
                    true
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static User updateGroup(User oldUser, Integer group) {
        String insert_data =
                "UPDATE " +
                        Constants.USER_TABLE.table_name + " SET "
                        + Constants.USER_TABLE.level_columm + " = ? " +
                        " WHERE id = ?";
        System.out.println(insert_data);
        try {
            Connection connect = Connecting.getDb_connect();
            PreparedStatement prSt = connect.prepareStatement(insert_data);
            prSt.setInt(1, group);
            prSt.setInt(2, oldUser.getId());
            prSt.execute();
            connect.close();
            return new User(oldUser, group);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
