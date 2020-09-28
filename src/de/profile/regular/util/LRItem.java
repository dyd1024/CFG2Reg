package de.profile.regular.util;

import java.util.ArrayList;
import java.util.HashSet;


/*
inPut:ArrayList<String>，
result:ArrayList<String> item
non_Terminal::终结符集合
alphabet::字母表集合
 */

public class LRItem {
    private ArrayList<String> item = new ArrayList<>();
    private HashSet<String> non_Terminal = new HashSet<>();
    private HashSet<String> alphabet = new HashSet<>();

    public ArrayList<String> getItem(){
        return this.item;
    }

    public HashSet<String> getNon_Terminal() {
        return non_Terminal;
    }

    public HashSet<String> getAlphabet() {
        return alphabet;
    }

    public LRItem(ArrayList<String> grammar){
        phrase(grammar);
        dot(grammar);
        System.out.println(item);
    }


/*
input::ArrayList<String> grammar
result::
non_Terminal::终结符集合
alphabet::字母表集合
 */
    public void phrase(ArrayList<String> grammar){

        for(String s:grammar){
            if(s.equals("")){
                break;
            }
            non_Terminal.add(s.substring(0,1));                                                                         //保存所有的非终结符
            alphabet.add(s.substring(0,1));

            if(s.substring(3).equals("epsilon")){
                alphabet.add(String.valueOf(0));
            }else {
                char[] temp = s.toCharArray();
                for(int j=0;j<temp.length;j++){
                    if(temp[j] != '-' && temp[j] != '>'){
                        alphabet.add(String.valueOf(temp[j]));                                                          //得到字母表
                    }
                }
            }
        }

        System.out.println(non_Terminal);
        System.out.println(alphabet);
    }

/*
将所有产生式打点
input::ArrayList<String> grammar
result:ArrayList<String> item
 */

    public void dot(ArrayList<String> grammar){

        System.out.println(grammar);
        item.add("S'->.S");                                                                                              //开始符号默认为S
        item.add("S'->S.");
        for(String s:grammar){
            if(s.equals("")){
                break;
            }
            if(s.substring(3).equals("epsilon")){
                item.add(s.substring(0,3)+".");
                break;
            }

            for(int index=3; index < s.length()+1; index++){
                StringBuffer stringBuffer = new StringBuffer(s);
                item.add(stringBuffer.insert(index, '.').toString());
            }
        }

    }
}


//非终结符的集合
//从开始符号出发，得到S'->S
//子集构造怎么搞  NFA确定化