package de.profile.regular.Automata;
import de.profile.regular.State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/*
状态定义
isFinal::是否是终结状态
transitions::每个状态对应的转移  可能有多个
list::打点的自动机子集构造时需要保存之前的状态内容
 */

public class FiniteState extends State{

    public static final FiniteState STATE_NULL = new FiniteState("null");
    private boolean isFinal = false;        //是否是终结状态
    private boolean isReduce = false;       //是否是可规约状态
    private List<FiniteTransition> transitions;
    private ArrayList<String> list = new ArrayList<>();

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public FiniteState(String name) {
        super(name);
        this.transitions = new LinkedList<FiniteTransition>();
    }

    public boolean isReduce() {
        return isReduce;
    }

    public void setReduce() {
        isReduce = true;
    }

    public void setFinal(){
        this.isFinal = true;
    }

    public boolean isFinal(){
        return this.isFinal;
    }

    public void addDotState(String s){
        if (!s.equals("")){
            list.add(s);
        }
    }

    /*
    待商榷修改    TODO
     */
    public boolean contain(String s){
        boolean b = false;
        if(transitions.contains(s)){
            b = true;
        }
        return b;
    }

    /*
    添加转移，看来这个自动机是以列表方式实现
     */
    public void addTransition(FiniteState state,char... cs){                                                            //可变参数，允许定义多个与实参相匹配的形参
        for (char c : cs){
            FiniteTransition transition = new FiniteTransition(state, c);
            if (transitions.contains(transition)){
                continue;
            }
            transitions.add(transition);
        }
    }
    /*
    遍历自动机用到的，输入一个字符，得到下一个状态
     */
    public List<FiniteState> getState(char c){
        List<FiniteState> states = new LinkedList<FiniteState>();
        for (FiniteTransition transition:transitions){
            if (transition.getChar() == c){
                states.add(transition.getState());
            }
        }
        if (states.isEmpty()){
            states.add(STATE_NULL);
        }
        return states;
    }

    @Override
    public int hashCode(){
        final int prime =31;
        int result = 1;
        result = prime * result + (isFinal ? 1231 : 1237);
        result = prime * result + (super.hashCode());
        result = prime * result + ((transitions == null) ? 0 :transitions.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FiniteState)) {
            return false;
        }
        FiniteState other = (FiniteState) obj;
        if (isFinal != other.isFinal) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        if (transitions == null) {
            if (other.transitions != null) {
                return false;
            }
        } else if (!transitions.equals(other.transitions)) {
            return false;
        }
        return true;
    }


}
