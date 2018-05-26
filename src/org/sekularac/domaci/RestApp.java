package org.sekularac.domaci;

import org.sekularac.domaci.contollers.ControllerAccounts;
import org.sekularac.domaci.contollers.ControllerTweets;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ControllerTweets.class);
        classes.add(ControllerAccounts.class);
        return classes;
    }
}
