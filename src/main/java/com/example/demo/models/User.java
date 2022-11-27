package com.example.demo.models;

import com.example.demo.helpers.PasswordHelper;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class User
{
    private int id;
    private String login;
    private String password;
    private int level;

    public User(int id,
                String login,
                String password,
                int level
    ) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.level = level;
    }
    public User(User old, String login)
    {
        this.id = old.getId();
        this.login = login;
        this.password = old.getPassword();
        this.level = old.getLevel();
    }
    public User(User old, String password, Boolean encrypteed)
    {
        this.id = old.getId();
        this.login = old.getLogin();
        if (encrypteed) this.password = password;
        else {
            try {
                this.password = PasswordHelper.generateStorngPasswordHash(password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        this.level = old.getLevel();
    }
    public User(User old, Integer groupId) {
        this.id = old.getId();
        this.login = old.getLogin();
        this.password = old.getPassword();
        this.level = groupId;
    }
    public int getId() {
        return id;
    }

    public String getLogin() { return login; }

    public String getPassword() {
        return password;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", level=" + level +
                '}';
    }
}
