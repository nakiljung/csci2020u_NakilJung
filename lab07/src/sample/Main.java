package sample;

import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    private Canvas canvas;

    private int numOfFlashFlood = 0;
    private int numOfSevereThunderStorm = 0;
    private int numOfSpecialMarine = 0;
    private int numOfTornado = 0;



    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON
    };

    @Override
    public void start(Stage primaryStage) throws Exception{




        Group root = new Group();
        Scene scene = new Scene(root, 700,400);
        canvas = new Canvas();
        canvas.setHeight(600);
        canvas.setWidth(800);


        File filename = new File("weatherwarnings-2015.csv");
        String currLine = "";


        try{
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while((currLine = br.readLine()) != null){
                String[] columns = currLine.split(",");
                for (String column: columns){
                    if (column.contains("FLASH FLOOD")){
                        numOfFlashFlood += 1;
                    }else if (column.contains("SEVERE THUNDERSTORM")){
                        numOfSevereThunderStorm += 1;
                    }else if (column.contains("SPECIAL MARINE")){
                        numOfSpecialMarine +=1 ;
                    }else if (column.contains("TORNADO")){
                        numOfTornado += 1;
                    }
                }
            }


        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }



        int totalWarnings = numOfFlashFlood + numOfSevereThunderStorm + numOfSpecialMarine + numOfTornado;
        int warnings[] = {numOfFlashFlood,numOfSevereThunderStorm, numOfSpecialMarine, numOfTornado};



        root.getChildren().add(canvas);
        primaryStage.setTitle("Lab07");
        primaryStage.setScene(scene);
        primaryStage.show();

        drawPieChart(warnings,totalWarnings);
    }


    public void drawPieChart(int warnings[], int numOfWarnings){

        GraphicsContext gc = canvas.getGraphicsContext2D();

        double startAngle = 0.0;
        for (int i = 0; i < warnings.length; i++){
            double slicePercentage = (double) warnings[i]/ (double) numOfWarnings;
            double sweepAngle = slicePercentage * 360.0;

            gc.setFill(pieColours[i]);
            gc.fillArc(350,50,300,300,startAngle,sweepAngle, ArcType.ROUND);

            startAngle += sweepAngle;
        }


        String types;
        Font font = new Font("Times New Roman", 12);
        gc.setFont(font);



        String warningTypes[] = {"FLASH FLOOD",  "SEVERE THUNDERSTORM" , "SPECIAL MARINE", "TORNADO"};

        for(int i  = 0; i < warnings.length; i++){
            gc.setFill(Color.BLACK);
            gc.strokeRect(80,75+(i*50),30,30);
            gc.setFill(pieColours[i]);
            gc.fillRect(80,75+(i*50),30,30);
            types = warningTypes[i];
            gc.setFill(Color.BLACK);
            gc.fillText(types,120,100+(i*50));
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}