package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

//inPut:ArrayList<Rule>，outPut:ArrayList<String> item
//non_Terminal::终结符集合
//alphabet::字母表集合

public class LRItem {
    ArrayList<String> item = new ArrayList<>();
    public HashSet<String> non_Terminal = new HashSet<>();
    public HashSet<String> alphabet = new HashSet<>();

    public LRItem(ArrayList<String> grammar){
        phrase(grammar);


    }



    public void phrase(ArrayList<String> grammar){

        Iterator i = grammar.iterator();                                                                                //得到解析输入文件的结果，Iterator遍历。
        int line = 0;
        while(i.hasNext()){

            non_Terminal.add(grammar.get(line).substring(0,1));                                                         //保存所有的非终结符
            alphabet.add(grammar.get(line).substring(0,1));

            if(grammar.get(line).substring(3).equals("epsilon")){
                alphabet.add(String.valueOf(0));
            }else {
                char[] temp = grammar.get(line).toCharArray();
                for(int j=0;j<temp.length;j++){
                    if(temp[j] != '-' && temp[j] != '>'){
                        alphabet.add(String.valueOf(temp[j]));                                                          //得到字母表
                    }
                }
            }
//            System.out.println(grammar.get(line).leftSide+"->"+grammar.get(line).rightSide);
            i.next();
            line++;
        }

        System.out.println(non_Terminal);
        System.out.println(alphabet);
    }
}


//非终结符的集合
//从开始符号出发，得到S'->S
//自动机用树结构表示（节点类，边上的字母如何表示）
//打点的函数，每次调用后移一位   input：String  output：String
//子集构造怎么搞  NFA确定化