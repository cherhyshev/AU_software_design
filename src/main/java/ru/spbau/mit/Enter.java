package ru.spbau.mit;

public class Enter {
    public static void main(String[] args) {
        try {
            Shell.run(args);
        }catch (Exception e){
            main(args);
        }
    }
}
