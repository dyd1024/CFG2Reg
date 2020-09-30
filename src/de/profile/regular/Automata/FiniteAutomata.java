package de.profile.regular.Automata;

import java.util.ArrayList;

public class FiniteAutomata {
    private FiniteState initial;

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
    public FiniteAutomata(ArrayList<String> item, ArrayList<String> non_Terminal){
        if (item.contains("S'->.S")){

            int num = 0;

            FiniteState state = new FiniteState("q"+ num);
            if (num == 0){
                this.initial = state;
                state.addDotState("S'->.S");
            }


            for (String s : item){

                if (non_Terminal.contains(s.substring(0))){
                    if (s.substring(2,3).equals(".")){

                    }
                }
            }
        }
    }


    public void extendState(FiniteState state,ArrayList<String> non_Terminal){
        String s = state.getList().toString();
        int index = s.indexOf(".");
        if (index+1 == s.length()){
            state.setReduce(true);
            return;
        }
        String s0 = s.substring(index+1, index+2);
        if (non_Terminal.contains(s0)){
            
        }

    }
}
