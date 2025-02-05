package com.vic.tasker.Services.Interfaces;


import com.vic.tasker.model.Submission;

import java.util.List;

public interface SubmissionInterface {
    Submission submitTask(Long taskId, String githubLink, String userId, String jwt) throws Exception;

    Submission getTaskSubmissionById(Long submissionId) throws Exception;

    List<Submission> getAllTaskSubmissions();

    List<Submission> getTaskSubmissionsByTaskId(Long taskId);

    Submission acceptDeclineSubmission(Long id,String status) throws Exception;

}
