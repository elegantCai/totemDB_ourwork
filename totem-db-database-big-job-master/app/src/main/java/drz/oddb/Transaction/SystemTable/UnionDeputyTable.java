package drz.oddb.Transaction.SystemTable;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;

public class UnionDeputyTable implements Serializable{
    public List<UnionDeputyTableItem> uniondeputyTable=new ArrayList<>();

    public void clear(){
        uniondeputyTable.clear();
    }
}
