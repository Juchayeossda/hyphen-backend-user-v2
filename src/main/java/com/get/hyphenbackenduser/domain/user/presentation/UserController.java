package com.get.hyphenbackenduser.domain.user.presentation;

import com.get.hyphenbackenduser.domain.user.domain.User;
import com.get.hyphenbackenduser.domain.user.presentation.dto.request.RenameRequest;
import com.get.hyphenbackenduser.domain.user.presentation.dto.request.ChangePasswordRequest;
import com.get.hyphenbackenduser.domain.user.service.UserService;
import com.get.hyphenbackenduser.global.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.get.hyphenbackenduser.global.statics.response.ResponseMessageConstants.SUCCESSFUL_OK;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/image")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<String> getImage() {
        return ResponseData.of(HttpStatus.OK.value(), SUCCESSFUL_OK, userService.getImage());
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<User> getInfo() {
        return ResponseData.of(HttpStatus.OK.value(), SUCCESSFUL_OK, userService.getInfo());
    }

    @PatchMapping("/name")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<Object> rename(@Valid @RequestBody RenameRequest request) {
        userService.rename(request);
        return ResponseData.of(HttpStatus.OK.value(), SUCCESSFUL_OK);
    }

    @PatchMapping("/image")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<Object> reimage(@RequestParam("multipart-file-image") MultipartFile image) {
        userService.reimage(image);
        return ResponseData.of(HttpStatus.OK.value(), SUCCESSFUL_OK);
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<Object> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return ResponseData.of(HttpStatus.OK.value(), SUCCESSFUL_OK);
    }

    @DeleteMapping("/image")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<Object> deleteeImage() {
        userService.deleteImage();
        return ResponseData.of(HttpStatus.OK.value(), SUCCESSFUL_OK);
    }
}
