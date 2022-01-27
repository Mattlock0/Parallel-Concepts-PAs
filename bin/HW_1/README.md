# Compiling and Running
To compile the program, type the following into the command prompt: (while in this directory)

`javac assignment1.java`

And to run, use the following command:

`java assignment1`

## Proof of Correctness and Efficiency
In doing this project I messed around with a few different methods. I had a "brute force" method
which simply had each thread calculate if the number they were on was prime. And while this worked, 
it was far too slow. So I did some research and found out about the Sieve of Eratosthenes.

This was far more efficient and, when implementing it into my program, I found it able to calculate
10^8 primes in a fraction of the time. According to the sources I checked, the total number of primes
between 1 and 10^8 is 5,761,455. The program, when complete, outputs the same.

The Sieve of Eratosthenes works by starting at 2 and marking off all multiples of 2 from the list of
numbers up to 10^8. So it begins by marking off 2*2, 2*3, 2*4, etc. until it reaches the end. Then,
it moves on to the next *non-marked* number in the list (being 3) and repeats the process. Once there
are no more unmarked numbers greater than the current, it stops.

The final results are all accurate as according to other sources on prime numbers.
