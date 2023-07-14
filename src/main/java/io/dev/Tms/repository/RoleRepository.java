package io.dev.Tms.repository;

import io.dev.Tms.domain.Role;
import io.dev.Tms.domain.User;

import java.util.Collection;

public interface RoleRepository <T extends Role> {
    /* Basic CURD Operations */
    T create(T data);
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);

    /* More Complex Operations*/
    void addRoleToUser(Long userId, String roleName);

    Role getRoleByUserId(Long userId);
    Role getRoleByUSerEmail(String email);
    void updateUserRole(Long userId, String roleName);

}
