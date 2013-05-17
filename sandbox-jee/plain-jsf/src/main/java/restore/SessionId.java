package restore;

import java.io.Serializable;

public class SessionId implements Serializable{
    private final String id;

    public SessionId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}