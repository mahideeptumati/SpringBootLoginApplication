package com.springboot.login.rpository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {

	 UserDetails findByNameAndPassword(String userName, String passWord);

}
