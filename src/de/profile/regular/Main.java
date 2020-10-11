package de.profile.regular;

import de.profile.regular.Automata.FiniteAutomata;
import de.profile.regular.util.LRItem;
import de.profile.regular.util.PhraseFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static int count = 0;
    public static void main(String[] args) throws IOException {
        // write your code here
        String filePath = args[0];
        if(filePath != null){
            File file = new File(filePath);
            PhraseFile phraseFile = new PhraseFile(file);                                              //解析输入文件
            LRItem lrItem = new LRItem(phraseFile.getGrammar());                                       //打点
            FiniteAutomata automata = new FiniteAutomata(lrItem.getItem(),lrItem.getNon_Terminal());   //得到由打点的式子构成的自动机
            automata.print(automata);

        }

    }

}