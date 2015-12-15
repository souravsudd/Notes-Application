package com.gotprint.services.notes.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gotprint.services.notes.domain.User;

public interface UsersRepository extends CrudRepository<User, Long>
{
    @Query(value="Select userId from User where email = :email")
    public Long getUserId(@Param("email") String email);
}
