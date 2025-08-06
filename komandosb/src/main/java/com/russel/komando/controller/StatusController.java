package com.russel.komando.controller;

import com.russel.komando.model.Status;
import com.russel.komando.model.Task;
import com.russel.komando.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/statuses")
@RestController
public class StatusController {
    @Autowired
    private StatusService statusService;
    //========================================================================================================//
    @GetMapping
    public List<Status> getAllStatuses() {
        return statusService.fetchAllStatuses();
    }
    //========================================================================================================//
    @GetMapping("/{id}")
    public Status getStatus(@PathVariable Integer id) {
        return statusService.fetchStatus(id);
    }
    //========================================================================================================//
    @GetMapping("/title/{title}")
    public Status getStatusByTitle(@PathVariable String title) {
        return statusService.fetchStatusByTitle(title);
    }
    //========================================================================================================//
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Status createStatus(@RequestBody Status status) {
        return statusService.create(status);
    }
    //========================================================================================================//
    @PutMapping("/{id}")
    public Status updateStatus(@PathVariable Integer id, @RequestBody Status status) {
        status.setStatusId(id);
        return statusService.update(status);
    }
}
