package edu.self.bryan.beams_forcescalculator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeBeamList();
    }

    private void initializeBeamList() {
        List<BeamListItem> typesOfBeams = new ArrayList<>();

        typesOfBeams.add(new BeamListItem(0, getString(R.string.cantilever_end_load), R.drawable.cantilever_end_load));
        typesOfBeams.add(new BeamListItem(1, getString(R.string.cantilever_int_load), R.drawable.cantilever_int_load));
        typesOfBeams.add(new BeamListItem(2, getString(R.string.cantilever_uniform_load), R.drawable.cantilever_uniform_load));
        typesOfBeams.add(new BeamListItem(3, getString(R.string.simple_supports_center_load), R.drawable.simple_supports_center));
        typesOfBeams.add(new BeamListItem(4, getString(R.string.simple_supports_uniform_load), R.drawable.simple_supports_uniform));

        final BeamTypeAdapter beamTypeAdapter = new BeamTypeAdapter(this, typesOfBeams);

        final ListView beamListView = (ListView)findViewById(R.id.beam_type_list);

        beamListView.setAdapter(beamTypeAdapter);

        beamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position <= 2) {
                    Intent intent = new Intent(MainActivity.this, CantileverActivity.class);
                    intent.putExtra("Beam ID", position);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, SimpleSupportsActivity.class);
                    intent.putExtra("Beam ID", position);
                    startActivity(intent);
                }
            }
        });
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
