package Client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    //List to hold filenames in the local file path
    VBox localList = new VBox(5);
    //Holds the names of the files in the server
    VBox serverList = new VBox(5);
    //upload and download buttons
    Button upload = new Button("Upload");
    Button download = new Button("Download");
    //client object
    Client client;
    //hold the selected files for both local and server file lists
    String selectedLocal = "", selectedServer = "";
    //the path and the name of the computer
    static String path, name;
    @Override
    //function called to startup the Window
    public void start(Stage primaryStage) throws Exception{
        //create a borderpane as the root of the window
        Parent root = new BorderPane();
        ((BorderPane) root).setPadding(new Insets(7,7,7,7));
        //to hold the two lists
        HBox ac = new HBox(10);
        //set the width and sizes
        ac.setPrefSize(400, 300);
        localList.setPrefWidth(200);
        serverList.setPrefWidth(200);
        //add them to the root
        ac.getChildren().add(localList);
        ac.getChildren().add(serverList);
        ((BorderPane) root).setCenter(ac);
        HBox buttons = new HBox(10);
        //set the sizes
        upload.setPrefWidth(200);
        download.setPrefWidth(200);
        //set the action to be handled when upload button is pressed using lambda expression
        upload.setOnAction(e ->{
            try {
                //upload the file and refresh the files list
                client.uploadFile(selectedLocal);
                loadLists();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        //set the action to be handled when download button is pressed using lambda expression
        download.setOnAction(e->{
            try {
                //download the selectd file and refresh the files list
                client.downloadFile(selectedServer);
                loadLists();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        //add the buttons to the window
        buttons.getChildren().add(upload);
        buttons.getChildren().add(download);
        ((BorderPane) root).setBottom(buttons);
        //instantiate the client object
        client  = new Client(name,path);
        loadLists();

        //set the title and show the window
        primaryStage.setTitle("File Sharer v1.0");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();


    }

    //get the lists from the local shared file and the server
    public void loadLists(){
        try {
            setList(localList, true);
            setList(serverList, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //load the list into the appropriate VBox
    public void setList(VBox box, boolean local) throws IOException {
        String localFiles = "";
        //get the file list
        if(local) localFiles = client.getLocalFileList();
        else localFiles = client.getServerFileList();

        //remove all current elements in the list
        box.getChildren().clear();
        //add the label
        if(local)
            box.getChildren().add(new Label(name));
        else
            box.getChildren().add(new Label("Server"));
        //split the data into individual names
        String[] fileNames = localFiles.split("/");
        //add each into the list
        for(int i = 0; i < fileNames.length; i++ ){
            //create a new button for each name
            Button b = new Button(fileNames[i]);
            //set the action for the button
            b.setOnAction(e ->{
                //change the background color of all other buttons to white
                for(Node n: box.getChildren()){
                    (n).setStyle("-fx-background-color: white; -fx-text-fill: black");
                }
                //change this background color to make it unique
                ((Button)e.getSource()).setStyle("-fx-background-color: darkblue; -fx-text-fill: white");
                //set the selected filename
                if(local)
                    selectedLocal = ((Button)e.getSource()).getText();
                else
                    selectedServer = ((Button)e.getSource()).getText();
            });
            box.getChildren().add(b);
        }
    }

    //runs the program
    public static void main(String[] args) {
        //System.out.println(args[0]+" and "+args[1]);
        //get the arguments
        path = args[1];
        name = args[0];
        launch(args);
    }
}
