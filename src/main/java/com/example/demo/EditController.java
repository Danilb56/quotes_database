package com.example.demo;

import java.time.LocalDate;
import java.util.Calendar;

import com.example.demo.models.Quote;
import com.example.demo.models.QuoteAdapter;
import com.example.demo.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditController
{
    private User currentUser;
    private Quote currentQuote;
    private MainController main;

    public void setup(User user, Quote quote, MainController main)
    {
        addButton.setDisable(quote != null);
        if (quote == null)
        {
            quote = new Quote(0,"","","","",
                    new java.sql.Date(Calendar.getInstance().getTime().getTime()),
                    new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        }
        currentUser = user;
        currentQuote = quote;
        this.main = main;
        setupFields(quote);
        editButton.setDisable(!isUserAuthorized());
        deleteButton.setDisable(!isUserAuthorized());
    }

    @FXML
    private Button addButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TextArea quoteTextArea;

    @FXML
    private TextField subjectTextField;

    @FXML
    private TextField teacherTextField;

    @FXML
    void initialize()
    {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'edit-view.fxml'.";
        assert datePicker != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'edit-view.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'edit-view.fxml'.";
        assert editButton != null : "fx:id=\"editButton\" was not injected: check your FXML file 'edit-view.fxml'.";
        assert quoteTextArea != null : "fx:id=\"quoteTextArea\" was not injected: check your FXML file 'edit-view.fxml'.";
        assert subjectTextField != null : "fx:id=\"subjectTextField\" was not injected: check your FXML file 'edit-view.fxml'.";
        assert teacherTextField != null : "fx:id=\"teacherTextField\" was not injected: check your FXML file 'edit-view.fxml'.";

        addButton.setOnAction(
                event -> {
                    if (!quoteTextArea.getText().isEmpty() &&
                            !subjectTextField.getText().isEmpty() &&
                            !teacherTextField.getText().isEmpty()) {
                        Quote quote = new Quote(-1,
                                teacherTextField.getText(),
                                currentUser.getLogin(),
                                quoteTextArea.getText(),
                                subjectTextField.getText(),
                                java.sql.Date.valueOf(datePicker.getValue()),
                                java.sql.Date.valueOf(datePicker.getValue())
                        );

                        quote = QuoteAdapter.addQuote(quote);
                        main.addQuote(quote);
                    }
                }
        );
        editButton.setOnAction(
                event -> {
                    if (isUserAuthorized())
                    {
                        if (!quoteTextArea.getText().isEmpty() &&
                                !subjectTextField.getText().isEmpty() &&
                                !teacherTextField.getText().isEmpty()) {
                            Quote quote = new Quote(currentQuote.getId(),
                                    teacherTextField.getText(),
                                    currentUser.getLogin(),
                                    quoteTextArea.getText(),
                                    subjectTextField.getText(),
                                    java.sql.Date.valueOf(datePicker.getValue()),
                                    java.sql.Date.valueOf(datePicker.getValue())
                            );
                            quote = QuoteAdapter.updateQuote(quote);
                            main.updateQuote(quote);
                        }
                    } else {
                        System.out.println("Access denied");
                    }


                }
        );
        deleteButton.setOnAction(
                event -> {
                    if (isUserAuthorized()) {
                        QuoteAdapter.deleteQuote(currentQuote.getId());
                        main.deleteQuote(currentQuote);
                    } else {
                        System.out.println("Access denied");
                    }
                }
        );
    }

    private boolean isUserAuthorized()
    {
        if (currentQuote.getCreator().equals(currentUser.getLogin()))
        {
            return true;
        }
        return false;
    }
    private void setupFields(Quote quote)
    {
        if (quote != null)
        {
            System.out.println("NULL");
            quoteTextArea.setText(quote.getText());
            teacherTextField.setText(quote.getTeacher());
            subjectTextField.setText(quote.getSubject());
            datePicker.setValue(quote.getAddedDate().toLocalDate());
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }
        else
        {
            quoteTextArea.setText("");
            teacherTextField.setText("");
            subjectTextField.setText("");
            datePicker.setValue(LocalDate.now());
            editButton.setDisable(true);
            deleteButton.setDisable(true);
        }
    }

}
