package com.cts.elearn.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.elearn.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByContactNumber(String contactNumber);
    
    Optional<User> findByContactNumberAndStatus(String contactNumber, User.Status status);
    
    Optional<User> findByUserId(Integer userId);

}
