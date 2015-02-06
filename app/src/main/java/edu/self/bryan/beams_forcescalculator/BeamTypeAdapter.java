package edu.self.bryan.beams_forcescalculator;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

public class BeamTypeAdapter extends BaseAdapter implements ListAdapter {

    private List<BeamListItem> typesOfBeams;
    private Context context;

    public BeamTypeAdapter(Context context, List<BeamListItem> typesOfBeams) {
        this.context = context;
        this.typesOfBeams = typesOfBeams;
    }

    @Override
    public int getCount() {
        return typesOfBeams.size();
    }

    @Override
    public Object getItem(int position) {
        return typesOfBeams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return typesOfBeams.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.beam_list_item, parent, false);
        }

        BeamListItem beamItem = typesOfBeams.get(position);
        String title = beamItem.getBeamType();
        int imageId = beamItem.getImageID();
        Drawable image = context.getResources().getDrawable(imageId);
        image.setBounds(new Rect(0, 0, 500, 300));

        TextView beamTitle = (TextView)convertView.findViewById(R.id.beam_type_title);
        beamTitle.setText(title);
        beamTitle.setCompoundDrawables(null, null, null, image);

        return convertView;
    }
}
