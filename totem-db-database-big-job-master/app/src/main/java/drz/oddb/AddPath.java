package drz.oddb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.amap.api.services.help.Tip;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import drz.oddb.Transaction.TransAction;

/**
 * author：  HyZhan
 * created：2018/9/20 15:06
 * desc：    TODO
 */

public class AddPath extends AppCompatActivity {
    private ListView mTvPlace;
    private static final int REQUEST_PLACE = 1;
    private Addpathadapter pathAdapter;
    TransAction fortran = new TransAction(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_path);

        Button mBtnSearch = findViewById(R.id.btn_search2);
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPath.this, InputTipsActivity.class);
                startActivityForResult(intent, REQUEST_PLACE);
            }
        });

        Button button3;
        button3 = (Button) findViewById(R.id.btn_commitpath);//
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                fortran.SaveAll();    //保存刚才的路径(所有更改）
//              //改变show啥的状态
                SharedPreferences.Editor toshow=getSharedPreferences("showroad",Context.MODE_PRIVATE).edit();
                toshow.putInt("Showmode",1);
                toshow.commit();

                Intent intent = new Intent();
                intent.setClass(AddPath.this, MapActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                startActivity(intent);
            }
        });

        mTvPlace = findViewById(R.id.pathlist);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PLACE) {
            if (resultCode == InputTipsActivity.RESULT_CODE_INPUTTIPS && data != null) {

                ArrayList<Map<String,String>> pathlist = new ArrayList<Map<String,String>>();
                final Tip tip = data.getParcelableExtra("tip");
                if (tip.getName() != null) {
                    //mTvPlace.setText("选择地点是：" + tip.getName() + tip.getPoint());
                }
                SharedPreferences placelist = getSharedPreferences("placelist", Context.MODE_PRIVATE);
                SharedPreferences.Editor placelisteditor = placelist.edit();
                SharedPreferences latlnglist = getSharedPreferences("latlnglist", Context.MODE_PRIVATE);
                SharedPreferences.Editor latlnglisteditor = latlnglist.edit();
                int placeNums = placelist.getInt("PlaceNums", 0);
                for (int i = 0; i < placeNums; i++)
                {
                    String placeItem = placelist.getString("item_"+i, null);
                    String latlngItem = latlnglist.getString("item_"+i, null);
                    Map<String,String> thisplace= new HashMap<String,String>();
                    thisplace.put("placename",placeItem);
                    thisplace.put("latlng",latlngItem);
                    pathlist.add(thisplace);
                    //mTvPlace.setText("选择地点是：" + environItem);
                }
                Map<String,String> thisplace= new HashMap<String,String>();
                thisplace.put("placename",tip.getName());
                double lattitude=tip.getPoint().getLatitude();
                double Longitude=tip.getPoint().getLongitude();
                String forlatlng = "纬度:  " + String.valueOf(lattitude) +"   " +"经度： "+String.valueOf(Longitude);
                thisplace.put("latlng",forlatlng);
                pathlist.add(thisplace);
                placelisteditor.putInt("PlaceNums", placeNums+1);
                placelisteditor.putString("item_"+placeNums,tip.getName() );
                latlnglisteditor.putInt("latlngNums", placeNums+1);
                latlnglisteditor.putString("item_"+placeNums,forlatlng );
                placelisteditor.commit();
                latlnglisteditor.commit();

                //这里在存数据库
                SharedPreferences Wnew = getSharedPreferences("dbnew", Context.MODE_PRIVATE);
                int IFnew = Wnew.getInt("WNew", 0);
                if (IFnew==1) {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    Long timestampa = timestamp.getTime();
                    int timestampint = -timestampa.intValue();
                    timestampint = timestampint/100;
                    System.out.println(timestamp.getTime());
                    Calendar calendar =Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH)+1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    String aa=null;   // 变成解析的格式
                    aa = "INSERT" +" "+"INTO"+" "+"forpath" + " " +"VALUES" + " " +"(" +
                            String.valueOf(timestampint) + ","+
                            String.valueOf(year) + "," + String.valueOf(month) +
                            "," + String.valueOf(day) +")" +";";
                    fortran.query(aa);
                    SharedPreferences.Editor Wneweditor = Wnew.edit();
                    Wneweditor.putInt("WNew",0);
                    Wneweditor.commit();
                    SharedPreferences.Editor CLassid = getSharedPreferences("pathid",Context.MODE_PRIVATE).edit();
                    CLassid.putInt("Pathid",timestampint);
                    CLassid.commit();
                }
                double fractionalPart = lattitude % 1;
                double integralPart = lattitude - fractionalPart;
                int latint = (int)integralPart;
                fractionalPart = fractionalPart *1000000;
                int latfrac = (int) fractionalPart;

                fractionalPart = Longitude % 1 ;
                integralPart = Longitude - fractionalPart;
                int longint = (int) integralPart;
                fractionalPart = fractionalPart * 1000000;
                int longfrac = (int) fractionalPart;
                SharedPreferences classid = getSharedPreferences("pathid",Context.MODE_PRIVATE);
                int classidint = classid.getInt("Pathid",-1);
                String aa = null;
                aa = "INSERT" +" "+"INTO"+" "+"pathnode" + " "+"VALUES" + " " +"(" +
                        String.valueOf(classidint) + "," + String.valueOf(placeNums)+","+
                        String.valueOf(longint) + "," + String.valueOf(longfrac) +
                        "," + String.valueOf(latint) +"," + String.valueOf(latfrac)+")" +";";
                fortran.query(aa);
                pathAdapter = new Addpathadapter(this,pathlist);
                mTvPlace.setAdapter(pathAdapter);
            }
        }
    }

}
