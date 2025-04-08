package androidapp.repository;

import androidapp.entity.UserAddressEntity;
import androidapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddressEntity,Integer> {
   List<UserAddressEntity> findUserAddressEntityByUser(UserEntity user);
    @Query("SELECT uae FROM UserAddressEntity uae WHERE uae.user = :user AND uae.latitude = :latitude AND uae.longitude = :longitude")
    Optional<UserAddressEntity> findUserAddressEntityByUserAndCoordinates(
            @Param("user") UserEntity user,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude
    );
}
