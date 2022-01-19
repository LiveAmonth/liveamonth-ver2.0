package teamproject.lam_server.app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.user.domain.User;

import java.util.Optional;

import static teamproject.lam_server.constants.AttrConstants.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLoginId(String loginId);

    Optional<User> findUserByNameAndEmail(String name, String email);

    Optional<User> findUserByLoginIdAndEmail(String loginId, String email);

    Optional<User> findById(Long Id);

    Boolean existsByEmail(String email);

    Boolean existsByLoginId(String loginId);

    Boolean existsByNickname(String nickname);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.password = :temporaryPw where u.id=:#{#user.id}")
    Integer editPassword(@Param(USER) User user, @Param(TEMPORARY_PW) String temporaryPw);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.image = :image where u.id=:id")
    Integer editImage(@Param(ID) Long id, @Param(IMAGE) String image);

}