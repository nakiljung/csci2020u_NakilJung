package sample;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class WordCounter {
    private int hamCount;
    private int spamCount;

    //map  training
    private Map<String,Integer> hamFrequency, spamFrequency;
    //map  testing
    private Map<String,Double> hamProbability, spamProbability;
    //map probabilities
    private Map<String,Double> probFileSpam, probWordSpam, probWordHam;

    // Constructor
    public WordCounter() {
        this.hamFrequency = new TreeMap<>();
        this.spamFrequency = new TreeMap<>();
        this.hamProbability = new TreeMap<>();
        this.spamProbability = new TreeMap<>();
        this.probFileSpam = new TreeMap<>();
        this.probWordSpam = new TreeMap<>();
        this.probWordHam = new TreeMap<>();
        this.hamCount = 0;
        this.spamCount = 0;
    }

    // Word pattern
    String pattern = "^[a-zA-Z]*$";

    //searches all directories, and if found a file not in directory, sends to searchTrain
    public void searchDirectory(File file)throws IOException {
        if (file.isDirectory()) {
            File[] filesInDir = file.listFiles();
            assert filesInDir != null;
            for (File value : filesInDir) {
                searchDirectory(value);
            }
        }
        else {
            if(file.getAbsolutePath().contains("train"))
                trainWithisFile(file);
            else
                testWithFile(file);
        }
    }
    //reads from train file
    public void trainWithisFile(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        // case for windows or linux
        if(file.getAbsolutePath().contains("train/ham") || file.getAbsolutePath().contains("train\\ham")) {
            hamCount++;
            //read file word by word
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                
                if(word.matches(pattern)){
                    wordCount(word, hamFrequency);
                }
            }

        }
        else if(file.getAbsolutePath().contains("train/spam") || file.getAbsolutePath().contains("train\\spam")) {
            spamCount++;
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                if(word.matches(pattern)){
                    wordCount(word, spamFrequency);
                }
            }
        }
    }

    public void testWithFile(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        double probWordSpam = 0;
        while (scanner.hasNext()) {
            String word = scanner.next();
            if (word.matches(pattern) && probFileSpam.containsKey(word)){
                double trainSpamProb = probFileSpam.get(word);
                probWordSpam+= (Math.log(1-trainSpamProb) - Math.log(trainSpamProb));
            }
        }
        //calculate and display
        double fileIsSpam = 1/(1+(Math.pow(Math.E,probWordSpam)));

        if(file.getAbsolutePath().contains("test/ham") || file.getAbsolutePath().contains("test\\ham")) {
            hamProbability.put(file.getName(),fileIsSpam);
            TestFile.saveEmail(file.getName(), fileIsSpam, "Ham");
        }
        else if(file.getAbsolutePath().contains("test/spam") || file.getAbsolutePath().contains("test\\spam")) {
            spamProbability.put(file.getName(),fileIsSpam);
            TestFile.saveEmail(file.getName(), fileIsSpam, "Spam");
        }
        System.out.println("saved final: " + file.getAbsolutePath());
        System.out.println("saved fileIsSpam: " + probWordSpam);
    }

    //increment duplicate words in map
    private void wordCount(String word, Map<String,Integer> map) {
        if (map.containsKey(word)) {
            int oldCount = map.get(word);
            map.put(word, oldCount+1);
            //else add to map
        } else {
            map.put(word, 1);
        }
    }

    public void calculateProbability(File file){
        try{
            searchDirectory(file);
        } catch (Exception e){
            e.printStackTrace();
        }
 
        Set<String> keys = hamFrequency.keySet();
        Iterator<String> keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            int count = hamFrequency.get(key);
            probWordHam.put(key,(double)count/ hamCount);
        }
        
        
        keys = spamFrequency.keySet();
        keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            int count = spamFrequency.get(key);
            probWordSpam.put(key,(double)count/ spamCount);
        }

        keys = probWordSpam.keySet();
        keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if(probWordHam.containsKey(key))
                probFileSpam.put(key,probWordSpam.get(key)/(probWordSpam.get(key) + probWordHam.get(key)));
            else
                probFileSpam.put(key,1.0);
        }
    }


    //calculate precision
    public double findPrecision(){
        double precision;
        int truePos = 0;
        int falsePos = 0;

        Set<String> keys = hamProbability.keySet();
        for (String key : keys) {
            double count = hamProbability.get(key);
            if (count >= 0.5) {
                falsePos++;
            }
        }

        truePos = getCorrect(truePos);
        precision = (double)truePos / (falsePos+truePos);
        return precision;
    }



    public double computeAccuracy(){
        //calculate accuracy
        int correct = 0;
        Set<String> keys = hamProbability.keySet();
        for (String key : keys) {
            double count = hamProbability.get(key);
            if (count < 0.5) {
                correct++;
            }
        }

        correct = getCorrect(correct);

        System.out.println("Size: " + TestFile.getEmails().size());
        System.out.println("Spam size: " + spamProbability.size());
        double accuracy = (double)correct/(hamProbability.size()+ spamProbability.size());
        System.out.println("Accuracy: " + accuracy);
        return accuracy;
    }

    private int getCorrect(int correct) {
        Set<String> keys;
        Iterator<String> keyIterator;
        keys = spamProbability.keySet();
        keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            double count = spamProbability.get(key);
            if(count >= 0.5){
                correct++;
            }
        }
        return correct;
    }
}
