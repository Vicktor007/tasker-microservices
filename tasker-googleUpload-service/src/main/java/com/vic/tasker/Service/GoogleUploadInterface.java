package com.vic.tasker.Service;

import com.vic.tasker.Dto.ImageResponse;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleUploadInterface {
    ImageResponse uploadImageToDrive(File file);

    void deleteImageFromGoogleDrive(String uploadId) throws GeneralSecurityException, IOException;
}
