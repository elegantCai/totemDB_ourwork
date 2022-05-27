package drz.oddb.show;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.io.Serializable;

import drz.oddb.R;
import drz.oddb.Transaction.SystemTable.UnionDeputyTable;

public class ShowUniDep extends AppCompatActivity implements Serializable {
    private final int W = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int M = ViewGroup.LayoutParams.MATCH_PARENT;
    private TableLayout show_tab;
    //private ArrayList<String> objects = new ArrayList<String> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ShowUniDep", "oncreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_result);

        Intent intent = getIntent();
        Bundle bundle0 = intent.getExtras();
        showDepTab((UnionDeputyTable) bundle0.getSerializable("UnionDepTable"));

    }

    private void showDepTab(UnionDeputyTable deputyt) {
        int tabCol = 5;
        int tabH = deputyt.uniondeputyTable.size();
        Object oj,oj1,oj2;
        String stemp1, stemp2, stemp3,stemp4, stemp5;
        String[] satemp,satemp1;

        show_tab = findViewById(R.id.rst_tab);

        for (int i = 0; i <= tabH; i++) {
            TableRow tableRow = new TableRow(this);
            if (i == 0) {
                stemp1 = "deputyid";
                stemp2 = "originid";
                stemp3 = "originid2";
                stemp4 = "deputyrule";
                stemp5 = "deputyrule2";

            } else {
                oj = deputyt.uniondeputyTable.get(i-1).deputyid;
                oj1 = deputyt.uniondeputyTable.get(i-1).originid;
                oj2 = deputyt.uniondeputyTable.get(i-1).originid2;
                satemp = deputyt.uniondeputyTable.get(i-1).deputyrule;
                satemp1 = deputyt.uniondeputyTable.get(i-1).deputyrule2;
                stemp1 = oj.toString();
                stemp2 = oj1.toString();
                stemp3 = oj2.toString();
                stemp4 = satemp[0].toString()+satemp[1].toString()+satemp[2].toString();
                stemp5 = satemp1[0].toString()+satemp1[1].toString()+satemp1[2].toString();
            }
            for (int j = 0; j < tabCol; j++) {
                TextView tv = new TextView(this);
                switch (j) {
                    case 0:
                        tv.setText(stemp1);
                        break;
                    case 1:
                        tv.setText(stemp2);
                        break;
                    case 2:
                        tv.setText(stemp3);
                        break;
                    case 3:
                        tv.setText(stemp4);
                        break;
                    case 4:
                        tv.setText(stemp5);
                        break;

                }
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.drawable.tab_bg);
                tv.setTextSize(28);
                tableRow.addView(tv);
            }
            show_tab.addView(tableRow, new TableLayout.LayoutParams(M, W));

        }

    }

}

