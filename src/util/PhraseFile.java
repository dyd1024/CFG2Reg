package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PhraseFile {

    public ArrayList<Rule> grammar = new ArrayList<>();                                                             //解析文件完成之后，产生式存储在grammar中

    public  PhraseFile(File file){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String tempString = null;                                                                               // 一次读入一行，直到读入null为文件结束

            while ((tempString = reader.readLine()) != null) {                                                      //TODO没有判断文件输入是否合法，之后再补
                Rule rule = new Rule(tempString);
                grammar.add(rule);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Iterator i = grammar.iterator();                                                                         //得到解析输入文件的结果，Iterator遍历。
//        int line = 0;
//        while(i.hasNext()){
//            System.out.println(grammar.get(line).leftSide+"->"+grammar.get(line).rightSide);
//            i.next();
//            line++;
//        }

    }
}

//          str.indexOf(',')表示在整个字符串中检索，返回位置值