package androidapp.repository;

import androidapp.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {


    @Query("""
    select t from TokenEntity t inner join UserEntity u on t.user.id = u.id
    where t.user.id = :userId and t.loggedOut = false
    """)
    List<TokenEntity> findAllAccessTokensByUser(Integer userId);

    Optional<TokenEntity > findByRefreshToken(String token);
}
