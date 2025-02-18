package androidapp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import androidapp.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	Optional<UserEntity> findByEmail(String email);

	Optional<UserEntity>  findByUsername(String username);

	UserEntity findUsersByEmail(String email);

	UserEntity findUsersByUsername(String username);
}
