package com.depromeet.articlereminder.service;

public interface TokenService<T> {
    String create(T value);

    T decode(String accessToken);
}
