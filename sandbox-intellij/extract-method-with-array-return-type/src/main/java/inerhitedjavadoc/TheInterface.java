package inerhitedjavadoc;

public interface TheInterface {

    /**
     * Method does Stuff to you
     * @throws RuntimeException be nice or I will throw it.
     */
    void doStuff() throws RuntimeException;

    /**
     * Never again ...
     *
     * @throws Exception I don't trust you to threat me nice, so I force you to.
     */
    void doEvenMoreStuff() throws Exception;
}
