package com.vic.tasker.Controller;

import com.vic.tasker.Services.GoogleUploadService;
import com.vic.tasker.Services.Interfaces.TaskInterface;
import com.vic.tasker.Services.Interfaces.UserInterface;
import com.vic.tasker.dtos.ImageResponse;
import com.vic.tasker.dtos.UserDto;
import com.vic.tasker.enums.TaskStatus;
import com.vic.tasker.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskInterface taskInterface;

    @Autowired
    private UserInterface userInterface;

    @Autowired
    GoogleUploadService googleUploadService;

    @PostMapping
    public ResponseEntity<Task> createTask(
           @RequestBody Task task,
            @RequestHeader("Authorization") String jwt) {

        try {
            UserDto user = userInterface.getUserProfile(jwt);
            Task createdTask = taskInterface.createTask(task, user.getRole());
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle the exception appropriately
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @PostMapping
//    private ResponseEntity<Task> createTask(@RequestParam(value = "title", required = false) String title,
//                                            @RequestParam(value = "description", required = false) String description,
//                                            @RequestParam(value = "tags", required = false)  List<String> tags,
//                                            @RequestParam(value = "file", required = false) MultipartFile file,
//                                            @RequestParam(value = "deadline", required = false) LocalDateTime deadline,
//                                            @RequestHeader("Authorization") String jwt) throws Exception {
//
////        File file1 = convertMultipartFileToFile(file);
//        UserDto user = userInterface.getUserProfile(jwt);
//        Task createdTask = taskInterface.createTask(title,description,tags, deadline, file, user.getRole());
//        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
//    }



    @PostMapping("/googleUpload")
    public ResponseEntity<ImageResponse> uploadImageToGoogleDrive(@RequestParam("image") MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("temp", null);
//        file.transferTo(tempFile);
//        System.out.println("File received: " + tempFile.getAbsolutePath());
        File file1 = convertMultipartFileToFile(file);
        ImageResponse imageResponse = googleUploadService.uploadImageToDrive(file1);

        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }
    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        return file;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id,
                                            @RequestHeader("Authorization") String jwt) throws Exception {
        if(jwt==null){
            throw new Exception("jwt required...");
        }
        Task task = taskInterface.getTaskById(id);
        return task != null ? new ResponseEntity<>(task, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Task>> getAssignedUsersTask(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) String sortByDeadline,
            @RequestParam(required = false) String sortByCreatedAt) throws Exception {
        UserDto user=userInterface.getUserProfile(jwt);
        List<Task> tasks = taskInterface.assignedUsersTask(user.getId(),status, sortByDeadline, sortByCreatedAt);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) String sortByDeadline,
            @RequestParam(required = false) String sortByCreatedAt
    ) throws Exception {
        if(jwt==null){
            throw new Exception("jwt required...");
        }
        List<Task> tasks = taskInterface.getAllTasks(status, sortByDeadline, sortByCreatedAt);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}/user/{userId}/assigned")
    public ResponseEntity<Task> assignedTaskToUser(
            @PathVariable Long id,
            @PathVariable String userId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDto user=userInterface.getUserProfile(jwt);
        Task task = taskInterface.assignToUser(userId,id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        if(jwt==null){
            throw new Exception("jwt required...");
        }
        UserDto user=userInterface.getUserProfile(jwt);
        Task task = taskInterface.updateTask(id, req, user.getId());
        return task != null ? new ResponseEntity<>(task, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) throws Exception {
        taskInterface.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) throws Exception {
        Task task = taskInterface.completeTask(id);
        return new ResponseEntity<>(task, HttpStatus.NO_CONTENT);
    }
}
