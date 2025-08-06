-- Create the database
DROP DATABASE IF EXISTS komando_db;
CREATE DATABASE IF NOT EXISTS komando_db;
USE komando_db;

-- Employee table
CREATE TABLE Employee (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    employee_title VARCHAR(255)
);

-- Status table
CREATE TABLE Status (
    status_id INT AUTO_INCREMENT PRIMARY KEY,
    status_title VARCHAR(255) NOT NULL UNIQUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Project table
CREATE TABLE Project (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(255),
    project_start_date DATE,
    project_due_date DATE,
    status_id INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_project_status
        FOREIGN KEY (status_id) REFERENCES Status(status_id)
);

-- Task table
CREATE TABLE Task (
    task_id INT AUTO_INCREMENT PRIMARY KEY,
    task_name VARCHAR(255),
    description TEXT,
    status_id INT,
    priority ENUM('LOW', 'MEDIUM', 'HIGH'),
    task_due_date DATE,
    project_id INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_task_status
        FOREIGN KEY (status_id) REFERENCES Status(status_id),
    CONSTRAINT fk_task_project
        FOREIGN KEY (project_id) REFERENCES Project(project_id)
        ON DELETE CASCADE
);

-- TaskAssignment table
CREATE TABLE TaskAssignment (
    ta_id INT AUTO_INCREMENT PRIMARY KEY,
    task_id INT,
    employee_id INT,
    CONSTRAINT fk_ta_task
        FOREIGN KEY (task_id) REFERENCES Task(task_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_ta_employee
        FOREIGN KEY (employee_id) REFERENCES Employee(employee_id)
        ON DELETE CASCADE
);

-- ProjectAssignment table
CREATE TABLE ProjectAssignment (
    pa_id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT,
    employee_id INT,
    CONSTRAINT fk_pa_project
        FOREIGN KEY (project_id) REFERENCES Project(project_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_pa_employee
        FOREIGN KEY (employee_id) REFERENCES Employee(employee_id)
        ON DELETE CASCADE
);


-- Insert Employees
INSERT INTO Employee (first_name, last_name, employee_title) VALUES
('Alice', 'Smith', 'Developer'),
('Bob', 'Johnson', 'Designer'),
('Charlie', 'Williams', 'Project Manager'),
('Diana', 'Brown', 'QA Engineer'),
('Edward', 'Jones', 'DevOps'),
('Fiona', 'Garcia', 'Developer'),
('George', 'Miller', 'Designer'),
('Hannah', 'Davis', 'QA Engineer'),
('Ian', 'Wilson', 'Developer'),
('Julia', 'Martinez', 'Business Analyst');

-- Insert Statuses
INSERT INTO Status (status_title) VALUES
('Not Started'),
('In Progress'),
('Completed');

-- Insert Projects
INSERT INTO Project (project_name, project_start_date, project_due_date, status_id) VALUES
('Website Redesign', '2025-07-01', '2025-09-30', 2),
('Mobile App Development', '2025-07-01', '2025-12-31', 1),
('Cloud Migration', '2025-07-01', '2026-03-31', 2);

-- Insert Tasks for Project 1 (Website Redesign)
INSERT INTO Task (task_name, description, status_id, priority, task_due_date, project_id) VALUES
('Design Homepage', 'Create new homepage mockups.', 1, 'HIGH', '2025-07-15', 1),
('Develop Homepage', 'Implement homepage HTML/CSS.', 2, 'HIGH', '2025-07-31', 1),
('Design About Page', 'Design the About Us page.', 1, 'MEDIUM', '2025-08-05', 1),
('Develop About Page', 'Implement About Us page.', 1, 'MEDIUM', '2025-08-20', 1),
('Setup CI/CD', 'Configure pipelines.', 2, 'HIGH', '2025-08-10', 1),
('Write Test Cases', 'Write tests for redesign.', 1, 'MEDIUM', '2025-08-15', 1),
('Deploy to Staging', 'Deploy the site.', 1, 'HIGH', '2025-08-25', 1),
('Review SEO', 'Optimize SEO.', 1, 'LOW', '2025-08-30', 1),
('Finalize Content', 'Update site content.', 1, 'MEDIUM', '2025-09-10', 1),
('Launch Website', 'Go live.', 1, 'HIGH', '2025-09-30', 1);

-- Insert Tasks for Project 2 (Mobile App Development)
INSERT INTO Task (task_name, description, status_id, priority, task_due_date, project_id) VALUES
('Design App UI', 'Create app designs.', 1, 'HIGH', '2025-08-01', 2),
('Develop Login Feature', 'Implement login.', 1, 'HIGH', '2025-08-15', 2),
('Develop Dashboard', 'Implement dashboard.', 1, 'MEDIUM', '2025-09-01', 2),
('Setup Backend API', 'Create REST API.', 1, 'HIGH', '2025-09-15', 2),
('Write Unit Tests', 'Unit testing.', 1, 'MEDIUM', '2025-09-20', 2),
('Integrate Payments', 'Payment gateway.', 1, 'HIGH', '2025-10-05', 2),
('Push Notifications', 'Configure notifications.', 1, 'MEDIUM', '2025-10-15', 2),
('Beta Testing', 'Invite beta users.', 1, 'LOW', '2025-11-01', 2),
('Bug Fixing', 'Fix reported bugs.', 1, 'MEDIUM', '2025-11-15', 2),
('App Release', 'Publish to stores.', 1, 'HIGH', '2025-12-31', 2);

-- Insert Tasks for Project 3 (Cloud Migration)
INSERT INTO Task (task_name, description, status_id, priority, task_due_date, project_id) VALUES
('Assess Infrastructure', 'Review current setup.', 2, 'HIGH', '2025-07-20', 3),
('Select Cloud Provider', 'Choose vendor.', 1, 'HIGH', '2025-07-31', 3),
('Setup Cloud Environment', 'Provision resources.', 1, 'HIGH', '2025-08-15', 3),
('Migrate Databases', 'Move databases.', 1, 'HIGH', '2025-09-01', 3),
('Migrate Applications', 'Move apps.', 1, 'HIGH', '2025-09-15', 3),
('Update Configurations', 'Update settings.', 1, 'MEDIUM', '2025-10-01', 3),
('Performance Testing', 'Test performance.', 1, 'MEDIUM', '2025-11-01', 3),
('Disaster Recovery Setup', 'Setup DR.', 1, 'HIGH', '2025-11-15', 3),
('Training Team', 'Train staff.', 1, 'LOW', '2025-12-01', 3),
('Finalize Migration', 'Close migration.', 1, 'HIGH', '2026-03-31', 3);

-- Assign Employees to Projects
INSERT INTO ProjectAssignment (project_id, employee_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(3, 9),
(3, 10);

-- Assign Employees to Tasks (each user gets at least one task)
INSERT INTO TaskAssignment (task_id, employee_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 1),
(6, 2),
(7, 3),
(8, 4),
(9, 5),
(10, 6),
(11, 5),
(12, 6),
(13, 7),
(14, 8),
(15, 5),
(16, 6),
(17, 7),
(18, 8),
(19, 9),
(20, 10),
(21, 9),
(22, 10),
(23, 9),
(24, 10),
(25, 9),
(26, 10),
(27, 9),
(28, 10),
(29, 9),
(30, 10);
