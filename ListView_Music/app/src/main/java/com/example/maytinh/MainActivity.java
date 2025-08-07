package com.example.maytinh;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {
    private String operator = "";
    private String DigitInput ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

    }

    double var1,var2;

    public void C(View view){
        var1 =0; var2=0;
        TextView Main_Screen = (TextView) findViewById(R.id.screen_Main);
        TextView Sub_Screen = (TextView) findViewById(R.id.screen_Sub);
        TextView Calsymbol = (TextView) findViewById(R.id.Calsymbol);
        DigitInput = "";
        Main_Screen.setText("");
        Sub_Screen.setText("");
        Calsymbol.setText("");
    }

    public void CE(View view){
        var1 =0; var2=0;
        TextView Main_Screen = (TextView) findViewById(R.id.screen_Main);
        TextView Calsymbol = (TextView) findViewById(R.id.Calsymbol);
        DigitInput = "";
        Main_Screen.setText("");
        Calsymbol.setText("");
    }

    public void DEL(View view){
        TextView Main_Screen = (TextView) findViewById(R.id.screen_Main);
        String text = Main_Screen.getText().toString();
        if(text.length()==0){

        }
        else {
           Main_Screen.setText(text.substring(0,text.length()-1));
           DigitInput = DigitInput.substring(0,DigitInput.length()-1);
        }

    }

    public void onDigitClick(View view){
        TextView Main_Screen = (TextView) findViewById(R.id.screen_Main);
        Button button = (Button) view;
        DigitInput += button.getText().toString();
        Main_Screen.setText(DigitInput);
    }

    public void onOperatorClick(View view){
        TextView Main_Screen = (TextView) findViewById(R.id.screen_Main);
        TextView Sub_Screen = (TextView) findViewById(R.id.screen_Sub);
        TextView Calsymbol = (TextView) findViewById(R.id.Calsymbol);
        Button button = (Button) view;
        operator = button.getText().toString();
        DigitInput ="";
        if(Main_Screen.getText() !=""){
            Sub_Screen.setText(Main_Screen.getText());
        }

        Main_Screen.setText("");
        Calsymbol.setText(operator);
    }

    public void onEqualClick(View view){
        TextView Main_Screen = (TextView) findViewById(R.id.screen_Main);
        TextView Sub_Screen = (TextView) findViewById(R.id.screen_Sub);
        TextView Calsymbol = (TextView) findViewById(R.id.Calsymbol);

         double result = 0;
         DigitInput = Main_Screen.getText().toString();
         var1 = Double.parseDouble(Sub_Screen.getText().toString());
         var2 = Double.parseDouble(Main_Screen.getText().toString());

         switch (operator){
             case "+": result = var1 + var2; break;
             case "-": result = var1 - var2; break;
             case "x": result = var1 * var2; break;
             case "/": result = var2 != 0 ? var1 / var2 : 0; break;
         }
         Main_Screen.setText(String.valueOf(result));
         Sub_Screen.setText("");
         Calsymbol.setText("");
    }


}
