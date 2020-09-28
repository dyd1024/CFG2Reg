package de.profile.regular;

public abstract class State {

    private final String name;

    public State(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    /*
    为什么要写hashCode
     */
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null)? 0 : name.hashCode());
        return result;
    }


    /*
    state的判等方法，需自己实现    1、需要自己实现的原因  2、this的含义  3、instanceof 的含义
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if (!(obj instanceof State)){
            return false;
        }
        State other = (State) obj;
        if(name == null){
            if (other.name != null){
                return false;
            }
        }else if(!name.equals(other.name)){
            return false;
        }
        return true;
    }
}
