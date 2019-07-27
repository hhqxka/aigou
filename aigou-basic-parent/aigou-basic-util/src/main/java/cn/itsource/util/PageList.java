package cn.itsource.util;

import java.util.ArrayList;
import java.util.List;

public class PageList<T> {
    private Long total ;
    private List<T> rows =new ArrayList<T>();

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
