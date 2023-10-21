package com.get.hyphenbackenduser.global.lib.webflux.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
public class ImageUploadResponse {

    private int code;
    private String message;
    private Data data;
}