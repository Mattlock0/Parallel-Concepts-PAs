import java.util.Random;

class Guest extends Thread {
    static Cupcake cupcake = new Cupcake(true); // the cupcake starts as there
    public boolean leader = false;
    boolean invited = false;
    int eaten = 0;

    public static boolean finished = false;

    public Guest(boolean leader) {
        this.leader = leader;
    }

    public void enter() {
        invited = true;
    }

    public boolean inside() {
        return invited;
    }
    
    public void run() {  
        try {
            while(!finished) {
                while(!invited) {
                    Thread.sleep(1); // must sleep to give the other threads time

                    if(finished) {
                        return;
                    }
                } // keep looping until you're invited or the game is over

                if(leader) { // only the leader will do this
                    if(cupcake.isEaten()) { // they check if the cupcake is eaten
                        eaten++; // if so a new person has finished the maze
                        cupcake.replace();
                        invited = false;
    
                        if(eaten >= assignment2.numGuests-1) { // everyone else has run it
                            System.out.println("All guests have now entered the labyrinth.");
                            Guest.finished = true;
                            invited = false;
                            // send the kill signal to the Minotaur
                        }
                    } else {
                        invited = false;
                        // do nothing!
                        // no new person has eaten a cupcake

                        if(eaten >= assignment2.numGuests-1) { // everyone else has run it
                            System.out.println("All guests have now entered the labyrinth.");
                            Guest.finished = true;
                            invited = false;
                            // send the kill signal to the Minotaur
                        } // just in case it somehow became done
                    }
                } else { // they are not the leader
                    if(cupcake.isEaten()) {
                        invited = false;
                        // if the cupcake is eaten, do nothing
                        // because we gain no useful information this way
                    } else {
                        if(eaten == 0) { // they have not eaten before
                            cupcake.eat();
                            eaten = 1;
                            // they use the eaten int a little differently
                        } // if they already eaten do nothing
                        invited = false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION CAUGHT");
            e.printStackTrace();
        }
    }
}

public class assignment2 {
    public static int numGuests = 25;

    public static void main(String[] args) throws Exception {
        // PROBLEM 1
        Guest[] guests = new Guest[numGuests]; // creating the appropriate number of guests
        guests[0] = new Guest(true); // initializing our leader
        guests[0].start();

        for (int i = 1; i < numGuests; i++) {
            guests[i] = new Guest(false);
            guests[i].start(); // starting each thread
        }

        System.out.println("Guests are invited into the labyrinth.");

        //MINOTAUR AREA
        Random rand = new Random();

        while(!Guest.finished) { // going until the guests say they're done
            if (Guest.finished) {
                break; // return if they say they're done
            }
        
            int chosen = rand.nextInt(assignment2.numGuests); // picking a random guest
            //System.out.println("Guest " + chosen + " has entered.");
            guests[chosen].enter(); // allow the guest to enter

            while(guests[chosen].inside()) { // while they're doing their thing inside
                try {
                    Thread.sleep(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (Guest.finished) {
                    break; // return if they say they're done
                }
            }
        }

        try {
            for (int i = 0; i < numGuests; i++) {
                guests[i].join(); // joining them to make sure each is done
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION CAUGHT");
            e.printStackTrace();
        }


        // PROBLEM 2

    }
}