package com.vic.tasker.Services.Implementation;

import com.vic.tasker.Repository.TaskRepository;
import com.vic.tasker.Services.GoogleUploadService;
import com.vic.tasker.Services.Interfaces.GoogleUploadInterface;
import com.vic.tasker.Services.Interfaces.TaskInterface;
import com.vic.tasker.dtos.ImageResponse;
import com.vic.tasker.enums.TaskStatus;
import com.vic.tasker.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements TaskInterface {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GoogleUploadService uploadService;

    @Autowired
    private GoogleUploadInterface googleUploadInterface;



    @Override
    public Task createTask(Task task, String requestUserRole) throws Exception {
        if (!requestUserRole.equals("ROLE_ADMIN")) {
            throw new Exception("Only admins can create tasks.");
        }


        try {
            task.setStatus(TaskStatus.PENDING);
            task.setCreatedAt(LocalDateTime.now());
        } catch (Exception e) {
            // Log the error or handle it as needed
            System.out.println("image upload issue");

        }

        return taskRepository.save(task);
    }

//
//    @Override
//    public Task createTask(String title, String description, List<String> tags, LocalDateTime deadline, MultipartFile file, String requestUserRole) throws Exception {
//        if (!requestUserRole.equals("ROLE_ADMIN")) {
//            throw new Exception("Only admins can create tasks.");
//        }
//        Task task = new Task();
//        try {
//            ImageResponse uploadedFile = googleUploadInterface.uploadImageToGoogleDrive(file);
//            task.setImage(uploadedFile.getUrl());
//            task.setImageId(uploadedFile.getId());
//        } catch (Exception e) {
//            // Log the error or handle it as needed
//            task.setImage(null);
//            task.setImageId(null);
//        }
//        task.setTitle(title);
//        task.setDescription(description);
//        task.setDeadline(deadline);
//        task.setTags(tags);
//        task.setStatus(TaskStatus.PENDING);
//        task.setCreatedAt(LocalDateTime.now());
//        return taskRepository.save(task);
//    }

//    @Override
//    public Task createTask(String title, String description, List<String> tags, LocalDateTime deadline, MultipartFile file, String requestUserRole) throws Exception {
//        if (!requestUserRole.equals("ROLE_ADMIN")) {
//            throw new Exception("Only admins can create tasks.");
//        }
//        Task task = new Task();
////       ImageResponse uploadedFile = uploadService.uploadImageToDrive(file);
//        ImageResponse uploadedFile = googleUploadInterface.uploadImageToGoogleDrive(file);
//        task.setImage(uploadedFile.getUrl());
//        task.setImageId(uploadedFile.getId());
//        task.setTitle(title);
//        task.setDescription(description);
//        task.setDeadline(deadline);
//        task.setTags(tags);
//        task.setStatus(TaskStatus.PENDING);
//        task.setCreatedAt(LocalDateTime.now());
//        return taskRepository.save(task);
//    }

    @Override
    public Task getTaskById(Long id) throws Exception {
        return taskRepository.findById(id).orElseThrow(()->new Exception(("task not found with id"+id)));
    }

    @Override
    public List<Task> getAllTasks(TaskStatus status, String sortByDeadline, String sortByCreatedAt) {
        List<Task> allTasks = taskRepository.findAll();

        List<Task> filteredTasks = allTasks.stream()
                .filter(task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString()))
                .collect(Collectors.toList());

        if (sortByDeadline != null && !sortByDeadline.isEmpty()) {
            filteredTasks.sort(Comparator.comparing(Task::getDeadline));
        } else if (sortByCreatedAt != null && !sortByCreatedAt.isEmpty()) {
            filteredTasks.sort(Comparator.comparing(Task::getCreatedAt));
        }

        return filteredTasks;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask, String userId) throws Exception {
        Task existingTask = getTaskById(id);

        if(updatedTask.getTitle()!=null){
            existingTask.setTitle(updatedTask.getTitle());
        }
        if(updatedTask.getImage()!=null){
            existingTask.setImage(updatedTask.getImage());
        }
        if (updatedTask.getDescription()!=null){
            existingTask.setDescription(updatedTask.getDescription());
        }
        if(updatedTask.getStatus()!=null){
            existingTask.setStatus(updatedTask.getStatus());
        }
        if(updatedTask.getDeadline()!=null){
            existingTask.setDeadline(updatedTask.getDeadline());
        }


        return taskRepository.save(existingTask);

    }

    @Override
    public void deleteTask(Long id) throws Exception {
        var task =taskRepository.findById(id).orElseThrow(()->new Exception(("task not found with id"+id)));

        String imageId = task.getImageId();

        uploadService.deleteImageFromGoogleDrive(imageId);
        taskRepository.deleteById(id);
    }

    @Override
    public Task assignToUser(String userId, Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.ASSIGNED);

        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUsersTask(String userId, TaskStatus status, String sortByDeadline, String sortByCreatedAt) {
        List<Task> allTasks = taskRepository.findByAssignedUserId(userId);


        List<Task> filteredTasks = allTasks.stream()
                .filter(task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString()))


                .collect(Collectors.toList());


        if (sortByDeadline != null && !sortByDeadline.isEmpty()) {
            filteredTasks.sort(Comparator.comparing(Task::getDeadline));
        } else if (sortByCreatedAt != null && !sortByCreatedAt.isEmpty()) {
            filteredTasks.sort(Comparator.comparing(Task::getCreatedAt));
        }

        return filteredTasks;

    }

    @Override
    public Task completeTask(Long taskId) throws Exception {
         Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}
