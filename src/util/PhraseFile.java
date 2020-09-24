package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class PhraseFile {

    public Rule[] grammer;

    public  PhraseFile(File file){

        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                if (((char) tempchar) != '\r') {
                    System.out.print((char) tempchar);
                }
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}