package space.bielsolososdev.rdl.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import space.bielsolososdev.rdl.domain.users.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
