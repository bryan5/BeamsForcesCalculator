package edu.self.bryan.beams_forcescalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CantileverActivity extends ActionBarActivity {

    int beamID;
    CantileverBeam cantileverBeam;
    Database database;

    EditText elasticity;
    EditText inertia;
    EditText beamLength;
    EditText force;
    EditText partialLength;
    EditText uniformLoad;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cantilever);
        beamID = getIntent().getIntExtra("Beam ID", 0);
        imageView = (ImageView)findViewById(R.id.cantilever_image);
        switch(beamID) {
            case 0: database = new Database(this, "cantileverEndLoad.db");
                    (findViewById(R.id.length_a_row)).setVisibility(View.GONE);
                    (findViewById(R.id.uniform_load_row)).setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.cantilever_end_load);
                    break;
            case 1: database = new Database(this, "cantileverIntLoad.db");
                    (findViewById(R.id.uniform_load_row)).setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.cantilever_int_load);
                    break;
            case 2: database = new Database(this, "cantileverUniformLoad.db");
                    (findViewById(R.id.force_row)).setVisibility(View.GONE);
                    (findViewById(R.id.length_a_row)).setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.cantilever_uniform_load);
                    break;
        }
        initializeEditTexts();
        cantileverBeam = new CantileverBeam();
    }

    public void onClickCalculateButton(View view) {
        switch (beamID) {
            case 0: if (elasticity.getText().toString().equals("") || inertia.getText().toString().equals("") || beamLength.getText().toString().equals("") ||
                        force.getText().toString().equals("")) {
                        Toast.makeText(this, "Please fill in all values", Toast.LENGTH_SHORT).show();
                    } else {
                        calculateResults();
                    }
                    break;
            case 1: if (elasticity.getText().toString().equals("") || inertia.getText().toString().equals("") || beamLength.getText().toString().equals("") ||
                        force.getText().toString().equals("") || partialLength.getText().toString().equals("")) {
                        Toast.makeText(this, "Please fill in all values", Toast.LENGTH_SHORT).show();
                    } else {
                        calculateResults();
                    }
                    break;
            case 2: if (elasticity.getText().toString().equals("") || inertia.getText().toString().equals("") || beamLength.getText().toString().equals("") ||
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
        TextView results = (TextView) findViewById(R.id.cantilever_results_text);
        if (cantileverBeam.elasticity == 0 || cantileverBeam.inertia == 0) {
            results.setText("Neither the modulus of elasticity nor the moment of inertia can be 0.");
        } else {
            cantileverBeam.calculateReaction(beamID);
            cantileverBeam.calculateMaxShear(beamID);
            cantileverBeam.calculateMaxMoment(beamID);
            cantileverBeam.calculateMaxDeflection(beamID);
            String resultsText = String.format("Reaction Force (R) = %.3f\n" +
                                               "Maximum Shear = %.3f\n" +
                                               "Maximum Moment = %.3f\n" +
                                               "Maximum Deflection = %.3f",
                                               cantileverBeam.reaction, cantileverBeam.maxShear,
                                               cantileverBeam.maxMoment, cantileverBeam.maxDeflection);
            results.setText(resultsText);
        }
    }

    public void onClickSaveButton(View view) {
        switch (beamID) {
            case 0: if (elasticity.getText().toString().equals("") || inertia.getText().toString().equals("") || beamLength.getText().toString().equals("") ||
                        force.getText().toString().equals("")) {
                        Toast.makeText(this, "Please fill in all values", Toast.LENGTH_SHORT).show();
                    } else {
                        saveData();
                    }
                    break;
            case 1: if (elasticity.getText().toString().equals("") || inertia.getText().toString().equals("") || beamLength.getText().toString().equals("") ||
                        force.getText().toString().equals("") || partialLength.getText().toString().equals("")) {
                        Toast.makeText(this, "Please fill in all values", Toast.LENGTH_SHORT).show();
                    } else {
                        saveData();
                    }
                    break;
            case 2: if (elasticity.getText().toString().equals("") || inertia.getText().toString().equals("") || beamLength.getText().toString().equals("") ||
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
        database.storeCantileverValues(cantileverBeam, beamID);
        Toast.makeText(this, "Configuration successfully saved", Toast.LENGTH_SHORT).show();
    }

    public void onClickLoadButton(View view) {
        database.getCantileverValues(cantileverBeam, beamID);
        setEditTextNumbers(beamID);
    }

    private void initializeEditTexts() {
        elasticity = (EditText)findViewById(R.id.cantilever_elasticity_editText);
        inertia = (EditText)findViewById(R.id.cantilever_inertia_editText);
        beamLength = (EditText)findViewById(R.id.cantilever_length_editText);
        force = (EditText)findViewById(R.id.cantilever_force_editText);
        partialLength = (EditText)findViewById(R.id.cantilever_length_a_editText);
        uniformLoad = (EditText)findViewById(R.id.cantilever_uniform_load_editText);

    }

    private void getEditTextNumbers(int id) {
        cantileverBeam.elasticity = Double.valueOf(elasticity.getText().toString());
        cantileverBeam.inertia = Double.valueOf(inertia.getText().toString());
        cantileverBeam.lengthTotal = Double.valueOf(beamLength.getText().toString());

        switch (id) {
            case 0: cantileverBeam.force = Double.valueOf(force.getText().toString());
                    break;
            case 1: cantileverBeam.force = Double.valueOf(force.getText().toString());
                    cantileverBeam.partialLength = Double.valueOf(partialLength.getText().toString());
                    break;
            case 2: cantileverBeam.uniformLoad = Double.valueOf(uniformLoad.getText().toString());
                    break;
        }
    }

    private void setEditTextNumbers(int id) {
        elasticity.setText(Double.toString(cantileverBeam.elasticity));
        inertia.setText(Double.toString(cantileverBeam.inertia));
        beamLength.setText(Double.toString(cantileverBeam.lengthTotal));

        switch (id) {
            case 0: force.setText(Double.toString(cantileverBeam.force));
                    break;
            case 1: force.setText(Double.toString(cantileverBeam.force));
                    partialLength.setText(Double.toString(cantileverBeam.partialLength));
                    break;
            case 2: uniformLoad.setText(Double.toString(cantileverBeam.uniformLoad));
                    break;
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cantilever, menu);
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
