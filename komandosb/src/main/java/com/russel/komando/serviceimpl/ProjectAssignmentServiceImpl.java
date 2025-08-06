package com.russel.komando.serviceimpl;

import com.russel.komando.entity.ProjectAssignmentData;
import com.russel.komando.entity.TaskAssignmentData;
import com.russel.komando.model.ProjectAssignment;
import com.russel.komando.model.TaskAssignment;
import com.russel.komando.repository.ProjectAssignmentRepository;
import com.russel.komando.service.ProjectAssignmentService;
import com.russel.komando.util.EmployeeMapper;
import com.russel.komando.util.ProjectMapper;
import com.russel.komando.util.TaskMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectAssignmentServiceImpl implements ProjectAssignmentService {

    @Autowired
    ProjectAssignmentRepository projectAssignmentRepository;
    //========================================================================================================//
    @Override
    public ProjectAssignment create(ProjectAssignment projectAssignment){
        ProjectAssignmentData entity = new ProjectAssignmentData();

        entity.setProject(ProjectMapper.toEntity(projectAssignment.getProject()));
        entity.setEmployee(EmployeeMapper.toEntity(projectAssignment.getEmployee()));

        ProjectAssignmentData saved = projectAssignmentRepository.save(entity);

        ProjectAssignment result = new ProjectAssignment();
        result.setProject(ProjectMapper.toModel(saved.getProject()));
        result.setEmployee(EmployeeMapper.toModel(saved.getEmployee()));

        return result;
    }
    //========================================================================================================//
    @Override
    @Transactional
    public void deleteByProject(Integer projectId){
        projectAssignmentRepository.deleteByProject_ProjectId(projectId);
    }
    //========================================================================================================//
    @Override
    @Transactional
    public void deleteByEmployee(Integer employeeId){
        projectAssignmentRepository.deleteByEmployee_EmployeeId(employeeId);
    }

}
