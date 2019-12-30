package com.example.calculator;

import android.widget.TextView;


public class Calculator {

    //Screen
    private final String DEFAULT_VALUE = "0.0";
    private final String ZERO_DIVISION_SCREEN = "Cannot divide by zero!";
    private String screenText = DEFAULT_VALUE;
    private TextView screenView = null;

    //Actual numbers
    private String num1 = "";
    private String num2 = "";

    //Number length limit
    private final int LENGTH = 14;

    //Flag whether second number is present
    private boolean iSsecondNumberPresent = false;

    //Flag weather coma is present
    private boolean isDotPresent = false;

    //current operation
    private String operation = "";

    //result
    private Double result = 0.0;

    /**
     * @param screenView - reference to the screen view
     */
    public Calculator(TextView screenView){
        this.screenView = screenView;
    }

    private void clear(){
        setAllToDefault();
    }

    /**
     * @param code - value for which calculator decides what operation is about to perform
     */
    public void decrypt(String code)
    {
        try{
            int num = Integer.valueOf(code);
            putNumber(num);
        }
        catch(Exception ex)
        { //if we are here it means that code is probably an operator value, not a number
            switch(code){
                case "+": saveOperation(code); break;
                case "-": saveOperation(code); break;
                case "/": saveOperation(code); break;
                case "x": saveOperation(code); break;
                case "C": clear(); break;
                case "CE": cancel(); break;
                case "BCK": back(); break;
                case "=": calculateResult(); break;
                case ".": addDot(); break;
                default: //do nothing;
            }
        }
        if(!code.equals("=") && !code.equals("C")) updateScreenText();
        refresh();
    }

    /**
     * puts a digit in its proper place
     * @param x - number to be put in a representation sequence
     */
    private void putNumber(int x){

        if(iSsecondNumberPresent) {
            if(num2.length()<LENGTH){
                if(!(num2.length()==1 && num2.charAt(0)=='0'))
                    num2 += x;
            }
        }
        else if(num1.length()<LENGTH){
            if(!(num1.length()==1 && num1.charAt(0)=='0'))
                num1 += x;
        }
    }

    /**
     * removes last digit from current number representation
     */
    private void back()
    {
        try {
            if (iSsecondNumberPresent) {
                if(num2.equals("")) removeSecondNumber();
                else if(num2.substring(num2.length()-1).equals(".")) isDotPresent = false;
                num2 = num2.substring(0, num2.length() -1);
            } else {
                if(num1.equals("")) return;
                if(num1.substring(num1.length()-1).equals(".")) isDotPresent = false;
                num1 = num1.substring(0, num1.length() -1);
            }
        } catch(Exception ex)
        {
            //no use
        }
    }

    private void cancel()
    {
        if(iSsecondNumberPresent) num2 = "";
        else num1 = "";
        isDotPresent = false;
    }

    private void removeSecondNumber()
    {
        operation = "";
        iSsecondNumberPresent = false;
        if(num1.contains(".")) isDotPresent = true;
    }

    private void saveOperation(String operation)
    {
        if(this.operation.equals("")) {
            if(num1.equals("")) num1 = DEFAULT_VALUE;
            isDotPresent = false;
            iSsecondNumberPresent = true;
            this.operation = operation;
        }
    }

    private void calculateResult()
    {
        if(validate()<0) return;
        try {
            double d1 = Double.valueOf(num1);
            double d2 = Double.valueOf(num2);
            switch(operation){
                case "+": result = d1 + d2; break;
                case "-": result = d1 - d2; break;
                case "x": result = d1 * d2; break;
                case "/": result = d1 / d2; break;
                default: return; //undefined operation
            }
        }
        catch(Exception ex)
        {
            // error of calculations
        }
        String temp = result.toString();
        setAllToDefault();
        saveResult(temp);
        screenText = temp;
    }

    /** validate input for calculation and make proper actions
     * returning minus value means error, positive means ok*/
    private int validate(){
        if(operation.equals("/") && checkIfZero(num2)){
            setAllToDefault();
            screenText = ZERO_DIVISION_SCREEN;
            return -1;
        }
        if(num1.equals("") || num2.equals("")) return -2;
        return 0;
    }

    /** saving result for future usage if not 0*/
    private void saveResult(String result)
    {
        if(!result.equals("0.0")) {
            num1 = result;
            if (num1.contains(".")) isDotPresent = true;
        }
    }

    private void addDot()
    {
        if(!isDotPresent)
        {
            if(iSsecondNumberPresent && !num2.equals("")){
                num2 += ".";
                isDotPresent = true;
            } else if(!iSsecondNumberPresent && operation.equals("")){
                num1 += ".";
                isDotPresent = true;
            }
        }
    }

    private void refresh()
    {
        screenView.setText(screenText);
    }

    private void setAllToDefault()
    {
        num1 = "";
        num2 = "";
        operation = "";
        isDotPresent = false;
        iSsecondNumberPresent = false;
        screenText = DEFAULT_VALUE;
        result = 0.0;
    }

    private void updateScreenText()
    {
        String text = num1.equals("")? DEFAULT_VALUE : num1;
        if(!operation.equals("")) text += ("\n" + operation);
        text += num2;
        screenText = text;
    }

    private static boolean checkIfZero(String number)
    {
        for(int i=0; i<number.length(); i++)
        {
            if(number.charAt(i)!='0' && number.charAt(i)!='.' ) return false;
        }
        return true;
    }
}
