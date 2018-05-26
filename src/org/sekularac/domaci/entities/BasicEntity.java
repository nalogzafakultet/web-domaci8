package org.sekularac.domaci.entities;

import org.sekularac.domaci.utils.Utils;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    protected List<String> columnNames;
    private static final String ID = "id";
    private int id;

    public BasicEntity() {
        this.setId(Integer.MIN_VALUE);
        this.columnNames = new ArrayList<>();
        this.columnNames.add(this.ID);
    }

    @Override
    public int hashCode() {
        final int prime = 53;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        if (obj instanceof BasicEntity) {
            BasicEntity other = (BasicEntity) obj;
            return this.id == other.id;
        }
        return false;
    }

    public List<String> columnNames() {
        return columnNames;
    }

    public void setValueForColumnName(String columnName, Object value) {
        if (this.ID.equals(columnName)) {
            this.setId(Utils.safeConvertToInt(value));
        }
    }

    public Object getValueForColumnName(String columnName) {
        if (this.ID.equals(columnName)) {
            return this.id;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String primaryKeyColumnName() {
        return this.ID;
    }
}
