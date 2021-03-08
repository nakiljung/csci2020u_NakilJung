package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();

        //get instance of controller
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose a Directory");
        directoryChooser.setInitialDirectory(new File("../"));

        File currDirectory = directoryChooser.showDialog(primaryStage);
        File trainDir = new File(currDirectory + "/train");
        File testDir = new File(currDirectory + "/test");

        // instance of Counter class
        WordCounter w = new WordCounter();
        w.calculateProbability(trainDir);
        w.searchDirectory(testDir);

        controller.setAccuracy(Double.toString(w.computeAccuracy()));
        controller.setPrecision(Double.toString(w.findPrecision()));

        primaryStage.setTitle("Spam Master 3000");
        primaryStage.setScene(new Scene(root, 620, 650));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
