package com.gymmer.gymmerstation.programManagement.validations;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class InputValidation {
    private static final String INPUT_BLANK = " Is Blank!";
    private static final String INPUT_MISMATCH = " Must Be Numeric!";
    private static final String NEGATIVE_DIGIT = " Must Be No Less Than 1!";
    private static final String REST_TIME = "Rest Time Is 00:00!";

    public static void inputBlankValidation(LinkedHashMap<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry entry : map.entrySet()) {
            if(entry.getValue().toString().isBlank()) {
                sb.append(entry.getKey()).append(", ");
            }
        }
        if(!sb.toString().isBlank()) {
            sb.delete(sb.length()-2,sb.length());
            sb.append(INPUT_BLANK);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static void inputMismatchValidationNumber(String input, String field) {
        Long num;
        if(input.isBlank()) {
            input = "1";
        }
        try {
            num = Long.parseLong(input);
        } catch (Exception e) {
            throw new IllegalArgumentException(field + INPUT_MISMATCH);
        }

        if(num < 1) {
            throw new IllegalArgumentException(field + NEGATIVE_DIGIT);
        }
    }

    public static void inputMismatchValidationRestTime(String input) {
        if(input.equals("00:00")) {
            throw new IllegalArgumentException(REST_TIME);
        }
    }
}
