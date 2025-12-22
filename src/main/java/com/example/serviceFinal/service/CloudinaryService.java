package com.example.serviceFinal.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudinaryService {

  @Autowired
  private Cloudinary cloudinary;

  public String uploadFile(MultipartFile file, String folder)
    throws IOException {
    File uploadedFile = convertMultiPartToFile(file);
    Map options = ObjectUtils.asMap("folder", folder);
    Map uploadResult = cloudinary.uploader().upload(uploadedFile, options);
    uploadedFile.delete();
    return uploadResult.get("url").toString();
  }

  private File convertMultiPartToFile(MultipartFile file) throws IOException {
    File convFile = new File(file.getOriginalFilename());
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }
}
