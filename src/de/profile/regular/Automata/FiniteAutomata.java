package de.profile.regular.Automata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class FiniteAutomata {
    private FiniteState initial;
    private HashSet<String> non_Terminal;
    private ArrayList<String> item;
    private static int count = 0;

    public FiniteAutomata(FiniteState initial) {
        this.initial = initial;
    }

    public FiniteState getInitial() {
        return initial;
    }

    /*
    把一个自动机转成正则表达式   TODO
     */

    /*
    通过所有的打点的表达式得到自动机的方法
     */
    public FiniteAutomata(ArrayList<String> item, HashSet<String> non_Terminal){
        this.item = item;
        this.non_Terminal = non_Terminal;
        construction();
    }


    public void construction(){
        if (item.contains("S'->.S")){

            FiniteState state = new FiniteState("q"+ count);
            count++;
            state.addDotState("S'->.S");
//            addTransition(state);
            this.initial = state;
            extendState(state);
        }
    }




    public void extendState(FiniteState state){
        /*
        首先对该状态存储的规则 进行扩展   最开始时只有一个条目
         */
        String rule = state.getList().get(0);
        if (isLast(rule)){              //判断该条目是否是接受状态  或 可规约   是则终止
            if (rule.equals("S'->S.")){
                state.setFinal();
            }
            state.setReduce();
            return;
        }
        state.setList(extendRule(rule));     //扩展该条目
        ArrayList<String> this_list = state.getList();
        for (String temp : this_list){                       //对该状态添加转移
            if (isLast(temp)){                               //若该状态中有可规约串，则该状态是可规约状态   继续遍历
                state.setReduce();
            }else {
                char c = getTran(temp).charAt(0);            //不是可规约状态，则添加转移
                String shift = shift(temp);
                System.out.println(shift);
                ArrayList<String> next_list = extendRule(shift);
                if (isArrayEqual(this_list,next_list)){
                    state.addTransition(state,c);
                }else {
                    FiniteState next_state = new FiniteState("q"+count);
                    count++;
                    next_state.addDotState(shift);
                    state.addTransition(next_state , c);
                    extendState(next_state);
                }

            }
        }

    }


    /*
    每添加一个打点的转移式都要判断一下    是否可扩展
     */
    public ArrayList<String> extendRule(String rule){

        ArrayList<String>  list = new ArrayList<>();
        list.add(rule);
        if (isLast(rule)){
            return list;
        }
        String s0 = getTran(rule);
        if (non_Terminal.contains(s0)){
            for (String s : item){
                String temp = s.substring(0,1);          //每个表达式看它的最左侧的终结符
                if (temp.equals(s0)){
                    String dot = s.substring(3,4);
                    if (dot.equals(".")){
                        list.add(s);
                    }
                }
            }
        }
        return list;
    }




//    public void addTransition(FiniteState state){
//        String rule = state.getList().get(0);
//        System.out.println(rule);
//        if (isLast(rule)){
//            state.setReduce();
//            if (rule.equals("S'->S.")){
//                state.setFinal();
//            }
//            return;
//        }
//        int index = rule.indexOf(".");
//        String temp = rule.substring(index+1,index+2);
//        if (non_Terminal.contains(temp)){
//            String shift_s = shift(rule);
//            if (! list.contains(shift_s)){
//                FiniteState tr_state = new FiniteState("q"+count);
//                count++;
//
//                tr_state.addDotState(shift_s);
//                state.addTransition(tr_state,temp.toCharArray());
//                addTransition(tr_state);
//            }else {
//                int num = list.indexOf(shift_s);
//
//            }
//
//
//            for (String s : item){
//                int temp_index = s.indexOf(".");
//                if (s.substring(0,1).equals(temp) && temp_index == 3){
//                    FiniteState add_state = new FiniteState("q"+count);
//                    count++;
//                    add_state.addDotState(s);
//                    state.addTransition(add_state,'%');
//                    addTransition(add_state);
//
//                }
//            }
//        }else {
//            FiniteState add_state = new FiniteState("q"+count);
//            count++;
//            String shift_s = shift(rule);
//            add_state.addDotState(shift_s);
//            state.addTransition(add_state,temp.toCharArray());
//            addTransition(add_state);
//        }
//    }



    /*
    判断一个打点的表达式   点是否在末尾
     */
    public boolean isLast(String temp){
        boolean bool = false;
        int index = temp.indexOf(".");
        if (index+1 == temp.length()){
            bool = true;
        }
        return bool;
    }

/*
将点往后移一位
 */
    public String shift(String s){
        int index = s.indexOf(".");
        String temp = s.substring(0,index) + s.substring(index+1);
        return temp.substring(0,index+1) + "." + temp.substring(index+1);
    }

    /*
    判断两个ArrayList<String>中的条目是否一致
     */
    public boolean isArrayEqual(ArrayList<String> ary1 , ArrayList<String> ary2){
        HashSet<String> temp1 = new HashSet<>();
        HashSet<String> temp2 = new HashSet<>();
        for (String s : ary1){
            temp1.add(s);
        }
        for (String s : ary2){
            temp2.add(s);
        }
        return temp1.equals(temp2);

//        Collections.sort(ary1);       //运行时修改  线程不安全报错
//        Collections.sort(ary2);
//        return ary1.equals(ary2);
    }

    public String getTran(String s){
        int index = s.indexOf(".");
        String temp = s.substring(index+1, index+2);
        return temp;
    }


    public void print(){
        
    }
}
