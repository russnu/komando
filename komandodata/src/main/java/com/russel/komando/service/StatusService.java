package com.russel.komando.service;

import com.russel.komando.model.Status;

import java.util.List;

public interface StatusService {
    List<Status> fetchAllStatuses();
    Status fetchStatus(Integer statusId);
    Status fetchStatusByTitle(String title);
    Status create(Status status);
    Status update(Status status);
}
