package com.russel.komando.uimodel;

import lombok.Data;

@Data
public class Status {
    private int statusId;
    private String statusTitle;
    private String updatedAt;
    private String createdAt;

    @Override
    public String toString() {
        return statusTitle;
    }
}
