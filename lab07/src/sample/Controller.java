package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.util.HashMap;

public class Controller {

//
//    @FXML
//    private Canvas mainCanvas;
//    @FXML
//    public GraphicsContext gc;
//
//
//    @FXML
//    public void initialize(){
//        gc = mainCanvas.getGraphicsContext2D();
//        drawPieChart(gc);
//    }
//
//
//    public void drawPieChart(GraphicsContext gc){
//        HashMap<String, Integer> warningsData = new HashMap<>();
//        FileLoader file = new FileLoader("weatherwarnings-2015.csv");
//        warningsData = file.loadFile();
//
//        System.out.println(warningsData);
//
//        int [] data;
//
//
//
//
//        int numOfWarnings = warningsData.size();
//
//        double startAngle = 0.0;
//        for (int i = 0; i < numOfWarnings; i++){
//            double slicePercentage = (double)  / (double) numOfWarnings;
//            double sweepAngle = slicePercentage * 360.0;
//
//            gc.setFill(pieColours[i]);
//            gc.fillArc(525,0,250,250,startAngle,sweepAngle, ArcType.ROUND);
//
//            startAngle += sweepAngle;
//        }
//
//    }
}

