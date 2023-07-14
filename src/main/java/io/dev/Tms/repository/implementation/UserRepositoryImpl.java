package io.dev.Tms.repository.implementation;

import io.dev.Tms.domain.Role;
import io.dev.Tms.domain.User;
import io.dev.Tms.exception.ApiException;
import io.dev.Tms.repository.RoleRepository;
import io.dev.Tms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static io.dev.Tms.enumeration.RoleType.ROLE_USER;
import static io.dev.Tms.enumeration.VerificationType.ACCOUNT;
import static io.dev.Tms.query.UserQuery.*;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {

    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User create(User user) {
        //check the email is unique
        if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email already in use. Please use a different email and try again. ");
        //save new user
        try{
            KeyHolder holder =new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey()).longValue());
            //add role to the user
            roleRepository.addRoleToUser(user.getId(),ROLE_USER.name());
            //send verification URl
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            //save URL in verification URL
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("userId",user.getId(),"url", verificationUrl));
            //Send email to user with verificaton Url
            //emailService.sendVerificationUrl(user.getFirstname(),user.getEmail(),verificationUrl,ACCOUNT.getType());
            user.setEnabled(false);
            user.setNotLocked(true);
            //Return the newly crated user
            return user;
            // if any errors, throw exceptions with proper message
        }catch(Exception exception){
            throw new ApiException("An error occurred. Please try again.");
        }
    }
    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, of("email", email),Integer.class);
    }
    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstname",user.getFirstname())
                .addValue("lastname",user.getLastname())
                .addValue("email",user.getEmail())
                .addValue("password",encoder.encode(user.getPassword()));


    }

    private String getVerificationUrl(String key, String type){
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/"+ type+"/"+key).toUriString();

    }
}
