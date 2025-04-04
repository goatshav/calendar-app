package com.project.calendarapp.repositories;

import com.project.calendarapp.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
}
