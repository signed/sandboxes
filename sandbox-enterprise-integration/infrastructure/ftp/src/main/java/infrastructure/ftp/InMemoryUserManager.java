package infrastructure.ftp;

import org.apache.ftpserver.ftplet.Authentication;
import org.apache.ftpserver.ftplet.AuthenticationFailedException;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.usermanager.impl.AbstractUserManager;

import java.util.HashMap;
import java.util.Map;

class InMemoryUserManager extends AbstractUserManager {

    private final Map<String, User> users = new HashMap<String, User>();

    public InMemoryUserManager() {
        super("deep-dark-secret", new ClearTextPasswordEncryptor());
    }

    @Override
    public User getUserByName(String name) throws FtpException {
        return users.get(name);
    }

    @Override
    public String[] getAllUserNames() throws FtpException {
        return users.keySet().toArray(new String[users.size()]);
    }

    @Override
    public void delete(String name) throws FtpException {
        users.remove(name);
    }

    @Override
    public void save(User user) throws FtpException {
        users.put(user.getName(), user);
    }

    @Override
    public boolean doesExist(String userName) throws FtpException {
        return users.containsKey(userName);
    }

    @Override
    public User authenticate(Authentication authentication) throws AuthenticationFailedException {
        if (authentication instanceof UsernamePasswordAuthentication) {
            UsernamePasswordAuthentication usernameAndPassword = (UsernamePasswordAuthentication) authentication;
            String username = usernameAndPassword.getUsername();

            User user = users.get(username);
            if (null == user) {
                throw new AuthenticationFailedException("unknown user '" + username + "'");
            }
            String password = usernameAndPassword.getPassword();
            if (getPasswordEncryptor().matches(password, user.getPassword())) {
                return user;
            } else {
                throw new AuthenticationFailedException("password wrong");
            }
        }
        throw new AuthenticationFailedException("try harder");
    }
}
