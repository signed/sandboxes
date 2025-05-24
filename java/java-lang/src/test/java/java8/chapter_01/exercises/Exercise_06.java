package java8.chapter_01.exercises;

import org.junit.jupiter.api.Test;

import static java8.chapter_01.exercises.Exercise_06.RunnableEx.uncheck;

class Exercise_06 {

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
    void testName() throws Exception {
        new Thread(uncheck(() -> {
            System.out.println("Zzzzz");
            Thread.sleep(2000);
        })).start();
    }
}
