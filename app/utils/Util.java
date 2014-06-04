package utils;

import java.util.*;
import models.*;
import models.SecureSocial.*;
import securesocial.core.*;

public class Util{

	public static <E> List<E> iterableToList(Iterable<E> iter){
		List<E> list = new ArrayList<E>();
		for(E item : iter){
			list.add(item);
		}
		return list;
	}
	
	public static User transformIdentityToUser(Identity identity){
		User user = new User();
		Password password = new Password();
		user.setUserid(identity.identityId().userId());
		user.setProvider(identity.identityId().providerId());
		user.setFirstname(identity.firstName());
		user.setLastname(identity.lastName());
		user.setEmail(identity.email().get());
		user.setAuthmethod(identity.authMethod().method());
		if(identity.passwordInfo().nonEmpty()){
			password.setPasswordHasher(identity.passwordInfo().get().hasher());
			password.setPassword(identity.passwordInfo().get().password());
			if(identity.passwordInfo().get().salt().nonEmpty()){
				password.setSalt(identity.passwordInfo().get().salt().get());
			}
		}
		user.setPwd(password);
		user.setUsername(user.getFirstname());
		return user;
	}
}
