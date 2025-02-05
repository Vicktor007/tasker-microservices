package com.vic.tasker.Services;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.vic.tasker.dtos.ImageResponse;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleUploadService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = getPathToGoogleCredentials();

    private static String getPathToGoogleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "cred.json");
        String fullPath = filePath.toAbsolutePath().toString();
        System.out.println("Resolved path to credentials: " + fullPath);
        return fullPath;
    }


    public ImageResponse uploadImageToDrive(File file) {
       ImageResponse response = new ImageResponse();

        try {
            String folderId = "1YkFbEyexvIOkrNR9WxNmiOYqJu808xk1";

            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));

            String mimeType = Files.probeContentType(file.toPath());
            FileContent mediaContent = new FileContent(mimeType,file);

            com.google.api.services.drive.model.File uploadedFile =
                    drive.files().create(fileMetaData,mediaContent)
                            .setFields("id").execute();

            String imageUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
           response.setUrl(imageUrl);
           response.setId(uploadedFile.getId());
           response.setMessage("upload success");
            file.delete();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }
        return response;
   }

   public void deleteImageFromGoogleDrive(String uploadId) throws GeneralSecurityException, IOException {
        ImageResponse response = new ImageResponse();
        try{
            Drive drive = createDriveService();
            drive.files().delete(uploadId).execute();
            response.setStatus(200);
            response.setMessage("Image Successfully Deleted From Drive");
        } catch ( IOException e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }
   }

    private Drive createDriveService()
            throws GeneralSecurityException, IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("GoogleUpload") .build(); }

//    private Drive createDriveService() throws GeneralSecurityException, IOException {
//        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
//                .createScoped(Collections.singleton(DriveScopes.DRIVE));
//
//        return new Drive.Builder(
//                GoogleNetHttpTransport.newTrustedTransport(),
//                JSON_FACTORY,
//                credential)
//                .setApplicationName("GoogleUpload")
//                .build();
//    }
}
