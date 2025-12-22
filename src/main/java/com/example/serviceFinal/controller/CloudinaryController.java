// package com.example.serviceFinal.controller;

// import com.example.serviceFinal.service.CloudinaryService;
// import java.io.IOException;
// import java.util.Map;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// // import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// @RestController
// @RequestMapping("/api/cloudinary")
// public class CloudinaryController {

//   @Autowired
//   private CloudinaryService cloudinaryService;

//   @PostMapping("/upload")
//   public ResponseEntity<?> uploadImage(
//     @RequestParam("image") MultipartFile file
//   ) {
//     try {
//       String imageUrl = cloudinaryService.uploadFile(file);
//       return ResponseEntity.ok(
//         Map.of("url", imageUrl, "message", "Image uploaded successfully")
//       );
//     } catch (IOException e) {
//       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//         "Upload failed: " + e.getMessage()
//       );
//     }
//   }

//   @PostMapping("/upload-profile")
//   public ResponseEntity<?> uploadProfileImage(
//     @RequestParam("image") MultipartFile file
//   ) {
//     try {
//       // Upload to specific folder
//       String imageUrl = cloudinaryService.uploadFile(file, "profiles");
//       return ResponseEntity.ok(Map.of("profileImageUrl", imageUrl));
//     } catch (Exception e) {
//       return ResponseEntity.badRequest().body("Upload failed");
//     }
//   }
// }
