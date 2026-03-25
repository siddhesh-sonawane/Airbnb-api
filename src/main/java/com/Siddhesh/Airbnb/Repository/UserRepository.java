package com.Siddhesh.Airbnb.Repository;

import com.Siddhesh.Airbnb.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
