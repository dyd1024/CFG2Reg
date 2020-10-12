package de.profile.regular.Automata;

import java.util.ArrayList;
import java.util.HashSet;

public class FiniteAutomata {
    private FiniteState initial;                    //起始状态，链表的开始   作为自动机的头结点
    private HashSet<String> non_Terminal;           //非终结符集合
    private ArrayList<String> item;                 //打点产生式的集合
    private static int count = 0;                   //状态计数
    private ArrayList<FiniteState>  states;         //状态集合
    private FiniteAutomata DFAutomata;              //确定化得到的DFA

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
        this.construction();
    }


    public void construction(){
        if (item.contains("S'->.S")){

            construct(item);

//            FiniteState state = new FiniteState("q"+ count);
//            count++;
//            state.addDotState("S'->.S");
//            this.initial = state;

//            addTransition(state);   递归扩展状态构建NFA，转移到一个已经扩展出来的状态，会出现不断扩展的情况  暂时不想去解决
//            extendState(state);     直接构造DFA，不稳定   S->.aSb  和 S->.a 在同一个状态集合里面 读取a之后的转移   暂时不想去解决
        }
    }



    /*

    碑 ：： 10-11-2020

    直接构造确定转移自动机的方式  不是很稳定  还是先写成非确定的  再子集构造吧

     */


//    public void extendState(FiniteState state){
//        /*
//        首先对该状态存储的规则 进行扩展   最开始时只有一个条目
//         */
//        String rule = state.getList().get(0);
//        if (isLast(rule)){              //判断该条目是否是接受状态  或 可规约   是则终止
//            if (rule.equals("S'->S.")){
//                state.setFinal();
//            }
//            state.setReduce();
//            return;
//        }
//        state.setList(extendRule(rule));     //扩展该条目
//        ArrayList<String> this_list = state.getList();
//        for (String temp : this_list){                       //对该状态添加转移
//            if (isLast(temp)){                               //若该状态中有可规约串，则该状态是可规约状态   继续遍历
//                state.setReduce();
//            }else {
//                char c = getTran(temp).charAt(0);            //不是可规约状态，则添加转移           添加转移这里   S->.aSb  和 S->.a   TODO  还是写成单个的   再子集构造吧
//                String shift = shift(temp);
//                ArrayList<String> next_list = extendRule(shift);
//                if (isArrayEqual(this_list,next_list)){
//                    state.addTransition(state,c);
//                }else {
//                    FiniteState next_state = new FiniteState("q"+count);
//                    count++;
//                    next_state.addDotState(shift);
//                    state.addTransition(next_state , c);
//                    extendState(next_state);
//                }
//            }
//        }
//    }
//
//
//    /*
//    每添加一个打点的转移式都要判断一下    是否可扩展
//     */
//    public ArrayList<String> extendRule(String rule){
//
//        ArrayList<String>  list = new ArrayList<>();
//        list.add(rule);
//        if (isLast(rule)){
//            return list;
//        }
//        String s0 = getTran(rule);
//        if (non_Terminal.contains(s0)){
//            for (String s : item){
//                String temp = s.substring(0,1);          //每个表达式看它的最左侧的终结符
//                if (temp.equals(s0)){
//                    String dot = s.substring(3,4);
//                    if (dot.equals(".")){
//                        list.add(s);
//                    }
//                }
//            }
//        }
//        return list;
//    }


    /*

    碑：： 10-11-2020

    问题在于：读取一个字符之后，若转移到已经存在的状态，没法识别，需要引入新的变量来保存     下一步：对每两个状态检查添加转移
    先写成非确定自动机的形式    在通过子集构造
     */

//    public void addTransition(FiniteState state){
//        if (state.getList().size()>1){
//            return;
//        }
//        String rule = state.getList().get(0);
//        System.out.println(rule);
//        if (isLast(rule)){                        //如果点在最后了，设置成可规约状态，若是终结状态了，同理   作为递归结束返回
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
////            if (! list.contains(shift_s)){
//                FiniteState tr_state = new FiniteState("q"+count);
//                count++;
//
//                tr_state.addDotState(shift_s);
//                state.addTransition(tr_state,temp.toCharArray());
//                addTransition(tr_state);
////            }else {
////                int num = list.indexOf(shift_s);
////
////            }
//
//            for (String s : item){                                            //点之后为非终结符的  要加的空串转移
//                int temp_index = s.indexOf(".");
//                if (s.substring(0,1).equals(temp) && temp_index == 3){
//                    FiniteState add_state = new FiniteState("q"+count);
//                    count++;
//                    add_state.addDotState(s);
//                    state.addTransition(add_state,'@');
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
    通过遍历所有状态，为每两个需要添加转移的状态之间添加转移
     */

    public void construct(ArrayList<String> item){
        ArrayList<FiniteState> stateList = new ArrayList<>();
        for (String rule : item){
            FiniteState state = new FiniteState("q" + count);
            count++;
            state.addDotState(rule);

            if (rule.equals("S'->S.")){
                state.setFinal();
            }

            if (isLast(rule)){
                state.setReduce();
            }

            if (rule.equals("S'->.S")){
                this.initial = state;
            }

            stateList.add(state);
        }
        this.states = stateList;

        for (FiniteState state : stateList){
            String rule = state.getList().get(0);
            if (isLast(rule)){
                continue;
            }
            String temp = getTran(rule);
            for (FiniteState state1 : stateList){
                String rule1 = state1.getList().get(0);
                if (shift(rule).equals(rule1)){
                    state.addTransition(state1 , temp.toCharArray());
                }

                if (non_Terminal.contains(temp) && temp.equals(getLeft(rule1))){
                    if (rule1.substring(3 , 4).equals(".")){
                        state.addTransition(state1 , '@');                            //如果两个产生式之间  rule点之后是非终结符s   s和rule1的左边的非终结符一样    rule1 的点在最前面  添加空串转移
                    }
                }

            }
        }

    }


    /*
    将得到的NFA子集构造得到DFA
     */

    public void deterministic(FiniteAutomata automata){
        DFAutomata = new FiniteAutomata(automata.initial);

    }



    /*
    获取"."后面的字符
    */

    public String getTran(String s){
        int index = s.indexOf(".");
        String temp = s.substring(index+1, index+2);
        return temp;
    }

    /*
    得到左侧的非终结符
     */
    public String getLeft(String rule){
        int index = rule.indexOf("-");
        return rule.substring(0,index);
    }

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


    /*
    打印出自动机，只打印了转移，还没加初始状态和接收状态  以及字母表   TODO
     */
    public static void print(FiniteAutomata automata){
        for(FiniteState traverse : automata.states){
            String name = traverse.getName();
            for(FiniteTransition transition : traverse.getTransitions()){
                String nextName = transition.getState().getName();
                System.out.println(name + "--" + transition.getChar() + "-->" + nextName);
            }
        }
    }


}
