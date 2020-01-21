package com.dvpie.utilities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChemistryTools extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistry_tools);
    }

    public void startElementLookup(View view) {
        Intent intent = new Intent(this, ElementLookup.class);
        startActivity(intent);
    }

    public void startEffDeffCalculator(View view) {
        Intent intent = new Intent(this, EffusionDeffusionSolver.class);
        startActivity(intent);
    }
}
