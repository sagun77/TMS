package io.dev.Tms.repository.implementation;

import io.dev.Tms.domain.Role;
import io.dev.Tms.exception.ApiException;
import io.dev.Tms.repository.RoleRepository;
import io.dev.Tms.rowmapper.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import static io.dev.Tms.enumeration.RoleType.ROLE_USER;
import static io.dev.Tms.query.RoleQuery.*;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository {
   private final NamedParameterJdbcTemplate jdbc;

   @Override
   public Role create(Role data) {
      return null;
   }

   @Override
   public Collection list(int page, int pageSize) {
      return null;
   }

   @Override
   public Role get(Long id) {
      return null;
   }

   @Override
   public Role update(Role data) {
      return null;
   }

   @Override
   public Boolean delete(Long id) {
      return null;
   }

   @Override
   public void addRoleToUser(Long userId, String roleName) {
      log.info("Adding role {} to user id: {}", roleName, userId);
      try{
         Role role = jdbc.queryForObject(SELECT_ROLE_NAME_QUERY, of("roleName",roleName), new RoleRowMapper());
         jdbc.update(INSERT_ROLE_TO_USER_QUERY, of("userId",userId,"roleId", requireNonNull(role).getId()));
      }catch(EmptyResultDataAccessException exception){
         throw new ApiException("No role found by name"+ ROLE_USER.name());

      }catch(Exception exception){
         throw new ApiException("An error occurred. Please try again.");
      }
   }



   @Override
   public Role getRoleByUserId(Long userId) {
      return null;
   }

   @Override
   public Role getRoleByUSerEmail(String email) {
      return null;
   }

   @Override
   public void updateUserRole(Long userId, String roleName) {

   }
}
