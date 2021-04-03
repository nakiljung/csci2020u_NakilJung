package sample;


import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;

public class Controller {

    @FXML
    private Canvas canvas;

    @FXML
    private void initialize(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        ArrayList<Float> google = downloadStockPrices("GOOG");
        ArrayList<Float> apple = downloadStockPrices("AAPL");

        drawLinePlot(gc, google, apple);

    }


    public ArrayList<Float> downloadStockPrices(String ticker){
        ArrayList<Float> stocks = new ArrayList<>();

        try {
            String sURL = "https://query1.finance.yahoo.com/v7/finance/download/" + ticker + "?period1=" +
                    "1262322000" + "&period2=" + "1451538000" + "&interval=1mo&events=history&" +
                    "includeAdjustedClose=true";
            URL netURL = new URL(sURL);

            URLConnection conn = netURL.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(netURL.openStream()));

            String line = in.readLine();

            while((line =in.readLine()) != null) {
                String[] dataFields = line.split(",");
                stocks.add(Float.parseFloat(dataFields[4]));
            }

            in.close();

        } catch (IOException e) {
                e.printStackTrace();
        }

        return stocks;
    }

    private void drawLinePlot(GraphicsContext gc,ArrayList<Float> stock1, ArrayList<Float> stock2 ) {
        gc.setStroke(Color.GREY);
        gc.setLineWidth(3);

        float max1 = Collections.max(stock1);
        float max2 = Collections.max(stock2);
        float maxValue = max1;
        if (max2 > max1){
            maxValue = max2;
        }


        gc.strokeLine(50, 50, 50, maxValue);
        gc.strokeLine(50, maxValue, stock1.size() * 15 + 50, maxValue);


        plotLine(gc, stock1, Color.RED, maxValue);
        plotLine(gc, stock2, Color.BLUE, maxValue);
    }

    private void plotLine(GraphicsContext gc, ArrayList<Float> stock, Color colour, float maxValue){
        gc.setStroke(colour);

        int x = 50;

        for (int i = 0; i < stock.size() - 1; i++){
            gc.strokeLine(x,maxValue-50-stock.get(i)*.85,x+15, maxValue-50-stock.get(i+1)*.85);
            x+=15;
        }

    }
}
