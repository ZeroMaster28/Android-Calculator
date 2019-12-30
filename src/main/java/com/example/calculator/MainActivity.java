package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Calculator calculator;

    private Button num_0;
    private Button num_1;
    private Button num_2;
    private Button num_3;
    private Button num_4;
    private Button num_5;
    private Button num_6;
    private Button num_7;
    private Button num_8;
    private Button num_9;

    private Button key_plus;
    private Button key_minus;
    private Button key_div;
    private Button key_times;

    private Button key_c;
    private Button key_ce;
    private Button key_dot;
    private Button key_eq;
    private Button key_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing buttons

        num_0 = (Button) findViewById(R.id.num0);
        num_1 = (Button) findViewById(R.id.num1);
        num_2 = (Button) findViewById(R.id.num2);
        num_3 = (Button) findViewById(R.id.num3);
        num_4 = (Button) findViewById(R.id.num4);
        num_5 = (Button) findViewById(R.id.num5);
        num_6 = (Button) findViewById(R.id.num6);
        num_7 = (Button) findViewById(R.id.num7);
        num_8 = (Button) findViewById(R.id.num8);
        num_9 = (Button) findViewById(R.id.num9);

        key_plus = (Button) findViewById(R.id.key_plus);
        key_minus = (Button) findViewById(R.id.key_sub);
        key_times = (Button) findViewById(R.id.key_times);
        key_div = (Button) findViewById(R.id.key_div);

        key_c = (Button) findViewById(R.id.key_c);
        key_ce = (Button) findViewById(R.id.key_ce);
        key_dot = (Button) findViewById(R.id.key_dot);
        key_eq = (Button) findViewById(R.id.key_eq);
        key_back = (Button) findViewById(R.id.key_back);

        TextView screenTextView = (TextView) findViewById(R.id.textViewScreen);
        calculator = new Calculator(screenTextView);

        //setting onClick listeners
        setListeners();
    }

    protected void setListeners()
    {
        setListener(num_0);
        setListener(num_1);
        setListener(num_2);
        setListener(num_3);
        setListener(num_4);
        setListener(num_5);
        setListener(num_6);
        setListener(num_7);
        setListener(num_8);
        setListener(num_9);

        setListener(key_plus);
        setListener(key_minus);
        setListener(key_times);
        setListener(key_div);

        setListener(key_c);
        setListener(key_ce);
        setListener(key_dot);
        setListener(key_eq);
        setListener(key_back);
    }

    protected void setListener(Button button)
    {
        ButtonListener listener = new ButtonListener();
        listener.assignListener(button);
    }

    public class ButtonListener implements View.OnClickListener{
        String buttonText;

        @Override
        public void onClick(View v){
            if(buttonText != null)
                calculator.decrypt(buttonText);
        }

        public void assignListener(Button button){
            buttonText = button.getText().toString();
            button.setOnClickListener(this);
        }
    }


}