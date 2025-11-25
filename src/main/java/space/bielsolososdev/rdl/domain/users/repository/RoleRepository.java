package space.bielsolososdev.rdl.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import space.bielsolososdev.rdl.domain.users.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
