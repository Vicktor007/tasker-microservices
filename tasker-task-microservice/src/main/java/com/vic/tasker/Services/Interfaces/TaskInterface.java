package com.vic.tasker.Services.Interfaces;

import com.vic.tasker.enums.TaskStatus;
import com.vic.tasker.model.Task;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskInterface {

//    Task createTask(String title, String description, List<String> tags, LocalDateTime deadline, MultipartFile file, String requestUserRole) throws Exception;

    Task createTask(Task task, String requestUserRole) throws Exception;

    Task getTaskById(Long id) throws Exception;

    List<Task> getAllTasks(TaskStatus status, String sortByDeadline, String sortByCreatedAt);

    Task updateTask(Long id, Task updatedTask, String userId) throws Exception;

    void deleteTask(Long id) throws Exception;

    Task assignToUser(String userId,Long taskId) throws Exception;

    List<Task> assignedUsersTask(String userId,TaskStatus status, String sortByDeadline, String sortByCreatedAt);

    Task completeTask(Long taskId) throws Exception;

}
