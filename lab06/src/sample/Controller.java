package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Controller {
    @FXML
    private Canvas mainCanvas;
    @FXML
    public GraphicsContext gc;

    // For Bar Chart
    private static double[] avgHousingPricesByYear = {
            247381.0,264171.4,287715.3,294736.1,
            308431.4,322635.9,340253.0,363153.7
    };
    private static double[] avgCommercialPricesByYear = {
            1121585.3,1219479.5,1246354.2,1295364.8,
            1335932.6,1472362.0,1583521.9,1613246.3
    };



    // For Pie Chart
    private static String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };
    private static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };
    private static Color[] pieColours = {
            Color. AQUA , Color. GOLD , Color. DARKORANGE ,
            Color. DARKSALMON , Color. LAWNGREEN , Color. PLUM
    };


    @FXML
    public void initialize(){
        gc = mainCanvas.getGraphicsContext2D();
        drawBarChart(200,300, avgHousingPricesByYear, avgCommercialPricesByYear, Color.RED, Color.BLUE);
        drawPieChart(gc);
    }

    // Setting Up Bar Chart
    public void drawBarChart(int w, int h, double[] avgHousingPricesByYear, double[] avgCommercialPricesByYear,  Color color, Color color2){



        int numRows = avgHousingPricesByYear.length;
        int numColumns = 2;
        double[][] data = new double[numRows][numColumns];
        for (int j = 0; j <numRows; j++){
            double[] element = new double[] {avgHousingPricesByYear[j], avgCommercialPricesByYear[j]};
            data[j]= element;
        }


        double maxVal = Double.NEGATIVE_INFINITY, minVal = Double.MAX_VALUE;
        for (double [] val : data) {
            for (double v: val){
            if (v > maxVal)
                maxVal = v;
            if (v < minVal)
                minVal = v;
            }
        }

        double barWidth = w / data.length;
        double x = 0;
        for (double [] val : data) {
            for (double v : val) {
                if (v < 1000000){
                    gc.setFill(color);
                }else{
                    gc.setFill(color2);
                }

                double barHeight = ((v - minVal) / (maxVal - minVal)) * h;
                gc.fillRect(x, (h - barHeight), barWidth, barHeight);
                x += barWidth + 5;
            }
        }

    }

    // Setting Up Pie Chart
    public void drawPieChart(GraphicsContext gc){
        int numOfAgeGroup = ageGroups.length;
        int totalPurchase = 0;
        for(int purchase: purchasesByAgeGroup)
            totalPurchase += purchase;


        double startAngle = 0.0;
        for (int i = 0; i < numOfAgeGroup; i++){
            double slicePercentage = (double) purchasesByAgeGroup[i] / (double) totalPurchase;
            double sweepAngle = slicePercentage * 360.0;

            gc.setFill(pieColours[i]);
            gc.fillArc(525,0,250,250,startAngle,sweepAngle, ArcType.ROUND);

            startAngle += sweepAngle;
        }

    }
}
