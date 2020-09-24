package util;

/*
   作为存储CFG产生式的类
   成员变量:leftSide::String
           rightSide::String[]
   方法：构造函数Rule（String）
 */

public class Rule {

    public String leftSide;
    public String rightSide;

    public  Rule(String rule){
        this.leftSide = String.valueOf(rule.charAt(0));
        this.rightSide = rule.substring(2);
    }


    public String getLeftSide(){
        return leftSide;
    }

    public String getRightSide(){
        return rightSide;
    }


}