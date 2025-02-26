package it.sysman.chefbook.utils;

import java.util.Random;

public class TransferTokenGenerator {

    private static final String alphabet= "ABCDEFGHIJKLMNOPQRTSUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int limit = 24;

    public static String generate(){
        Random random = new Random();
        StringBuilder token = new StringBuilder();
        for(int i=0; i<limit; i++)
            token.append(alphabet.charAt(random.nextInt(alphabet.length())));
        return token.toString();
    }
}
