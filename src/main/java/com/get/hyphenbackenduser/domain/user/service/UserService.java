package com.get.hyphenbackenduser.domain.user.service;

import com.get.hyphenbackenduser.domain.user.domain.User;
import com.get.hyphenbackenduser.domain.user.domain.repository.UserRepository;
import com.get.hyphenbackenduser.domain.user.exception.AlreadyNameExistsException;
import com.get.hyphenbackenduser.domain.user.exception.ImageNotFoundException;
import com.get.hyphenbackenduser.domain.user.presentation.dto.request.RenameRequest;
import com.get.hyphenbackenduser.domain.user.presentation.dto.request.ChangePasswordRequest;
import com.get.hyphenbackenduser.global.annotation.ServiceWithTransactionalReadOnly;
import com.get.hyphenbackenduser.global.infra.RestRequest;
import com.get.hyphenbackenduser.global.lib.security.service.SecurityService;
import com.get.hyphenbackenduser.global.lib.webflux.dto.response.ImageUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@ServiceWithTransactionalReadOnly
public class UserService {

    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final RestRequest webClient;
    private final PasswordEncoder passwordEncoder;

    @Value(value = "${webClient.servers.imageServer.path}")
    private String imageServerPath;

    public String getImage() {
        User user = securityService.getAuthUserInfo();
        return user.getImage();
    }

    public User getInfo() {
        return securityService.getAuthUserInfo();
    }

    public void rename(RenameRequest request) {
        User user = securityService.getAuthUserInfo();
        String newName = request.getNewName();
        if (userRepository.existsByName(newName)) {
            throw AlreadyNameExistsException.EXCEPTION;
        }
        user.updateUser(newName, null, null);
        userRepository.save(user);
    }

    public void reimage(MultipartFile image) {
        User user = securityService.getAuthUserInfo();
        if (image == null || image.isEmpty()) {
            throw ImageNotFoundException.EXCEPTION;
        }
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("multipart-file-image", image.getResource());
        ImageUploadResponse imageUploadResponse = webClient.postImage(String.format("%s%s", imageServerPath, "/api/siss/storages/images/image"), ImageUploadResponse.class, builder);
        if (imageUploadResponse.getCode() == 201) {
            user.updateUser(null, null, String.format("%s%s%s", imageServerPath, "/api/siss/storages/images/", imageUploadResponse.getData().getId()));
            userRepository.save(user);
        }
    }

    public void changePassword(ChangePasswordRequest request) {
        User user = securityService.getAuthUserInfo();
        user.updatePassword(passwordEncoder, request.getPreviousPassword(), request.getNewPassword());
        userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteImage() {
        User user = securityService.getAuthUserInfo();
        user.updateUser(null, null, null);
    }
}
