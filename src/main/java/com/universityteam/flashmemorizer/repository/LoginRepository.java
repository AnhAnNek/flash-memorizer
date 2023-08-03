package com.universityteam.flashmemorizer.repository;

import com.universityteam.flashmemorizer.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findByUsernameAndPassword(String username, String password);
}
