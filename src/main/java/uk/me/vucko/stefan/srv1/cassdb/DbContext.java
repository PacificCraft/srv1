package uk.me.vucko.stefan.srv1.cassdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraOperations;

import uk.me.vucko.stefan.srv1.cassdb.repo.RoleRepo;
import uk.me.vucko.stefan.srv1.cassdb.repo.UserRepo;

@Configuration
public class DbContext {

	@Autowired
	private UserRepo userRepo;
    
	@Autowired
	private RoleRepo roleRepo;

    @Autowired
    private CassandraOperations cassandraOperations;

    public CassandraOperations getCassandraOperations() {
		return cassandraOperations;
	}

	public void setCassandraOperations(CassandraOperations cassandraOperations) {
		this.cassandraOperations = cassandraOperations;
	}

	public UserRepo getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	public RoleRepo getRoleRepo() {
		return roleRepo;
	}

	public void setRoleRepo(RoleRepo roleRepo) {
		this.roleRepo = roleRepo;
	}

}
