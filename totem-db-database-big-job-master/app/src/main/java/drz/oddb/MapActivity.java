package drz.oddb;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Map;

import drz.oddb.Memory.Tuple;
import drz.oddb.Memory.TupleList;
import drz.oddb.Transaction.TransAction;

public class MapActivity extends AppCompatActivity {
    TransAction fortran = new TransAction(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);//设置对应的XML布局文件

        MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap aMap = mapView.getMap();
        SharedPreferences Showroad = getSharedPreferences("showroad",Context.MODE_PRIVATE);
        int toshow=Showroad.getInt("Showmode",0);
        if (toshow==0){
            LatLng latLng = new LatLng(30.536507,114.364092);
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,80));
            aMap.addPolyline((new PolylineOptions()).add(new LatLng(30.536507,114.364092),new LatLng(30.535559,114.36463),new LatLng(30.53597,114.362705)).color(0xFFFF7F00));
            aMap.addPolyline((new PolylineOptions()).add(new LatLng(30.536507,114.364092),new LatLng(30.536747,114.361906),new LatLng(30.53317,114.358043)).color(0xFFFF7F00));
            aMap.addPolyline((new PolylineOptions()).add(new LatLng(30.536507,114.364092),new LatLng(30.53835,114.361879),new LatLng(30.539306,114.360436)).color(0xFFFF7F00));
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("我的位置");
            aMap.addMarker(markerOptions);
        }
        if (toshow==1){
            SharedPreferences pathid = getSharedPreferences("pathid",
                    Context.MODE_PRIVATE);
            int intpathid=pathid.getInt("Pathid",-1);
            //int storepathid = intpathid/1000;
            //String aa=null;
            //aa="SELECT longing AS longing,longnode AS longnode,lati AS lati,latinode AS latinode FROM pathnode WHERE nodeid="+String.valueOf(intpathid)+";";
            String[] p=new String[22];
            p[0]=String.valueOf(6);p[1]=String.valueOf(4);
            p[2]="longing";p[3]=String.valueOf(0);p[4]=String.valueOf(0);p[5]="longing";
            p[6]="longnode";p[7]=String.valueOf(0);p[8]=String.valueOf(0);p[9]="longnode";
            p[10]="lati";p[11]=String.valueOf(0);p[12]=String.valueOf(0);p[13]="lati";
            p[14]="latinode";p[15]=String.valueOf(0);p[16]=String.valueOf(0);p[17]="latinode";
            p[18]="pathnode";p[19]="pathid";p[20]="=";p[21]=String.valueOf(intpathid);
            TupleList roadnodes=fortran.FortSelect(p);
            Toaddpolyline(aMap,roadnodes);
        }

        Button button1;
        button1 = (Button) findViewById(R.id.btn1);//
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                SharedPreferences.Editor editor= getSharedPreferences("placelist",
                        Context.MODE_PRIVATE).edit();
                editor.clear();
                editor.putInt("PlaceNums", 1);
                editor.putString("item_"+0,"下面是你现有的路径" );
                editor.commit();
                SharedPreferences.Editor editor2= getSharedPreferences("latlnglist",
                        Context.MODE_PRIVATE).edit();
                editor2.clear();
                editor2.putInt("latlngNums", 1);
                editor2.putString("item_"+0,"这一行是你的路径的经纬度" );
                editor2.commit();
                intent.setClass(MapActivity.this, AddPath.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                startActivity(intent);
                SharedPreferences.Editor editor3= getSharedPreferences("dbnew",
                        Context.MODE_PRIVATE).edit();
                editor3.clear();
                editor3.putInt("WNew", 1);
                editor3.commit();
                SharedPreferences.Editor editor4= getSharedPreferences("pathid",
                        Context.MODE_PRIVATE).edit();
                editor4.clear();
                editor4.putInt("Pathid", -1);
                editor4.commit();
            }
        });

        Button button2;
        button2 = (Button) findViewById(R.id.btn2);//
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(MapActivity.this, ShowRoadActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                startActivity(intent);
            }
        });

        Button button3;
        button3 = (Button) findViewById(R.id.btn3);//
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(MapActivity.this, MainActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                startActivity(intent);
            }
        });
    }

    public void Toaddpolyline(AMap aMap,TupleList pathnodes){
        ArrayList<LatLng> latlngs=new ArrayList<LatLng>();
        int tabH = pathnodes.tuplenum;
        for(int r = 0;r < tabH;r++){
            double latitude=0;
            double longtitude=0;
            for(int c = 2;c < 6;c++){
                Object oj=pathnodes.tuplelist.get(r).tuple[c];
                switch(c) {
                    case 2:
                        longtitude = Double.valueOf(oj.toString());
                        break;
                    case 3:
                        longtitude= longtitude+Double.valueOf(oj.toString())/1000000;
                        break;
                    case 4:
                        latitude =Double.valueOf(oj.toString());
                        break;
                    case 5:
                        latitude= latitude+Double.valueOf(oj.toString())/1000000;
                        break;
                }
            }
            LatLng thisnodell=new LatLng(latitude,longtitude);
            latlngs.add(thisnodell);
        }
        PolylineOptions thisline=(new PolylineOptions());
        for (LatLng i:latlngs){
            thisline.add(i);
            aMap.addMarker(new MarkerOptions().position(i));
        }
        thisline.color(0xFF7F007F);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngs.get(0),40));
        aMap.addPolyline(thisline);
    }


}