package de.profile.regular.BigAutomata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BigState {
    /*
    自动机编号，保存的三元组内容，转移信息
     */

    /*
    判断两个状态相等的方法          三元组的内容相同即相等
     */

    /*
     等价关系与不等价关系冲突的方法
     */

    /*
    修改三元组的方法   需要封装关于四种不同类型的转移语句的更改操作
     */

    private String name;
    private Set<String> eql = new HashSet<>();
    private Set<String> unequal = new HashSet<>();
    private Set<String> fun = new HashSet<>();
    private Set<String> vars_list = new HashSet<>();

    public BigState(Set<String> eql,Set<String> unequal,Set<String> fun){
        this.eql = eql;
        this.unequal = unequal;
        this.fun = fun;

        init_var_list();
    }

    public BigState(ArrayList<String> var_list, ArrayList<String> fun_list){
        for (String var : var_list){
            eql.add(var + ":" + var);
        }

        for (String fun_name : fun_list){
            for (String var : var_list){
                fun.add(fun_name + ":" + var + ":" + "undef");
            }
        }

        init_var_list();

//        打印等价关系和函数关系
        for (String  s : eql){
            System.out.println(s);
        }

        for (String s : fun){
            System.out.println(s);
        }

    }

    /*
    得到所有的变量名
     */
    public void init_var_list(){
        for (String s : eql){
            vars_list.add(s.substring(0,1));
        }
    }

    /*
    判断两个元素是否有等价关系
     */
    public boolean isEqualClass(String a,String b){
        boolean flag = false;
        for (String s : this.eql){
           if (s.substring(0,1).equals(a) && s.substring(2,3).equals(b)){
               flag = true;
           }
        }
        return flag;
    }


    /*
    普通赋值语句对三元组的更新
     */
    public BigState assign_update (String assign){
        String left_var = assign.substring(0,1);
        String right_var = assign.substring(2,3);
        Set<String> eql = new HashSet<>();
        Set<String> unequal = new HashSet<>();
        Set<String> fun = new HashSet<>();

        for (String s : this.eql){
            eql.add(s);
        }

        for (String s : this.unequal){
            unequal.add(s);
        }

        for (String s : this.fun){
            fun.add(s);
        }

        /*
         对等价关系的更新    若等价关系中有 x：y  语句为 x = y,会不会冲突
         */

        for (String s : this.eql){
            if (s.substring(0,1).equals(left_var) | s.substring(2,3).equals(left_var)){
                eql.remove(s);
            }
            if (s.substring(0,1).equals(right_var)){
                eql.add(s.substring(2,3) + ":" + left_var);
                eql.add(left_var + ":" + s.substring(2,3));
            }
        }

        eql.add(left_var + ":" + left_var);

        /*
        此处有一个输出
         */
        for (String s : eql){
            System.out.println(s);
        }

        /*
        对不等价关系的更新
         */
        for (String s : this.unequal){
            if (s.substring(0,1).equals(left_var) | s.substring(2,3).equals(left_var)){
                unequal.remove(s);
            }
        }

        /*
        对函数关系的更新
         */
        for (String s : this.fun){
            if (isEqualClass(s.substring(0,1),left_var) | isEqualClass(s.substring(2,3),left_var)){
                fun.add(s.substring(0,4) + "undef");
                fun.remove(s);
            }
        }

        return new BigState(eql,unequal,fun);

    }

    /*
    函数赋值语句对三元组的更新
    */
    public BigState fun_update (String assign_fun){

        String left_var = assign_fun.substring(0,1);
        String right_var = assign_fun.substring(4,5);
        String fun_name = assign_fun.substring(2,3);
        Set<String> eql = new HashSet<>();
        Set<String> unequal = new HashSet<>();
        Set<String> fun = new HashSet<>();

        for (String s : this.eql){
            eql.add(s);
        }

        for (String s : this.unequal){
            unequal.add(s);
        }

        for (String s : this.fun){
            fun.add(s);
        }

        boolean isDefined = false;
        String var_defined = null;
        for (String s : this.fun){
            if (s.substring(0,1).equals(fun_name) && s.substring(2,3).equals(right_var) && !s.substring(4).equals("undef")){
                isDefined = true;
                var_defined = s.substring(4);
            }
        }

        /*
        对等价关系的更新
         */

        if (isDefined){
            for (String s : this.eql){
                if (s.substring(0,1).equals(left_var) | s.substring(2,3).equals(left_var)){
                    eql.remove(s);
                }
                for (String var : vars_list) {
                    if (isEqualClass(var, var_defined)) {
                        eql.add(var + ":" + left_var);
                        eql.add(left_var + ":" + var);
                    }
                }
            }
        }else {
            for (String s : this.eql){
               if (s.substring(0,1).equals(left_var) | s.substring(2,3).equals(left_var)){
                  eql.remove(s);
               }
            }
        }
        eql.add(left_var + ":" + left_var);

        /*
        对不等价关系的更新    x = f(y)  不管f(y)之前有无定义，与 x 有关的不等价关系都被移除
         */

        for (String s : unequal){
            if (s.substring(0,1).equals(left_var) | s.substring(2,3).equals(left_var)){
                unequal.remove(s);
            }
        }


        /*
        对函数关系的更新
         */
        for (String s : this.fun){
            if (!s.substring(0,1).equals(fun_name)){
                if (isEqualClass(s.substring(0,1),left_var) | isEqualClass(s.substring(2,3),left_var)){
                    fun.add(s.substring(0,4) + "undef");
                    fun.remove(s);
                }
            }else {
                
            }

        }

        return new BigState(eql,unequal,fun);
    }


    /*
   assume 等于  语句对三元组的更新
    */
    public void assume_eql_update (String assume){

        String left_var = assume.substring(0,1);
        String right_var = assume.substring(4,5);
        Set<String> eql = new HashSet<>();
        Set<String> unequal = new HashSet<>();
        Set<String> fun = new HashSet<>();

        for (String s : this.eql){
            eql.add(s);
        }

        for (String s : this.unequal){
            unequal.add(s);
        }

        for (String s : this.fun){
            fun.add(s);
        }

        /*
        对等价关系的更新
         */

        /*
        对不等价关系的更新
         */

        /*
        对函数关系的更新
         */
    }

    /*
    assume 不等于  语句对三元组的更新
    */
    public void assume_unequal_update (String assign){

        /*
        对等价关系的更新
         */

        /*
        对不等价关系的更新
         */

        /*
        对函数关系的更新
         */
    }

}
