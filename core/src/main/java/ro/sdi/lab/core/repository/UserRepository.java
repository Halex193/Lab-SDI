package ro.sdi.lab.core.repository;

import org.springframework.data.jpa.repository.Query;

import ro.sdi.lab.core.model.User;

public interface UserRepository extends Repository<Long, User> {

    @Query("select u from User u where u.userName=?1")
    User getUserByUserName(String userName);
}
