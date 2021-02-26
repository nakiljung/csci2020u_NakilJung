package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {

    @FXML
    public javafx.scene.control.TextField userNameField;
    @FXML
    public javafx.scene.control.TextField passwordField;
    @FXML
    public javafx.scene.control.TextField nameField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField phoneField;
    @FXML
    public DatePicker datePicker;
    @FXML
    public Button registerButton1;

    private DateTimeFormatter dateTimeFormatter;

    @FXML
    public void initialize(){
        System.out.println("App is running...");

        final String datePattern = "M/dd/yyyy";
        dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                String finalDate = null;
                if (date != null) {
                    finalDate = dateTimeFormatter.format(date);
                }
                return finalDate;

            }

            @Override
            public LocalDate fromString(String string) {
                LocalDate date = null;
                if (string != null){
                    date = LocalDate.parse(string, dateTimeFormatter);
                }
                return date;
            }
        });

    }


    public void btnOnPress(ActionEvent event) {
        if (userNameField.getText().length() > 0){
            System.out.println(userNameField.getText());
        }
        if (passwordField.getText().length() > 0){
            System.out.println("Password is inputted but cannot be shown due to privacy reason");
        }
        if (nameField.getText().length() > 0){
            System.out.println(nameField.getText());
        }
        if (emailField.getText().length() > 0){
            System.out.println(emailField.getText());
        }
        if (phoneField.getText().length() > 0){
            System.out.println(phoneField.getText());
        }
        if (datePicker.getEditor().getText().length() > 0){
            System.out.println(datePicker.getEditor().getText());
        }


    }
}
