package uk.me.vucko.stefan.srv1.cassdb.repo;

import java.util.UUID;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uk.me.vucko.stefan.srv1.cassdb.tables.Role;

public interface RoleRepo extends CrudRepository<Role, UUID> {

	@Query("select * from stefan_srv1.Role where roleId=?0")
    public Role fetchByRoleId(UUID roleId);

}