package com.russel.komando.util;

import com.russel.komando.entity.StatusData;
import com.russel.komando.model.Status;

public class StatusMapper {
    public static Status toModel(StatusData statusData) {
        if (statusData == null) return null;
        Status status = new Status();
        status.setStatusId(statusData.getStatusId());
        status.setStatusTitle(statusData.getStatusTitle());
        status.setCreatedAt(statusData.getCreatedAt());
        status.setUpdatedAt(statusData.getUpdatedAt());
        return status;
    }

    public static StatusData toEntity(Status status) {
        if (status == null) return null;
        StatusData statusData = new StatusData();
        statusData.setStatusId(status.getStatusId());
        statusData.setStatusId(status.getStatusId());
        statusData.setStatusTitle(status.getStatusTitle());
        return statusData;
    }
}
