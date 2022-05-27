package drz.oddb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableRow;

import com.amap.api.services.help.Tip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import drz.oddb.Memory.TupleList;
import drz.oddb.Transaction.TransAction;

public class ShowRoadActivity extends AppCompatActivity{
    private ListView listview;
    TransAction fortran = new TransAction(this);
    private Addpathadapter pathAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_road);
        initData();

    }
    private void initData() {
        ArrayList<Map<String,String>> pathlist=getallpath();
        pathAdapter = new Addpathadapter(this,pathlist);
        listview = findViewById(R.id.pathlist_view);
        listview.setAdapter(pathAdapter);
        //设置listview点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,String> info = (Map<String,String>) adapterView.getItemAtPosition(i);
                Intent intent = new Intent();
                //改变show啥的状态
                SharedPreferences.Editor toshow=getSharedPreferences("showroad", Context.MODE_PRIVATE).edit();
                toshow.putInt("Showmode",1);
                toshow.commit();
                SharedPreferences.Editor CLassid = getSharedPreferences("pathid",Context.MODE_PRIVATE).edit();
                String[] lista=info.get("placename").split("|");
                String forint=String.valueOf(1);
                for (int aj=9;aj<16;aj++){
                    forint=forint+lista[aj];
                }
                CLassid.putInt("Pathid", Integer.parseInt(forint));
                CLassid.commit();
                intent.setClass(ShowRoadActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

    }


    public ArrayList<Map<String,String>> getallpath(){
        String [] p=new String[22];
        p[0]=String.valueOf(6);p[1]=String.valueOf(4);
        p[2]="pathid";p[3]=String.valueOf(0);p[4]=String.valueOf(0);p[5]="pathid";
        p[6]="year";p[7]=String.valueOf(0);p[8]=String.valueOf(0);p[9]="year";
        p[10]="month";p[11]=String.valueOf(0);p[12]=String.valueOf(0);p[13]="month";
        p[14]="day";p[15]=String.valueOf(0);p[16]=String.valueOf(0);p[17]="day";
        p[18]="forpath";p[19]="year";p[20]="=";p[21]=String.valueOf(2022);
        TupleList pathtpls=fortran.FortSelect(p);
        ArrayList<Map<String,String>> pathlist = new ArrayList<Map<String,String>>();
        int tabH = pathtpls.tuplenum;
        for(int r = 0;r < tabH;r++) {
            Map<String, String> thispath = new HashMap<String, String>();
            String pathtitle = null;
            String pathcontent = null;
            for (int c = 0; c < 4; c++) {
                Object oj = pathtpls.tuplelist.get(r).tuple[c];
                switch (c) {
                    case 0:
                        pathtitle = "路径ID：  " + oj.toString();
                        break;
                    case 1:
                        pathcontent = "路径日期 ：   " + oj.toString() + "年   ";
                        break;
                    case 2:
                        pathcontent = pathcontent + oj.toString() + "月   ";
                        break;
                    case 3:
                        pathcontent = pathcontent + oj.toString() + "日   ";
                        break;
                }
            }
            thispath.put("placename",pathtitle);
            thispath.put("latlng",pathcontent);
            pathlist.add(thispath);
        }
        return pathlist;
    }
}