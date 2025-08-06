package com.russel.komando.serviceimpl;

import com.russel.komando.entity.TaskAssignmentData;
import com.russel.komando.model.TaskAssignment;
import com.russel.komando.repository.TaskAssignmentRepository;
import com.russel.komando.service.TaskAssignmentService;
import com.russel.komando.util.EmployeeMapper;
import com.russel.komando.util.TaskMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskAssignmentServiceImpl implements TaskAssignmentService {
    @Autowired
    TaskAssignmentRepository taskAssignmentRepository;
    //========================================================================================================//
    @Override
    public TaskAssignment create(TaskAssignment taskAssignment){
        TaskAssignmentData entity = new TaskAssignmentData();

        entity.setTask(TaskMapper.toEntity(taskAssignment.getTask()));
        entity.setEmployee(EmployeeMapper.toEntity(taskAssignment.getEmployee()));

        TaskAssignmentData saved = taskAssignmentRepository.save(entity);

        TaskAssignment result = new TaskAssignment();
        result.setTask(TaskMapper.toModel(saved.getTask()));
        result.setEmployee(EmployeeMapper.toModel(saved.getEmployee()));

        return result;
    }
    //========================================================================================================//
    @Override
    @Transactional
    public void deleteByTask(Integer taskId){
        taskAssignmentRepository.deleteByTask_TaskId(taskId);
    }
    //========================================================================================================//
    @Override
    @Transactional
    public void deleteByEmployee(Integer employeeId){
        taskAssignmentRepository.deleteByEmployee_EmployeeId(employeeId);
    }
}
