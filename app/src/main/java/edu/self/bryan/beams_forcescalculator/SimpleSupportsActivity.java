package edu.self.bryan.beams_forcescalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleSupportsActivity extends ActionBarActivity {

    int beamID;
    SimpleSupportsBeam simpleSupportsBeam;
    Database database;

    EditText elasticity;
    EditText inertia;
    EditText beamLength;
    EditText force;
    EditText uniformLoad;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_supports);
        beamID = getIntent().getIntExtra("Beam ID", 0);
        imageView = (ImageView)findViewById(R.id.simple_supports_image);
        switch (beamID) {
            case 3: database = new Database(this, "simpleSupportsEndLoad.db");
                    (findViewById(R.id.uniform_load_row_ss)).setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.simple_supports_center);
                    break;
            case 4: database = new Database(this, "SimpleSupportsUniformLoad.db");
                    (findViewById(R.id.force_row_ss)).setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.simple_supports_uniform);
                    break;
        }
        initializeEditTexts();
        simpleSupportsBeam = new SimpleSupportsBeam();
    }

    public void onClickSSCalculateButton(View view) {
        switch (beamID) {
            case 3: if (elasticity.getText().toString().equals("") || inertia.getText().toString().equals("") || beamLength.getText().toString().equals("") ||
                        force.getText().toString().equals("")) {
                        Toast.makeText(this, "Please fill in all values", Toast.LENGTH_SHORT).show();
                    } else {
                        calculateResults();
                    }
                    break;
            case 4: if (elasticity.getText().toString().equals("") || inertia.getText().toString().equals("") || beamLength.getText().toString().equals("") ||
                        uniformLoad.getText().toString().equals("")) {
                        Toast.makeText(this, "Please fill in all values", Toast.LENGTH_SHORT).show();
                    } else {
                        calculateResults();
                    }
                    break;
        }
    }

    private void calculateResults() {
        getEditTextNumbers(beamID);
        TextView results = (TextView)findViewById(R.id.ss_results_text);
        if (simpleSupportsBeam.elasticity == 0 || simpleSupportsBeam.inertia == 0) {
            results.setText("Neither the modulus of elasticity nor the moment of inertia can be 0.");
        } else {
            simpleSupportsBeam.calculateReactions(beamID);
            simpleSupportsBeam.calculateMaxShear(beamID);
            simpleSupportsBeam.calculateMaxMoment(beamID);
            simpleSupportsBeam.calculateMaxDeflection(beamID);
            String resultsText = String.format("Reaction Forces (R) = %.3f\n" +
                                               "Maximum Shear = %.3f\n" +
                                               "Maximum Moment = %.3f\n" +
                                               "Maximum Deflection = %.3f",
                                               simpleSupportsBeam.reaction, simpleSupportsBeam.maxShear,
                                               simpleSupportsBeam.maxMoment, simpleSupportsBeam.maxDeflection);
            results.setText(resultsText);
        }
    }

    public void onClickSSSaveButton(View view) {
        switch (beamID) {
            case 3: if (elasticity.getText().toString().equals("") || inertia.getText().toString().equals("") || beamLength.getText().toString().equals("") ||
                        force.getText().toString().equals("")) {
                        Toast.makeText(this, "Please fill in all values", Toast.LENGTH_SHORT).show();
                    } else {
                        saveData();
                    }
                    break;
            case 4: if (elasticity.getText().toString().equals("") || inertia.getText().toString().equals("") || beamLength.getText().toString().equals("") ||
                        uniformLoad.getText().toString().equals("")) {
                        Toast.makeText(this, "Please fill in all values", Toast.LENGTH_SHORT).show();
                    } else {
                        saveData();
                    }
                    break;
        }
    }

    private void saveData() {
        getEditTextNumbers(beamID);
        database.storeSimpleSupportsValues(simpleSupportsBeam, beamID);
        Toast.makeText(this, "Configuration saved successfully", Toast.LENGTH_SHORT).show();
    }

    public void onClickSSLoadButton(View view) {
        database.getSimpleSupportsValues(simpleSupportsBeam, beamID);
        setEditTextNumbers(beamID);
    }

    private void initializeEditTexts() {
        elasticity = (EditText)findViewById(R.id.ss_elasticity_editText);
        inertia = (EditText)findViewById(R.id.ss_inertia_editText);
        beamLength = (EditText)findViewById(R.id.ss_length_editText);
        force = (EditText)findViewById(R.id.ss_force_editText);
        uniformLoad = (EditText)findViewById(R.id.ss_uniform_editText);
    }

    private void getEditTextNumbers(int id) {
        simpleSupportsBeam.elasticity = Double.valueOf(elasticity.getText().toString());
        simpleSupportsBeam.inertia = Double.valueOf(inertia.getText().toString());
        simpleSupportsBeam.lengthTotal = Double.valueOf(beamLength.getText().toString());

        switch (id) {
            case 3: simpleSupportsBeam.force = Double.valueOf(force.getText().toString());
                    break;
            case 4: simpleSupportsBeam.uniformLoad = Double.valueOf(uniformLoad.getText().toString());
                    break;
        }
    }

    private void setEditTextNumbers(int id) {
        elasticity.setText(Double.toString(simpleSupportsBeam.elasticity));
        inertia.setText(Double.toString(simpleSupportsBeam.inertia));
        beamLength.setText(Double.toString(simpleSupportsBeam.lengthTotal));

        switch (id) {
            case 3: force.setText(Double.toString(simpleSupportsBeam.force));
                    break;
            case 4: uniformLoad.setText(Double.toString(simpleSupportsBeam.uniformLoad));
                    break;
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_supports, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
}
