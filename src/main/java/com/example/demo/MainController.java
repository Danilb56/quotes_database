package com.example.demo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.demo.models.Quote;
import com.example.demo.models.Quotes;
import com.example.demo.models.User;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainController
{
    private User currentUser;
    private Quotes quotes;
    private Stage userStage = new Stage();

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    private ObservableList<Quote> quoteObservableList;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exit_button;

    @FXML
    private TableView<Quote> tableView;

    @FXML
    private Button adding_in_table;

    @FXML
    void initialize(User user)
    {
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'main-screen.fxml'.";
        setCurrentUser(user);

        quotes = new Quotes();
        setupTableView();
        adding_in_table.setOnAction(
                event -> {
                    showAddingScreen();
                }
        );

        exit_button.setOnAction(Event -> {
            exit_button.getScene().getWindow().hide();

            FXMLLoader load2 = new FXMLLoader();
            load2.setLocation(getClass().getResource("hello-view.fxml"));

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

        adding_in_table.setDisable(currentUser.getLevel() == 0);

    }

    TableColumn quoteColumn;
    TableColumn teacherColumn;
    TableColumn subjectColumn;
    TableColumn dateColumn;

    private void setupTableView()
    {
        tableView.setPlaceholder(new Label("Нет данных для отображения"));
        tableView.setItems(FXCollections.observableArrayList());
        quoteColumn = tableView.getColumns().get(0);
        teacherColumn = tableView.getColumns().get(1);
        subjectColumn = tableView.getColumns().get(2);
        dateColumn = tableView.getColumns().get(3);

        quoteColumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("QuoteDate"));
        quoteObservableList = FXCollections.observableArrayList(quotes);
        tableView.setItems(quoteObservableList);

        tableView.getSelectionModel()
                 .selectedItemProperty()
                 .addListener(
                         (ChangeListener) (observableValue, oldValue, newValue) -> {
                             showEditorScreen();
                         }
                 );
    }

    private void showAddingScreen()
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("edit-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            userStage.setScene(scene);

            EditController editQuoteController = fxmlLoader.getController();
            editQuoteController.setup(currentUser, null,  this
            );

            userStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showEditorScreen()
    {
        try {
            System.out.println("EDITING");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("edit-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
//                        Parent root = fxmlLoader.getRoot();
            userStage.setScene(scene);
            EditController editQuoteController = fxmlLoader.getController();
            editQuoteController.setup(currentUser,
                    tableView.getSelectionModel().getSelectedItem(),
                    this
            );
            userStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addQuote(Quote quote)
    {
        quoteObservableList.add(quote);
        tableView.refresh();
        userStage.close();
    }

    public void updateQuote(Quote quote)
    {
        quoteObservableList.remove(tableView.getSelectionModel().getSelectedItem());
        quoteObservableList.add(quote);
        tableView.refresh();
        userStage.close();
    }

    public void deleteQuote(Quote quote) {
        quoteObservableList.remove(quote);
        tableView.refresh();
        userStage.close();
    }
    public void newUser(User user)
    {
        currentUser = user;
        userStage.close();
    }
}
