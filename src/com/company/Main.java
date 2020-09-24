package com.company;
import util.PhraseFile;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        String filePath = args[0];
        if(filePath != null){
            File file = new File(filePath);
            PhraseFile phraseFile = new PhraseFile(file);
        }

    }
}