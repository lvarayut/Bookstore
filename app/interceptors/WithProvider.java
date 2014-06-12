package interceptors;


import securesocial.core.java.Authorization;
import securesocial.core.Identity;

public class WithProvider implements Authorization {

    @Override
    public boolean isAuthorized(Identity identity, String[] params) {
        return true;
        //return identity.identityId().providerId().equals(params[0]);
    }
}
