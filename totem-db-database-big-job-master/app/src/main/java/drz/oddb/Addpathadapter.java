package drz.oddb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.help.Tip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Addpathadapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Map<String, String>> data;

    Addpathadapter(Context context, ArrayList<Map<String, String>>  mdata) {
        mContext = context;
        data = mdata;
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.adapter_inputtips, null);
        TextView placename=view.findViewById(R.id.name) ;
        TextView latlng =  view.findViewById(R.id.address);
        Map<String,String> item =data.get(i);
        placename.setText((CharSequence) item.get("placename"));
        latlng.setText((CharSequence) item.get("latlng"));
        return view;
    }
}
