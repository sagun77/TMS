package io.dev.Tms.repository;

import io.dev.Tms.domain.User;

import java.util.Collection;

public interface UserRepository <T extends User> {
    /* Basic CURD Operations */
    T create(T data);
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);
}
