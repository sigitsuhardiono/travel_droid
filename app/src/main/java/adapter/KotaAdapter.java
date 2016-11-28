package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.kota_asal.Datum;
import sigit.pertama.R;

/**
 * Created by www.scclaptop.com on 11/23/2016.
 */
public class KotaAdapter extends ArrayAdapter<Datum> {

    private int layoutResource;
    private List<Datum> worldpopulationlist = null;
    private ArrayList<Datum> arraylist;
    public KotaAdapter(Context context, int layoutResource, List<Datum> threeStringsList) {
        super(context, layoutResource, threeStringsList);
        this.layoutResource = layoutResource;
        this.worldpopulationlist = threeStringsList;
        this.arraylist = new ArrayList<Datum>();
        this.arraylist.addAll(threeStringsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Datum data = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_kota, parent, false);
        }
        TextView nama = (TextView) convertView.findViewById(R.id.kota_travel);
        ImageView icon = (ImageView) convertView.findViewById(R.id.kota_travel_icon);
        nama.setText(data.getId()+". "+data.getNama());
        if (data.getIsBandara().equals("1")) {
            icon.setImageResource(R.drawable.ic_bandara);
        } else {
            icon.setImageResource(R.drawable.ic_map);
        }
        return convertView;
    }

    // Filter Class
    public void filter(CharSequence charText) {
        charText = charText.toString().toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        } else {
            for (Datum wp : arraylist) {
                if (wp.getNama().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

