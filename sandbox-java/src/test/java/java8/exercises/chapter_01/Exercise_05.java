package java8.exercises.chapter_01;

import static java8.exercises.chapter_01.Exercise_05.RunnableEx.uncheck;

import org.junit.Test;

public class Exercise_05 {

    public interface RunnableEx {

        static Runnable uncheck(RunnableEx runnableEx) {
            return () -> {
                try {
                    runnableEx.run();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            };
        }

        void run() throws Exception;
    }

    @Test
    public void testName() throws Exception {
        new Thread(uncheck(() -> {
            System.out.println("Zzzzz");
            Thread.sleep(2000);
        })).start();
    }
}
