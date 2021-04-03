package sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileLoader {


//    private final String filename;
//
//
//    public FileLoader (String filename){
//        this.filename = filename;
//    }
//
//    public Map<String, Integer> loadFile(){
//
//        Map<String, Integer> warnings = new HashMap<>();
//
//        int numOfFlashFlood = 0;
//        int numOfSevereThunderStorm = 0;
//        int numOfSpecialMarine = 0;
//        int numOfTornado = 0;
//
//        String currLine = "";
//
//
//        try{
//            BufferedReader br = new BufferedReader(new FileReader(this.filename));
//            while((currLine = br.readLine()) != null){
//                String[] columns = currLine.split(",");
//                for (String column: columns){
//                    if (column.contains("FLASH FLOOD")){
//                        numOfFlashFlood += 1;
//                    }else if (column.contains("SEVERE THUNDERSTORM")){
//                        numOfSevereThunderStorm += 1;
//                    }else if (column.contains("SPECIAL MARINE")){
//                        numOfSpecialMarine +=1 ;
//                    }else if (column.contains("TORNADO")){
//                        numOfTornado += 1;
//                    }
//                }
//            }
//
//
//        } catch (FileNotFoundException e){
//            e.printStackTrace();
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//
//
//
//        warnings.put("FLASH FLOOD", numOfFlashFlood);
//        warnings.put("SEVERE THUNDERSTORM", numOfSevereThunderStorm);
//        warnings.put("SPECIAL MARINE", numOfSpecialMarine);
//        warnings.put("TORNADO", numOfTornado);
//
//
//        return warnings;
//    }
}
