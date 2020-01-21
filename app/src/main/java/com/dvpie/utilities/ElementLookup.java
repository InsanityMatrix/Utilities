package com.dvpie.utilities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dvpie.utilities.Chemistry.CommonIons;
import com.dvpie.utilities.Chemistry.Element;
import com.dvpie.utilities.Chemistry.Ion;
import com.dvpie.utilities.Chemistry.PeriodicTable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElementLookup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_lookup);
        LinearLayout elementinfo = findViewById(R.id.element_info);
        elementinfo.setVisibility(View.INVISIBLE);
    }

    public void searchElements(View view) {
        //First We Will Take Away Keyboard
        InputMethodManager inputMM = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        try {
            //May cause Null Pointer Exception if there is no keyboard
            inputMM.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            //No Keyboard to clear, safe to move on
        }
        //Get Input
        EditText input = findViewById(R.id.element_search_input);


        String query = input.getText().toString();
        //Decipher Input Type
        String match;
        char iType;
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile("^\\d+$");
        matcher = pattern.matcher(query);
        if(matcher.matches()) {
            match = matcher.group(0);
            iType = 'n';
        } else {
            pattern = Pattern.compile("\\w+");
            matcher = pattern.matcher(query);
            if(matcher.matches()) {
                match = matcher.group(0);
                if(match.length() < 3) {
                    iType = 's';
                } else {
                    iType = 't';
                }
            } else {
                match = "Error";
                iType = 'e';
            }
        }
        //IMPORTANT: iType n = number, s = symbol, t = name, e = Error
        //Get Info
        Element element;
        if(iType == 'e') {
            //ERROR STUFF HERE

            return;
        } else if(iType == 'n') {
            element = PeriodicTable.getElementByANumber(Integer.parseInt(match));
        } else if(iType == 's') {
            element = PeriodicTable.getElementBySymbol(match);
        } else {
            element = PeriodicTable.getElementByName(match);
        }

        if(element == null) {
            //We Dont have this element?
            //Check Ion time
            Ion ion = CommonIons.getIonByName(match);
            if(ion == null) {
                return;
            }
            TextView AtomicNum = findViewById(R.id.element_atomic_num);
            TextView Symbol = findViewById(R.id.element_symbol);
            TextView Name = findViewById(R.id.element_name);
            TextView Weight = findViewById(R.id.element_weight);
            TextView Location = findViewById(R.id.element_location);
            Symbol.setText(ion.getFormula());
            Symbol.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
            Name.setText(CommonIons.getNameByName(match));
            Weight.setText("");
            String charge = "Charge: " + ion.getCharge();
            Location.setText(charge);
            AtomicNum.setText("");
            LinearLayout elementinfo = findViewById(R.id.element_info);
            elementinfo.setVisibility(View.VISIBLE);
            return;
        }
        //Display Info
        TextView AtomicNum = findViewById(R.id.element_atomic_num);
        TextView Symbol = findViewById(R.id.element_symbol);
        TextView Name = findViewById(R.id.element_name);
        TextView Weight = findViewById(R.id.element_weight);
        TextView Location = findViewById(R.id.element_location);

        String loc = "Group: " + element.getGroup() + " | Period: " + element.getPeriod();
        AtomicNum.setText((element.getAtomic_number() + "" ));
        Symbol.setText(element.getSymbol());
        Name.setText(element.getName());
        Weight.setText((element.getAtomic_mass() + ""));
        Location.setText(loc);

        LinearLayout elementinfo = findViewById(R.id.element_info);
        elementinfo.setVisibility(View.VISIBLE);
    }
}
