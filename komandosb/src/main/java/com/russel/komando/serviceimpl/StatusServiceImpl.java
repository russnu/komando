package com.russel.komando.serviceimpl;

import com.russel.komando.entity.StatusData;
import com.russel.komando.exception.NotFound;
import com.russel.komando.model.Status;
import com.russel.komando.repository.StatusRepository;
import com.russel.komando.service.StatusService;
import com.russel.komando.util.StatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    StatusRepository statusRepository;
    //========================================================================================================//
    @Override
    public List<Status>fetchAllStatuses(){
        List<StatusData> statusDataList = new ArrayList<>();
        statusRepository.findAll().forEach(statusDataList::add);

        List<Status> statuses = new ArrayList<>();
        for (StatusData data : statusDataList) {
            Status status = StatusMapper.toModel(data);
            statuses.add(status);
        }
        return statuses;
    }
    //========================================================================================================//
    @Override
    public Status fetchStatus(Integer statusId) {
        StatusData data = statusRepository.findById(statusId)
                .orElseThrow(() -> new NotFound("Status with ID " + statusId + " not found."));
        if (data == null) return null;

        Status status = StatusMapper.toModel(data);

        return status;
    }
    //========================================================================================================//
    @Override
    public Status fetchStatusByTitle(String title) {
        StatusData data = statusRepository.findByStatusTitleIgnoreCase(title)
                .orElseThrow(() -> new NotFound("Status with title \"" + title + "\" not found."));

        return StatusMapper.toModel(data);
    }
    //========================================================================================================//
    @Override
    public Status create(Status status){
        StatusData data = new StatusData();
        data.setStatusTitle(status.getStatusTitle());

        StatusData saved = statusRepository.save(data);
        return StatusMapper.toModel(saved);
    }
    //========================================================================================================//
    @Override
    public Status update(Status status){
        StatusData existing = statusRepository.findById(status.getStatusId())
                .orElseThrow(() -> new NotFound("Status with ID " + status.getStatusId() + " not found."));

        existing.setStatusTitle(status.getStatusTitle());

        StatusData saved = statusRepository.save(existing);

        return StatusMapper.toModel(saved);
    }
}
