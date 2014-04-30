package service;

import models.User;
import play.Application;

import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.service.UserServicePlugin;

public class MyUserServicePlugin extends UserServicePlugin {

    public MyUserServicePlugin(final Application app) {
        super(app);
    }

    @Override
    public Object save(final AuthUser authUser) {

            return null;

    }

    @Override
    public Object getLocalIdentity(final AuthUserIdentity identity) {

            return null;

    }

    @Override
    public AuthUser merge(final AuthUser newUser, final AuthUser oldUser) {

        return oldUser;
    }

    @Override
    public AuthUser link(final AuthUser oldUser, final AuthUser newUser) {
       ;
        return null;
    }

}