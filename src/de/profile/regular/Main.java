package de.profile.regular;

import de.profile.regular.util.LRItem;
import de.profile.regular.util.PhraseFile;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        String filePath = args[0];
        if(filePath != null){
            File file = new File(filePath);
            PhraseFile phraseFile = new PhraseFile(file);                                       //解析输入文件
            LRItem lrItem = new LRItem(phraseFile.getGrammar());                                     //打点
        }

    }
}