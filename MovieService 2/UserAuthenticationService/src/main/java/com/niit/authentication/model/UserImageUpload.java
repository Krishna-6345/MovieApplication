package com.niit.authentication.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserImageUpload {
    private MultipartFile imageFile;
}
