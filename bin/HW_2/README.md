# Compiling and Running
To compile the program, type the following into the command prompt: (while in this directory)

`javac assignment2.java`

And to run, use the following command:

`java assignment2`

## Implementation (Problem 1)
For this problem, I used a similar method to the prisoner problem outlined in class. At the beginning, the guests choose a leader. (This being guest[0].) Everyone who is not a leader will never replace cupcakes. We will call non-leaders normies.

If a normie enters for the first time and sees a cupcake, they should eat it. From that point on, they must not eat any cupcakes. But if it is empty, they should move on. If they enter at a later time and *have not* eaten a cupcake, they should eat it now. All guests must only eat a cupcake once.

The leader has a different job. They must keep track of how many empty plates they come across. If the plate is empty, they know at least one new person has entered the labyrinth and their "counter" goes up. They then must replace the cupcake. Once the counter has reached one less than the total amount of guests (aka not including them) they can signal the Minotaur that every guest has entered.

# Efficiency and Evaluation
Via the implementation made, this method required me to use a couple Thread.sleep() calls: one for the minotaur and one for the guests. The guests' had to be shorter as they need to continually check if they have been invited in, while the minotaur has much less to do and therefore has a longer timer.

I ran the implementation at least forty times, experimenting with the sleep() times and assuring the method works. The implementation only broke if I removed one of the sleep() calls.

## Implementation (Problem 2)
This one was very simple. I used the second strategy, one in which the guests would set a sign to "BUSY" if one of them was inside and "AVAILABLE" once they exit. To accomplish this, I forced each thread to use a lock(). This way, only one thread could access the vase at a time (in this case a print statement) and they would unlock it (or flip the sign) once they finished. All threads are then joined together at the end to assure they have all seen the vase before ending.

# Efficiency and Evaluation
I considered the other strategies, but the first sounded like a worse version of the second. They would actually have to *try* the door to see if it is locked rather than just looking at a sign. In programming, this would be while looping until one of them sees that the door is unlocked. This causes some serious havoc.

The third strategy, on the other hand, is good but requires much more organization. People could lose their number, lose their spot, and would not be free to look around the rest of the castle while doing so. If they were not in line, the earlier queue member would have to track them down.

With the second strategy, once someone is done with the room, they're just done! I could have implemented where they check the room several times, but this is quite simple without it. In the scenario, being able to glance over at a sign is pretty easy, so it works there as well.
