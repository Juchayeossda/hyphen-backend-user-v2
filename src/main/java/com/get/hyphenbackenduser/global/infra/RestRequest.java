package com.get.hyphenbackenduser.global.infra;

import com.get.hyphenbackenduser.global.config.webClient.WebClientConfig;
import com.get.hyphenbackenduser.global.exception.global.ExternalAPIException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;

@Component
@RequiredArgsConstructor
public class RestRequest {

    private final WebClientConfig webClientConfig;

    public <T> T postImage(String url, Class<T> responseClass, MultipartBodyBuilder imageBuilder) {
        ResponseEntity<T> response = webClientConfig.webClient().method(HttpMethod.POST)
                .uri(url)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(imageBuilder.build()))
                .retrieve()
                .toEntity(responseClass)
                .block();
        if (response == null || response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            throw ExternalAPIException.EXCEPTION;
        }
        return response.getBody();
    }

    public <T> T get(String url, Class<T> responseClass) {
        ResponseEntity<T> response = webClientConfig.webClient().method(HttpMethod.GET)
                .uri(url)
                .retrieve()
                .toEntity(responseClass)
                .block();
        if (response == null || response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            throw ExternalAPIException.EXCEPTION;
        }
        return response.getBody();
    }
}
