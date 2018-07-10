package com.example.qqz.elktest.gson;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * Created by tsangzacks on 14/12/29.
 */
public class CalculatorUtil {


    private static final String OPERATOR = "+-*/()";

    private static int[][] OPERATORCOMPARE = new int[][] { { 0, 0, -1, -1, -1, 1 },
            { 0, 0, -1, -1, -1, 1 }, { 1, 1, 0, 0, -1, 1 },
            { 1, 1, 0, 0, -1, 1 }, { 1, 1, 1, 1, 0, 1 },
            { -1, -1, -1, -1, -1, 0 } };

    private static final char ENDCHAR='\n';

    public static boolean isOperator(String input){
        if (input.length()==0){
            return false;
        }
        return "+-*/".indexOf(input.charAt(input.length()-1))>-1;
    }

    public static boolean hasOperator(String inputString){
        if (inputString==null){
            return false;
        }else if (inputString.indexOf("+")>-1){
            return true;
        }else if(inputString.indexOf("-")>-1){
            return true;
        }else if(inputString.indexOf("*")>-1){
            return true;
        }else if(inputString.indexOf("/")>-1){
            return true;
        }else if(inputString.indexOf("(")>-1){
            return true;
        }else if(inputString.indexOf(")")>-1){
            return true;
        }

        return false;
    }

    public static BigDecimal calc(String solution) {

        // fill complete calcaulator
        if (solution.charAt(solution.length()-1)=='+'){
            solution+="0";
        }else if (solution.charAt(solution.length()-1)=='-'){
            solution+="0";
        }else if (solution.charAt(solution.length()-1)=='*'){
            solution+="1";
        }else if (solution.charAt(solution.length()-1)=='/'){
            solution+="1";
        }

        solution+=ENDCHAR;
        Stack<BigDecimal> digital = new Stack<BigDecimal>();
        Stack<Character> opeators = new Stack<Character>();
        int begin = 0;
        int length = 0;
        char current = 0;
        for (int i = 0; i < solution.length(); i++) {
            current = solution.charAt(i);

            if (('0' <= current && current <= '9')||current=='.') {
                length++;
                continue;
            } else {

                if (length > 0) {
                    digital.add(new BigDecimal(solution.substring(begin, begin + length)));
                    begin += length;
                    length = 0;
                }
                begin++;

                if (ENDCHAR == current) {
                    while(!opeators.isEmpty()){
                        operator(digital, opeators);
                    }
                } else {

                    while (true) {
                        if (opeators.isEmpty()) {
                            opeators.push(current);
                            break;
                        } else {
                            if (OPERATORCOMPARE[OPERATOR.indexOf(current)][OPERATOR
                                    .indexOf(opeators.lastElement())] > 0) {
                                opeators.push(current);
                                break;
                            } else if (opeators.lastElement() == '('
                                    && current == ')') {
                                opeators.pop();
                                break;
                            } else if (opeators.lastElement() == '('
                                    && current != ')') {
                                opeators.push(current);
                                break;
                            } else {
                                operator(digital, opeators);
                            }
                        }
                    }
                }

            }
        }
        if (digital.isEmpty()){
            return new BigDecimal(0);
        }
        return digital.pop();
    }

    private static void operator(Stack<BigDecimal> digital,Stack<Character> opeators) {
        BigDecimal number1=digital.pop();
        BigDecimal number2=null;
        switch (opeators.pop()) {
            case '+':
                if(digital.size()==0){
                    digital.push(number1);
                }else{
                    number2=digital.pop();
                    digital.push(number2.add(number1));
                }
                break;
            case '-':
                if(digital.size()==0){
                    digital.push(number1);
                }else{
                    number2=digital.pop();
                    digital.push(number2.subtract(number1));
                }
                break;
            case '*':
                if(digital.size()==0){
                    digital.push(number1);
                }else{
                    number2=digital.pop();
                    digital.push(number2.multiply(number1));
                }
                break;
            case '/':
                if(digital.size()==0){
                    digital.push(number1);
                }else{
                    number2=digital.pop();
                    if (number1.compareTo(BigDecimal.ZERO)!=0){
                        digital.push(number2.divide(number1,4,BigDecimal.ROUND_HALF_UP));
                    }else{
                        digital.push(number2);
                    }
                }
                break;
        }
    }
}

