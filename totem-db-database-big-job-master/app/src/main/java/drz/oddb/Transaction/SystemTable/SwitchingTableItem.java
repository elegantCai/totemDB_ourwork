package drz.oddb.Transaction.SystemTable;

import java.io.Serializable;

public class SwitchingTableItem implements Serializable {
    public String attr = null;
    public String deputy = null;
    public String rule = null;
    public int orginclaid = 0;
    public int deputyclaid = 0;

    public SwitchingTableItem(String attr, String deputy, int orginclaid,int deputyclaid,String rule) {
        this.attr = attr;
        this.deputy = deputy;
        this.orginclaid = orginclaid;
        this.deputyclaid = deputyclaid;
        this.rule = rule;
    }

    public SwitchingTableItem(){}
}
