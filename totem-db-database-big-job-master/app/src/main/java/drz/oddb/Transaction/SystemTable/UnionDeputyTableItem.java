package drz.oddb.Transaction.SystemTable;
import java.io.Serializable;

public class UnionDeputyTableItem implements Serializable{
    public UnionDeputyTableItem(int deputyid,int originid, int originid2, String[] deputyrule,String[] deputyrule2) {
        this.deputyid = deputyid;
        this.originid = originid;
        this.originid2 = originid2;
        this.deputyrule = deputyrule;
        this.deputyrule2 = deputyrule2;
    }

    public UnionDeputyTableItem() {
    }

    public int deputyid = 0;           //代理类id
    public int originid = 0;            //类id
    public int originid2 = 0;            //类id2
    public String[] deputyrule = null;    //代理guizedui
    public String[] deputyrule2 = null;    //代理guizedui
}
