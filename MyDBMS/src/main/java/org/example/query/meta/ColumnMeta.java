package org.example.query.meta;

public class ColumnMeta {
    private String clmName;
    private String dataType;

    public ColumnMeta(String clmName, String dataType) {
        this.clmName = clmName;
        this.dataType = dataType;
    }

    public String getClmName() {
        return clmName;
    }

    public String getDataType() {
        return dataType;
    }

}
