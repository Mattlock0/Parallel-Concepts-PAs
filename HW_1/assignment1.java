import java.lang.Math;
import java.time.Instant;
import java.util.Arrays;
import java.util.*;
import java.util.LinkedList;
import java.time.Duration;

class threadPrimer extends Thread {
    static Counter2 counter = new Counter2(2);          // starting the counter at 2
    static int val = (int) Math.pow(10, 8);
    static boolean prime[] = new boolean[val];
    
    public void run() {
        try {
            int p = counter.getAndIncrement();          // grabbing 2 and incrementing

            while (p*p < val) {                         // because by the time we hit p*p we will have marked everything
                if (prime[p]) {                         // our starting point is prime
                    for (int i = p*2; i < val; i += p) {
                        prime[i] = false;               // we mark every multiple up to val with false
                    }
                }
                p = counter.getAndIncrement();          // and increment again
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION CAUGHT");
            e.printStackTrace();
        }
    }
}

public class assignment1 {
    public static void main(String[] args) throws Exception {
        int threads = 8;

        Instant start = Instant.now();                  // starting the timer
        Arrays.fill(threadPrimer.prime, true);          // filling our boolean sieve array with trues
        threadPrimer[] obj = new threadPrimer[threads]; // and creating the appropriate number of threads

        for (int i = 0; i < threads; i++) {
            obj[i] = new threadPrimer();
            obj[i].start();                             // starting each thread
        }

        try {
            for (int i = 0; i < threads; i++) {
                obj[i].join();                          // joining them to make sure each is done
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION CAUGHT");
            e.printStackTrace();
        }

        List<Integer> primeNumbers = new LinkedList<>();
        int numPrimes = 0;
        long totalPrime = 0;
        
        for (int i = 2; i < threadPrimer.val; i++) {
            if (threadPrimer.prime[i]) {                // adding up and filtering prime numbers
                primeNumbers.add(i);                    // (still within the time limit)
                numPrimes++;
                totalPrime += i;
            }
        }

        Instant finish = Instant.now();                 // stopping the timer and converting it to milliseconds
        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("The execution took " + timeElapsed + "ms. and found " + numPrimes + " prime numbers.");
        System.out.println("In total, the primes were added up to " + totalPrime + ".");
        System.out.print("The highest ten primes found were "); // print out

        int[] setOfMaxPrimes = new int[10];
        
        for(int i = numPrimes-10; i < numPrimes; i++) { // grabbing only the last 10
            int prime = primeNumbers.get(i);
            
            if (prime > setOfMaxPrimes[0]) {            // compare it to the smallest value
                setOfMaxPrimes[0] = prime;              // if it's larger set it there
                Arrays.sort(setOfMaxPrimes);            //and sort the values accordingly
            }
        }

        System.out.println(Arrays.toString(setOfMaxPrimes));
    }
}