# Compiling and Running
To compile the program, type the following into the command prompt: (while in this directory)

`javac assignment2.java`

And to run, use the following command:

`java assignment2`

## Implementation (Problem 1)
For this problem, I used a similar method to the prisoner problem outlined in class.
At the beginning, the guests choose a leader. (This being guest[0].) Everyone who is
not a leader will never replace cupcakes. We will call non-leaders normies.

If a normie enters for the first time and sees a cupcake, they should eat it. From
that point on, they must not eat any cupcakes. But if it is empty, they should move
on. If they enter at a later time and *have not* eaten a cupcake, they should eat it
now. All guests must only eat a cupcake once.

The leader has a different job. They must keep track of how many empty plates they
come across. If the plate is empty, they know at least one new person has entered
the labyrinth and their "counter" goes up. They then must replace the cupcake. Once
the counter has reached one less than the total amount of guests (aka not including
them) they can signal the Minotaur that every guest has entered.

## Implementation (Problem 2)
