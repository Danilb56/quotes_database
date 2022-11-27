package com.example.demo.models;

import com.example.demo.Connecting;
import com.example.demo.helpers.Constants;

import java.sql.*;

public class QuoteAdapter
{
    public static Quote addQuote(Quote quote)
    {
        String insert_data =
                "INSERT INTO " +
                        Constants.QUOTE_TABLE.table_name + " ( "
                        + Constants.QUOTE_TABLE.teacher_column + ", "
                        + Constants.QUOTE_TABLE.creator_column + ", "
                        + Constants.QUOTE_TABLE.text_column + ", "
                        + Constants.QUOTE_TABLE.subject_column + ", "
                        + Constants.QUOTE_TABLE.quote_date_column + ", "
                        + Constants.QUOTE_TABLE.added_date_column + ") "
                        + "VALUES ( ?, ?, ?, ?, ?, ? )";
        System.out.println(insert_data);

        try {
            Connection connect = Connecting.getDb_connect();
            PreparedStatement prSt = connect.prepareStatement(insert_data, Statement.RETURN_GENERATED_KEYS);
            prSt.setString(1, quote.getTeacher());
            prSt.setString(2, quote.getCreator());
            prSt.setString(3, quote.getText());
            prSt.setString(4, quote.getSubject());
            prSt.setDate(5, quote.getQuoteDate());
            prSt.setDate(6, quote.getAddedDate());
            prSt.execute();
            connect.close();
            return new Quote(quote);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Quote updateQuote(Quote quote) {
        String insert_data =
                "UPDATE " +
                         Constants.QUOTE_TABLE.table_name + " SET " +
                         Constants.QUOTE_TABLE.id_column + " = ? , " +
                         Constants.QUOTE_TABLE.teacher_column + " = ? , " +
                         Constants.QUOTE_TABLE.text_column + " = ? , " +
                         Constants.QUOTE_TABLE.subject_column + " = ? , " +
                         Constants.QUOTE_TABLE.quote_date_column + " = ? , " +
                         Constants.QUOTE_TABLE.added_date_column + " = ?" +
                        " WHERE id = ?";
        System.out.println(insert_data);
        try {
            Connection connect = Connecting.getDb_connect();
            PreparedStatement prSt = connect.prepareStatement(insert_data);
            prSt.setInt(1, quote.getId());
            prSt.setString(2, quote.getTeacher());
            prSt.setString(3, quote.getText());
            prSt.setString(4, quote.getSubject());
            prSt.setDate(5, quote.getQuoteDate());
            prSt.setDate(6, quote.getAddedDate());
            prSt.setInt(7, quote.getId());
            prSt.execute();
            connect.close();
            return quote;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean deleteQuote(int quoteID)
    {
        String insert_data =
                "DELETE FROM " +
                        Constants.QUOTE_TABLE.table_name +
                        " WHERE id = ?";
        System.out.println(insert_data);
        try {
            Connection connect = Connecting.getDb_connect();
            PreparedStatement prSt = connect.prepareStatement(insert_data);
            prSt.setInt(1, quoteID);
            prSt.execute();
            connect.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
