# Compiling and Running
To compile the program, type the following into the command prompt: (while in this directory)

`javac assignment3.java`

And to run, use the following command:

`java assignment3`

## Implementation (Problem 3)
For this, I used another LinkedList implementation. Each thread adds to the temperature readouts, grabbing them for a full hour. At the end of the hour, that mini-list is sorted and the proper elements are extracted.

### Efficiency and Evaluation
I ran this many times to assure its consistency, particularly with the sorted list elements. It took some trial and error to get the interval down, but in the end it attempts to read the last 10 temperatures before the highest one was recorded. If this intersects with the beginning of the list, it still grabs 10 total, adding up to 10 minutes.
