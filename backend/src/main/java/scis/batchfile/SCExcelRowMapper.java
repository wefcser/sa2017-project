package scis.batchfile;

import scis.excel.RowMapper;
import scis.excel.support.rowset.RowSet;
import scis.model.SC;

public class SCExcelRowMapper implements RowMapper<SC> {

    @Override
    public SC mapRow(RowSet rowSet) throws Exception {
        SC sc = new SC();
        sc.setNo(rowSet.getColumnValue(0));
        sc.setName(rowSet.getColumnValue(1));
        sc.setDepart(rowSet.getColumnValue(2));
        sc.setCourse(rowSet.getColumnValue(3));
        return sc;
    }
}