package com.dvpie.utilities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dvpie.utilities.Chemistry.EffusionDefusionCalculator;
import com.dvpie.utilities.Chemistry.Element;
import com.dvpie.utilities.Chemistry.PeriodicTable;

public class EffusionDeffusionSolver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effusion_deffusion_solver);
    }

    public void solveEffusionDeffusion(View view) {
        //First We Will Take Away Keyboard
        InputMethodManager inputMM = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        try {
            //May cause Null Pointer Exception if there is no keyboard
            inputMM.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            //No Keyboard to clear, safe to move on
        }
        EditText ElementA_ET = findViewById(R.id.ElementA);
        EditText ElementB_ET = findViewById(R.id.ElementB);
        EditText RateA_ET = findViewById(R.id.RateA);
        EditText RateB_ET = findViewById(R.id.RateB);
        EffusionDefusionCalculator calculator = new EffusionDefusionCalculator();

        //Get our Elements
        String ElA_Text = ElementA_ET.getText().toString();
        String ElB_Text = ElementB_ET.getText().toString();
        Element elementA, elementB;
        //TODO: Better Element Searching / Like the Element Lookup
        if(ElA_Text.length() > 2) {
            elementA = PeriodicTable.getElementByName(ElA_Text);
        } else {
            elementA = PeriodicTable.getElementBySymbol(ElA_Text);
        }
        if(ElB_Text.length() > 2) {
            elementB = PeriodicTable.getElementByName(ElB_Text);
        } else {
            elementB = PeriodicTable.getElementBySymbol(ElB_Text);
        }

        //Find out if we have any of the Rates.
        boolean isRateAEmpty = TextUtils.isEmpty(RateA_ET.getText());
        boolean isRateBEmpty = TextUtils.isEmpty(RateB_ET.getText());

        //Generate Output
        TextView oConsole = findViewById(R.id.effdeffsolution);
        if(isRateAEmpty && isRateBEmpty) {
            double ratio = calculator.calculateRatio(elementA, elementB);
            String output = "The Ratio is:\n" + ratio;
            oConsole.setText(output);

        } else if(isRateBEmpty) {
            String rateA_Str = RateA_ET.getText().toString();
            double rateA = Double.parseDouble(rateA_Str);

            double answer = calculator.findXWithRateOfA(rateA,elementA,elementB);
            String output = "The Rate of Element B is:\n" + answer;
            oConsole.setText(output);
        } else if(isRateAEmpty) {
            String rateB_Str = RateB_ET.getText().toString();
            double rateB = Double.parseDouble(rateB_Str);

            double answer = calculator.findXWithRateOfB(rateB,elementA, elementB);
            String output = "The Rate of Element A is:\n" + answer;
            oConsole.setText(output);
        }

    }
}
