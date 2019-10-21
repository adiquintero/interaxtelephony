package com.interax.persistence.audict;

public enum ActionType {
    ADD ("ADD"),
    UPD_OBJECT_SINGLE ("SINGLE OBJECT UPDATE"),
    UPD_FIELDS_SINGLE ("SINGLE FIELDS UPDATE"),
    UPD_OBJECT_MULTIPLE ("MULTIPLE OBJECT UPDATE"),
    UPD_FIELDS_MULTIPLE ("MULTIPLE FIELDS UPDATE"),
    DEL_SINGLE ("DELETE SINGLE"),
    DEL_MULTIPLE ("DELETE MULTIPLE");

    private final String txt;
    
    ActionType(String txt) {
        this.txt = txt;
    }

	public String getTxt() {
		return txt;
	}
}
