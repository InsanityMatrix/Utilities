package com.dvpie.utilities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dvpie.utilities.algebra.AlgebraSolver;


public class AlgebraicCalculator extends AppCompatActivity {
    public AlgebraSolver calculator;
    EditText eq;
    TextView output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algebraic_calculator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eq = findViewById(R.id.equation);
        output = findViewById(R.id.calc_output);
        calculator = new AlgebraSolver();
    }

    public void solveEquation(View view) {
        output.setText("");
        String equation = eq.getText().toString();

        //Clear keyboard from screen
        InputMethodManager inputMM = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        try {
            //May cause Null Pointer Exception if there is no keyboard
            inputMM.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            System.out.println("There was no keyboard to clear. Moving on.");
        }


        String[] response = calculator.solve(equation);
        for(String line : response) {
            output.append(line + "\n");
        }
        output.append("--------");
    }
}
