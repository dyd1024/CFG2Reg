package de.profile.regular.BigAutomata;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BigState {
    /*
    自动机编号，保存的三元组内容，转移信息
     */

    /*
    判断两个状态相等的方法          三元组的内容相同即相等     判断两个set相等   互相add 之后size不变
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
    public boolean isEqualClass(String a,String b, Set<String> eql){
        boolean flag = false;
        for (String s : eql){
           if (s.substring(0,1).equals(a) && s.substring(2,3).equals(b)){
               flag = true;
           }
        }
        return flag;
    }

    /*
    得到一个元素的等价类
     */
    public Set<String> get_EqlClass(String a , Set<String> eql ){
        Set<String> set = new HashSet<>();
        for (String s : vars_list){
            if (isEqualClass(a,s,eql)){
                set.add(s);
            }
        }

        return set;
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
            if (isEqualClass(s.substring(0,1),left_var,eql) | isEqualClass(s.substring(2,3),left_var,eql)){         //判断等价关系需要在新的  eql 中吗
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
                    if (isEqualClass(var, var_defined , eql)) {
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
        if (isDefined){
            for (String s : this.fun) {
                if (isEqualClass(s.substring(0, 1), left_var,eql) | isEqualClass(s.substring(2, 3), left_var,eql)) {
                    fun.add(s.substring(0, 4) + "undef");
                    fun.remove(s);
                }
            }
        }else {
            for (String s : this.fun){
                if (!s.substring(0,1).equals(fun_name)){
                    if (isEqualClass(s.substring(0,1),left_var,eql) | isEqualClass(s.substring(2,3),left_var,eql)){
                        fun.add(s.substring(0,4) + "undef");
                        fun.remove(s);
                    }
                }else {
                    if (isEqualClass(s.substring(2,3),right_var,eql)){
                        for (String var : vars_list) {
                            if (isEqualClass(var, var_defined,eql)) {                             //添加函数关系
                                fun.add(s.substring(0,4) + var);
                                fun.remove(s);
                            }
                        }
                    }else {                                                                            //若函数关系之前是无定义的
                        if (s.substring(2,3).equals(left_var) | s.substring(4,5).equals(left_var)){
                            fun.add(s.substring(0,4) + "undef");
                            fun.remove(s);
                        }
                    }
                }
            }
        }


        return new BigState(eql,unequal,fun);
    }


    /*
    assume 等于  语句对三元组的更新
    */
    public BigState assume_eql_update (String assume){

        String left_var = assume.substring(7,8);
        String right_var = assume.substring(9,10);
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
        eql.add(left_var + ":" + right_var);
        eql.add(right_var + ":" + left_var);
        Set<String> left_class = get_EqlClass(left_var,eql);
        Set<String> right_class = get_EqlClass(right_var,eql);
        for (String left : left_class){
            for (String right : right_class){
                eql.add(left + ":" + right);
                eql.add(right + ":" + left);
            }
        }

        for (String fun1 : fun){
            for (String fun2 : fun){
                if (isEqualClass(fun1.substring(2,3),fun2.substring(2,3),eql) && !fun1.substring(4).equals("undef") && !fun2.substring(4).equals("undef")){
                    eql.add(fun1.substring(4) + ":" + fun2.substring(4));
                    eql.add(fun2.substring(4) + ":" + fun1.substring(4));
                }
            }
        }

        /*
        对不等价关系的更新
         */

        for (String un_eql : unequal){
            Set<String> left_eql = get_EqlClass(un_eql.substring(0,1),eql);
            Set<String> right_eql = get_EqlClass(un_eql.substring(2,3),eql);

            for (String left : left_eql){
                for (String right : right_eql){
                    unequal.add(left + ":" + right);
                    unequal.add(right + ":" + left);
                }
            }
        }


        /*
        对函数关系的更新
         */
        for (String fun_expr : fun){
            if (!fun_expr.substring(4).equals("undef")){
                String left = fun_expr.substring(4);
                String right = fun_expr.substring(2,3);
                Set<String> left_eql = get_EqlClass(left,eql);
                Set<String> right_eql = get_EqlClass(right.substring(2,3),eql);
                for (String s1 : left_eql){
                    for (String s2 : right_eql){
                        fun.add(fun_expr.substring(0,1) + s2 + ":" + s1);
                    }
                }
            }
        }


        return new BigState(eql,unequal,fun);
    }

    /*
    assume 不等于  语句对三元组的更新
    */
    public BigState assume_unequal_update (String assume){

        String left_var = assume.substring(7,8);
        String right_var = assume.substring(10,11);

        Set<String> unequal = new HashSet<>();


        for (String s : this.unequal){
            unequal.add(s);
        }


        /*
        对等价关系的更新
         */

        /*
        对不等价关系的更新
         */
        Set<String> left_class = get_EqlClass(left_var,eql);
        Set<String> right_class = get_EqlClass(right_var,eql);
        for (String left : left_class){
            for (String right : right_class){
                unequal.add(left + ":" + right);
                unequal.add(right + ":" + left);
            }
        }

        /*
        对函数关系的更新
         */

        return new BigState(this.eql,unequal,this.fun);
    }


   /*
   判断两个集合是否相等
    */
    public boolean is_Set_Eql(Set<String> a, Set<String> b){
        boolean flag = false;
        int a_size = a.size();
        int b_size = b.size();
        if (a_size == b_size){
            for (String s : a){
                b.add(s);
            }
            flag = (b.size() == b_size);
        }
        return  flag;
    }


    /*
    判断两个状态是否相等
     */

    public boolean equals(BigState first,BigState second){
        boolean flag = false;

        if (is_Set_Eql(first.eql,second.eql) && is_Set_Eql(first.unequal,second.unequal) && is_Set_Eql(first.fun,second.fun)){
            flag = true;
        }

        return flag;
    }

}
