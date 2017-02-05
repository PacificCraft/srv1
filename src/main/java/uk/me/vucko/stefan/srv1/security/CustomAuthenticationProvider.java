package uk.me.vucko.stefan.srv1.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;

@Configuration
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	Srv1UserDetailsService userDetailsService;

	private String hashPassword (String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] salt = md.digest(username.getBytes());
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = f.generateSecret(spec).getEncoded();
		Base64.Encoder enc = Base64.getEncoder();
		String pwdHash = enc.encodeToString(hash);
		return pwdHash;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		UserDetails userDetails = null;
	    UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
	    String username = String.valueOf(auth.getPrincipal());
	    String password = String.valueOf(auth.getCredentials());

	    logger.info("username:" + username);
	    logger.info("password:" + password);

	    try {
		    userDetails = userDetailsService.loadUserByUsername(username);
		    String pwdHash = hashPassword(username, password);
		    if (!pwdHash.equals(userDetails.getPassword())) {
		    	logger.info("Hash was {} and in the database it was {}", pwdHash, userDetails.getPassword());
		    	throw new BadCredentialsException("Invalid username or password");
		    }
	    } catch (UsernameNotFoundException ex) {
	    	throw new BadCredentialsException("Invalid username or password");
	    } catch (NoSuchAlgorithmException e) {
	    	throw new BadCredentialsException("Invalid username or password");
		} catch (InvalidKeySpecException e) {
	    	throw new BadCredentialsException("Invalid username or password");
		}

	    return  new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class aClass) {
	    return true;  //To indicate that this authenticationprovider can handle the auth request. since there's currently only one way of logging in, always return true
	}
}