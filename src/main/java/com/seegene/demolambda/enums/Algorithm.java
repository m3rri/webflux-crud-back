package com.seegene.demolambda.enums;

public enum Algorithm {
    ALGORITHM_1("a1", 10),
    ALGORITHM_2("a2", 15),
    ALGORITHM_3("a3", 21);

    public String code;
    public int value;

    Algorithm(String code, int value){
        this.code = code;
        this.value = value;
    }

    public static Algorithm fromCode(String code){
        for(Algorithm a : Algorithm.values()){
            if(a.code.equals(code)){
                return a;
            }
        }
        return null;
    }
}
