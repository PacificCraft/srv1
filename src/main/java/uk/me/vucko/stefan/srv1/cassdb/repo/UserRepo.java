package uk.me.vucko.stefan.srv1.cassdb.repo;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uk.me.vucko.stefan.srv1.cassdb.tables.User;

public interface UserRepo extends CrudRepository<User, String> {

	@Query("select * from ips_server.User where userId=?0")
    public User fetchByUserId(String userId);
}