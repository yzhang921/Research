package com.packtpub.mahoutcookbook;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Hello world!
 * 
 */
public class App {
    
    private static final String WORK_DIR="/home/peter/Research/mahoutck/05";
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        
        CSVReader reader = new CSVReader(new FileReader(WORK_DIR + "/train/training.csv"));
        
        String[] headerold = reader.readNext();
        String[] nextLine;
        String[] previousLine;
        String[] headernew = new String[headerold.length + 1];

        CSVWriter writer = new CSVWriter(new FileWriter(WORK_DIR + "/train/final.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);

        for (int i = 0; i < headerold.length; i++) {
            headernew[i] = headerold[i];
        }

        headernew[headernew.length - 1] = "action";
        writer.writeNext(headernew);

        previousLine = reader.readNext();

        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            System.out.println(nextLine[0] + nextLine[1] + "etc...");
            headernew = new String[nextLine.length + 1];

            for (int i = 0; i < headernew.length - 1; i++) {
                headernew[i] = nextLine[i];
            }

            if (Double.parseDouble(previousLine[4]) < Double.parseDouble(nextLine[4])) {
                headernew[headernew.length-1] = "SELL";
            } else {
                headernew[headernew.length-1] = "BUY";
            }

            writer.writeNext(headernew);

            previousLine = nextLine;

        }

        reader.close();
        writer.close();

    }
}
