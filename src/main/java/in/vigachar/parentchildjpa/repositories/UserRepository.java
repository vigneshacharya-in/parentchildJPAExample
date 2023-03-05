package in.vigachar.parentchildjpa.repositories;

import in.vigachar.parentchildjpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}