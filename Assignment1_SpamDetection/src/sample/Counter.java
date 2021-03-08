package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Counter {
    private Map<String, Integer> wordCountMap;
    private Map<String, Integer> fileWordsMap;
    private int fileCount;
    String wordPattern = "^[a-zA-Z]+$";

    public Counter() {
        wordCountMap = new TreeMap<>();
    }

    public int getFileCount() {
        return this.fileCount;
    }

    public static void main(String[] args) throws IOException{

        Counter hamTrain = new Counter();
        Counter spamTrain=new Counter();


        File hamOut=new File("hamOut");
        File spamOut=new File("spamOuts");
        File hamDir=new File("data/train/ham");
        File spamDir=new File("data/train/spam");
        try {
            hamTrain.readFile(hamDir);
            spamTrain.readFile(spamDir);
            hamTrain.saveCount( hamOut);
            spamTrain.saveCount(spamOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,Float> spamProb=new TreeMap<>();
        Set<String> spamKeys = spamTrain.wordCountMap.keySet();
        Iterator<String> spamKeyIterator = spamKeys.iterator();
        //calculating word spam
        while (spamKeyIterator.hasNext()) {
            String key = spamKeyIterator.next();
            int occur = spamTrain.wordCountMap.get(key);
            if(hamTrain.wordCountMap.containsKey(key)){
                float probAppearSpam=((float)occur/spamTrain.getFileCount());
                float probAppearHam=((float)hamTrain.wordCountMap.get(key)/hamTrain.getFileCount());
                float probSpamWord=probAppearSpam/(probAppearHam+probAppearSpam);
                System.out.println(probSpamWord);
                spamProb.put(key,probSpamWord);
                System.out.println(key);
            }
        }

        File testDir=new File("data/test/spam");
        File[] contents = testDir.listFiles();
        for (File current: contents) {
            ArrayList<String> wordsInFile = new ArrayList<String>();
            int spamChance=0;
            int totalSize=0;
            Scanner scanner = new Scanner(current);
            scanner.useDelimiter("'|\\s|\"|,|:|!|\\?|\\.|-|\'");
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                if (spamProb.containsKey(word)) {
                    wordsInFile.add(word);
                    totalSize++;
                }
            }

            System.out.println(current);
            System.out.println(totalSize);

        }
    }

    public void readFile(File file) throws IOException {
        if (file.isDirectory()) {
            System.out.println("Reading directory " + file.getAbsolutePath() + "...");
            // process all the files in that directory
            File[] contents = file.listFiles();
            for (File current : contents) {
                readFile(current);
            }
        } else if (file.exists()) {
            System.out.println("Reading file" + file.getAbsolutePath() + "...");
            // count the words in this file
            fileCount++;
            Scanner scanner = new Scanner(file);
            fileWordsMap = new TreeMap<>();
            scanner.useDelimiter("'|\\s|\"|,|:|!|\\?|\\.|-|\'");
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                // if it is a word
                if (word.matches(wordPattern)) {
                    if (!fileWordsMap.containsKey(word)) {
                        fileWordsMap.put(word, 1);
                        if (wordCountMap.containsKey(word)) {
                            wordCountMap.put(word, wordCountMap.get(word) + 1);
                        } else {
                            wordCountMap.put(word, 1);
                        }
                    }
                }
            }
        }
    }

    public void saveCount(File outFile)
            throws IOException {
        System.out.println("Saving counts to " + outFile.getAbsolutePath());
        System.out.println("Total of words: " + wordCountMap.keySet().size());
        if (outFile.canWrite()) {
            PrintWriter fileOut = new PrintWriter(outFile);

            Set<String> keys = wordCountMap.keySet();
            Iterator<String> keyIterator = keys.iterator();

            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                int count = wordCountMap.get(key);

                fileOut.println(key + ": " + count);
            }

            fileOut.close();
        } else {
            System.err.println("Error:  Cannot write to file: " + outFile.getAbsolutePath());
        }

    }
}
