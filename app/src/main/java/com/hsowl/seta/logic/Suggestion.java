package com.hsowl.seta.logic;

enum Suggestion {
    Now,
    One,
    Two,
    Three,
    Four,
    Later;


    public static Suggestion getHoursByInt(int i){
        Suggestion suggestion = Suggestion.Later;
        switch(i){
            case 0:
                suggestion = Suggestion.Now;
                break;
            case 1:
                suggestion = Suggestion.One;
                break;
            case 2:
                suggestion = Suggestion.Two;
                break;
            case 3:
                suggestion = Suggestion.Three;
                break;
            case 4:
                suggestion = Suggestion.Four;
                break;
        }
        return suggestion;
    }
}


