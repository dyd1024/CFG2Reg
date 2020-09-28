package de.profile.regular.util;

import java.io.*;
import java.util.ArrayList;

/*
解析文件得到上下文无关文法的条目
grammar::解析文件得到的产生式
 */

public class PhraseFile {

    private ArrayList<String> grammar = new ArrayList<>();

    public ArrayList<String> getGrammar() {
        return grammar;
    }

    public  PhraseFile(File file){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String tempString = null;                                                                               // 一次读入一行，直到读入null为文件结束

            while ((tempString = reader.readLine()) != null) {                                                     //TODO没有判断文件输入是否合法，之后再补

                grammar.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
