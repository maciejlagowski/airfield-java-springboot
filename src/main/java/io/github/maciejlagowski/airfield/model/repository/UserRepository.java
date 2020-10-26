package io.github.maciejlagowski.airfield.model.repository;

import io.github.maciejlagowski.airfield.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
