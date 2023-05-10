package com.example.festo.booth.domain;

public enum BoothStatus {
    CLOSE("close",false),
    OPEN("open",true);

    private String status;
    private boolean isOpen;

    BoothStatus(String status, boolean isOpen) {
        this.status=status;
        this.isOpen =isOpen;
    }

    public boolean getIsOpen(){
        return isOpen;
    }
}
