package lang;

import org.junit.Test;

public class Announcer_Test {

    @Test
    public void allowListenersAdditionWhileAnnouncing() throws Exception {
        Announcer<Runnable> announcer = new Announcer<>(Runnable.class);
        AlreadyAdded alreadyAdded = new AlreadyAdded(announcer);
        announcer.addListener(alreadyAdded);

        announcer.announce().run();
    }

    private static class AlreadyAdded implements Runnable {
        private final Announcer<Runnable> announcer;

        public AlreadyAdded(Announcer<Runnable> announcer) {
            this.announcer = announcer;
        }

        @Override
        public void run() {
            announcer.addListener(new Runnable() {
                @Override
                public void run() {
                    //nothing to do ...
                }
            });
        }
    }
}
