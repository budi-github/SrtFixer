package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.runners.Parameterized;
import org.junit.runners.model.RunnerScheduler;

/**
 * Runs parameterized tests in parallel.
 * 
 * @author budi
 * @author http://hwellmann.blogspot.com/2009/12/running-parameterized-junit-
 *         tests-in.html
 */
public class Parallelized extends Parameterized {

    /**
     * Private thread pool scheduler.
     * 
     * @author budi
     * @author http://hwellmann.blogspot.com/2009/12/running-parameterized-junit
     *         -tests-in.html
     */
    private static class ThreadPoolScheduler implements RunnerScheduler {

        /**
         * Number of threads.
         */
        private static final int THREADS = 2;

        /**
         * Executor service.
         */
        private ExecutorService executor;

        /**
         * Class constructor.
         */
        public ThreadPoolScheduler() {
            String threads = System.getProperty("junit.parallel.threads", Integer.toString(THREADS));
            int numThreads = Integer.parseInt(threads);
            executor = Executors.newFixedThreadPool(numThreads);
        }

        @Override
        public void finished() {
            executor.shutdown();
            try {
                executor.awaitTermination(10, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void schedule(Runnable childStatement) {
            executor.submit(childStatement);
        }
    }

    /**
     * Class constructor.
     * 
     * @param c class object
     * @throws Throwable
     */
    public Parallelized(Class<?> c) throws Throwable {
        super(c);
        setScheduler(new ThreadPoolScheduler());
    }
}
