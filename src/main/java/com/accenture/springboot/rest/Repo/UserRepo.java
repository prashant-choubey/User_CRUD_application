package com.accenture.springboot.rest.Repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.accenture.springboot.rest.Models.User;

public interface UserRepo extends JpaRepository<User, Long> {

	
	List<User> findByFirstName(String firstName);
    @Transactional
	@Modifying   
	@Query(value="DELETE FROM User u WHERE u.id IN (:ids)",nativeQuery = true)
	public void deleteUser(@Param("ids")List<Long> ids);
	





}
