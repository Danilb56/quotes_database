package com.example.demo.models;

import com.example.demo.Connecting;

import java.sql.*;
import java.util.ArrayList;

public class Quotes extends ArrayList<Quote>
{

    public Quotes() {
        loadQuotes();

    }
    public ArrayList<Quote> getQuotes() {
        return this;
    }

    public void loadQuotes() {
        this.clear();
        try {
            String query = "SELECT * FROM Quote;";
            Connection connection = Connecting.getDb_connect();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next())
            {
                int id = result.getInt("id");
                String teacher_name = result.getString("teacher_name");
                String creator = result.getString("creator");
                String quote_text = result.getString("text");
                String subject = result.getString("subject");
                Date quote_date = result.getDate("quote_date");
                Date added_date = result.getDate("added_date");


                this.add(
                        new Quote(
                                id,
                                teacher_name,
                                creator,
                                quote_text,
                                subject,
                                quote_date,
                                added_date
                        )
                );
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
