package de.profile.regular;

import de.profile.regular.Automata.FiniteAutomata;
import de.profile.regular.BigAutomata.BigState;
import de.profile.regular.util.LRItem;
import de.profile.regular.util.PhraseFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static int count = 0;
    public static void main(String[] args) throws IOException {
        // write your code here
        String filePath = args[0];
//        if(filePath != null){
//            File file = new File(filePath);
//            PhraseFile phraseFile = new PhraseFile(file);                                              //解析输入文件
//            LRItem lrItem = new LRItem(phraseFile.getGrammar());                                       //打点
//            FiniteAutomata automata = new FiniteAutomata(lrItem.getItem(),lrItem.getNon_Terminal());   //得到由打点的式子构成的自动机
//            automata.print(automata);
//
//        }

        ArrayList<String> varList = new ArrayList<>();                                   //n个
        ArrayList<String> funList = new ArrayList<>();                                   //m个
        varList.add("x");
        varList.add("y");
        varList.add("z");
        funList.add("f");
        funList.add("g");

        ArrayList<String> assume_eql = new ArrayList<>();                                //加入assume这一类的语句                        n*(n-1)个
        ArrayList<String> assume_unequal = new ArrayList<>();                            //加入assume这一类的语句                        n*(n-1)个
        ArrayList<String> assign_alphabet = new ArrayList<>();                              //加入变量之间相等的语句  排除了 x = x 这种情况     n*(n-1)个
        ArrayList<String> fun_alphabet = new ArrayList<>();                              //加入变量之间的函数关系                         n*(m*n)个

        for (String var_a : varList){
            for (String var_b : varList){
                if (!var_a.equals(var_b)){
                    assign_alphabet.add(var_a + "=" + var_b);
                    assume_eql.add("assume" + "(" + var_a + "=" + var_b + ")");
                    assume_unequal.add("assume" + "(" + var_a + "!=" + var_b +")");
                }
            }
        }

        for (String var_a : varList){
            for (String var_b : varList){
                for (String fun : funList){
                    fun_alphabet.add(var_a + "=" + fun + "("+ var_b + ")");
                }
            }
        }

        for (String s : assign_alphabet){
            System.out.println(s);
        }
        for (String s : fun_alphabet){
            System.out.println(s);
        }
        for (String s : assume_eql){
            System.out.println(s);
        }
        for (String s : assume_unequal){
            System.out.println(s);
        }

        BigState state = new BigState(varList,funList);
        state.assign_update("x=y");
    }

}