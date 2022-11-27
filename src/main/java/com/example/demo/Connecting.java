package com.example.demo;

import java.sql.*;


public class Connecting {

    public static Connection getDb_connect() {//throws ClassNotFoundException, SQLException
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2053_1",
                    "std_2053_1", "qwer1234");

            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Quote;";
            ResultSet result = statement.executeQuery(query);
//
            while(result.next())
            {
                int id = result.getInt("id");
                String teacher_name = result.getString("teacher_name");
                String creator = result.getString("creator");
                String quote_text = result.getString("text");
                System.out.println(teacher_name + quote_text);

            }

            return connection;

        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка. Библиотека баз данных не найдена");
            e.printStackTrace();
        } catch (SQLException e) {

            System.out.println("Ошибка. Ошибка SQL. Возможно отсуствует подключение к серверу");
            e.printStackTrace();
        }
        return null;
    }

}
