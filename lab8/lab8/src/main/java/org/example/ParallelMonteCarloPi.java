package org.example;

import java.util.concurrent.*;
import java.util.Random;
import java.util.Locale;
import java.text.NumberFormat;

public class ParallelMonteCarloPi {
    private static final long ITERATIONS_PER_THREAD = 250_000_000L;

    static class MonteCarloTask implements Callable<Long> {
        private final long iterations;
        private final long seed;

        public MonteCarloTask(long iterations, long seed) {
            this.iterations = iterations;
            this.seed = seed;
        }

        @Override
        public Long call() {
            Random random = new Random(seed);
            long insideCircle = 0;

            for (long i = 0; i < iterations; i++) {
                double x = random.nextDouble();
                double y = random.nextDouble();

                // Перевіряємо чи точка всередині чверті кола
                if (x * x + y * y <= 1.0) {
                    insideCircle++;
                }
            }

            return insideCircle;
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java ParallelMonteCarloPi <number_of_threads>");
            System.exit(1);
        }

        int numThreads = Integer.parseInt(args[0]);
        long totalIterations = ITERATIONS_PER_THREAD * numThreads;

        // Створюємо пул потоків
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        long startTime = System.nanoTime();

        try {
            // Створюємо завдання для кожного потоку
            Future<Long>[] futures = new Future[numThreads];
            for (int i = 0; i < numThreads; i++) {
                // Використовуємо різні seed для кожного потоку
                futures[i] = executor.submit(
                        new MonteCarloTask(ITERATIONS_PER_THREAD, System.nanoTime() + i)
                );
            }

            // Збираємо результати
            long totalInsideCircle = 0;
            for (Future<Long> future : futures) {
                totalInsideCircle += future.get();
            }

            long endTime = System.nanoTime();
            double elapsedTime = (endTime - startTime) / 1_000_000.0;

            // Обчислюємо Пі
            double pi = 4.0 * totalInsideCircle / totalIterations;

            NumberFormat nf = NumberFormat.getInstance(Locale.US);
            nf.setMaximumFractionDigits(5);
            nf.setMinimumFractionDigits(5);
            nf.setGroupingUsed(false);

            NumberFormat intFormat = NumberFormat.getInstance(Locale.US);
            intFormat.setGroupingUsed(true);

            // Виводимо результати
            System.out.println("PI is " + nf.format(pi));
            System.out.println("THREADS " + numThreads);
            System.out.println("ITERATIONS " + intFormat.format(totalIterations));
            System.out.printf(Locale.US, "TIME %.2fms%n", elapsedTime);

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error during computation: " + e.getMessage());
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}