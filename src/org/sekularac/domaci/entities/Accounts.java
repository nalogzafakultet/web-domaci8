package org.sekularac.domaci.entities;

import org.sekularac.domaci.utils.Utils;

import java.io.Serializable;

public class Accounts extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // Properties
    private String username;
    private String password;

    // Column names
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public Accounts() {
        super();
        columnNames.add(COLUMN_USERNAME);
        columnNames.add(COLUMN_PASSWORD);
        this.setUsername("");
        this.setPassword("");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void setValueForColumnName(String columnName, Object value) {
        if (COLUMN_USERNAME.equals(columnName)) {
            this.setUsername(Utils.safeConvertToStr(value));
        } else if (COLUMN_PASSWORD.equals(columnName)) {
            this.setPassword(Utils.safeConvertToStr(value));
        } else {
            super.setValueForColumnName(columnName, value);
        }
    }

    @Override
    public Object getValueForColumnName(String columnName) {
        if (COLUMN_USERNAME.equals(columnName)) {
            return this.username;
        } else if (COLUMN_PASSWORD.equals(columnName)) {
            return this.password;
        }
        return super.getValueForColumnName(columnName);
    }
}
