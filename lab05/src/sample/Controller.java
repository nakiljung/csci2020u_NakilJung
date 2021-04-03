package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import javax.xml.crypto.Data;
import java.io.*;


public class Controller {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn studentID;
    @FXML
    private TableColumn assignment;
    @FXML
    private TableColumn midterm;
    @FXML
    private TableColumn finalExam;
    @FXML
    private TableColumn finalMark;
    @FXML
    private TableColumn letterGrade;

    private TableView<StudentRecord> getallMarks;

    private File currentFilename;


    @FXML
    private MenuItem saveOption;

    @FXML
    public void initialize(){
        studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        assignment.setCellValueFactory(new PropertyValueFactory<>("assignment"));
        midterm.setCellValueFactory(new PropertyValueFactory<>("midterm"));
        finalExam.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        finalMark.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
        letterGrade.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        tableView.setItems(DataSource.getAllMarks());
    }

    @FXML
    private void onNewClick(javafx.event.ActionEvent e) {
        tableView.getItems().clear();
    }
    @FXML
    private void onSaveClick(javafx.event.ActionEvent e) {
        save();
    }

    @FXML
    private void onOpenClick(javafx.event.ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        currentFilename = fileChooser.showOpenDialog(tableView.getScene().getWindow());

        load();
    }



    @FXML
    public void save(){
            try {

                PrintWriter file_out = new PrintWriter(currentFilename);
                ObservableList<StudentRecord> studentRecordSave = tableView.getItems();
                for (StudentRecord studentRecord : studentRecordSave){
                    file_out.println(studentRecord.getStudentID() + "," + studentRecord.getAssignment() + "," +
                            studentRecord.getMidterm() + "," + studentRecord.getFinalExam());
                }

                file_out.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

    }

    @FXML
    public void onSaveAsClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File As");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        currentFilename = fileChooser.showSaveDialog(tableView.getScene().getWindow());
        save();

    }

    @FXML
    public void load(){
        try{
            if (currentFilename.exists()){
                FileReader reader = new FileReader(currentFilename);
                BufferedReader in = new BufferedReader(reader);

                String line;

                ObservableList<StudentRecord> marks = FXCollections.observableArrayList();
                while((line = in.readLine()) != null) {
                    if (line.trim().length() != 0) {
                        String[] dataFields = line.split(",");
                        marks.add(new StudentRecord(dataFields[0], Float.valueOf(dataFields[1])
                                , Float.valueOf(dataFields[2]), Float.valueOf(dataFields[3])));
                    }
                }
                tableView.setItems(marks);


            }else{
                System.out.println("Cannot load files");
            }
        }catch (IOException e) {
           e.printStackTrace();
        }


    }

    @FXML
    private void onExitClick(javafx.event.ActionEvent e) {
        Platform.exit();
    }


}
