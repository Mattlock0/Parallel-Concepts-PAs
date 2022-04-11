import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.lang.Math;

class Present {
    private boolean noteWritten;
    private int tagNumber;

    Present(int tag) {
        this.tagNumber = tag;
        this.noteWritten = false;
    }
    
    public int getTag() {
        return tagNumber;
    }

    public void writeNote() {
        noteWritten = true;
    }

    public static LinkedList<Present> createList(int size) {
        LinkedList<Present> list = new LinkedList<Present>();
        for (int i = 1; i < size+1; i++) {
            Present pres = new Present(i);
            list.add(pres);
        }

        return list;
    }

    public static void shuffleList(LinkedList list) {
        Object[] array = list.toArray();
        Random rand = new Random();

        for (int i = 0; i < array.length; i++) {
            int index = rand.nextInt(i + 1);
            // random index

            Object gift = array[index];
            array[index] = array[i];
            array[i] = gift;
            // swaps items 
        }

        ListIterator<Object> it = list.listIterator();
        for (Object n : array) {
            it.next();
            it.set(n);
        }
    }
}

class Servant extends Thread {
    public static LinkedList<Present> unorderedBag;
    public static LinkedList<Present> presentChain;
    private final static Object bagLock = new Object();
    private final static Object chainLock = new Object();
    private boolean job;
    
    Servant(boolean startingJob) {
        this.job = startingJob;
    }

    public void run() {
        try {
            while(unorderedBag.size() > 0 || presentChain.size() > 0) {
                if(job) { // job 1
                    Present pulled;
                    
                    synchronized(bagLock) {
                        if(unorderedBag.size() > 0) {
                            pulled = unorderedBag.remove();
                        } else {
                            this.job = false;
                            continue; // switch job and move on
                        }
                    }

                    synchronized(chainLock) {
                        if(presentChain.size() < 1) {
                            presentChain.add(pulled);
                        } else {
                            ListIterator<Present> it = presentChain.listIterator();
                        
                            while(it.hasNext()) { // cycle through the whole list
                                Present pres = it.next();

                                if(!it.hasNext() || presentChain.get(it.nextIndex()).getTag() > pres.getTag()) {
                                    // in short, if the iterator is at the end OR
                                    // the next tag is higher, add this present
                                    it.add(pres);
                                    break;
                                }
                            }
                        }
                    }

                    this.job = false; // switch job
                } else {  // job 2
                    Present pulled;
                    
                    synchronized(chainLock) {
                        if(presentChain.size() > 0) {
                            pulled = presentChain.remove();
                        } else {
                            this.job = true;
                            continue; // switch job and move on
                        }
                    }

                    pulled.writeNote();
                    this.job = true; // switch job
                }
            }
        } catch(Exception e) {
            System.out.println("EXCEPTION CAUGHT");
            e.printStackTrace();
        }
    }
}

class Sensor extends Thread {
    public static LinkedList<Integer> temps = new LinkedList<Integer>();
    private final static Object lock = new Object();
    private static int minute = 0;

    public static int randomTemp(int max, int min) {
        return (int) (Math.random() * (max - min) + min);
    }
    
    public void run() {
        try {
            while(minute < 60) { // cycle for one hour
                synchronized(lock) {
                    temps.add(randomTemp(-100, 70)); // generate a new temperature
                    minute++; // it's a new minute
                }
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION CAUGHT");
            e.printStackTrace();
        }
    }
}

public class assignment3 {
    public static void main(String args[]) {
        //PROBLEM 1
        Servant.unorderedBag = Present.createList(500000);
        Present.shuffleList(Servant.unorderedBag); // randomize the bag
        Servant.presentChain = new LinkedList<Present>();
        
        int numServants = 4;
        Servant[] servants = new Servant[numServants];
        for(int i = 0; i < numServants; i++) {
            servants[i] = new Servant(true);
            servants[i].start();
        } // initialize the servant threads

        try {
            for (int i = 0; i < servants.length; i++) {
                servants[i].join(); // joining them to make sure each is done
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION CAUGHT");
            e.printStackTrace();
        }
        
        System.out.println("The task is complete, Minotaur!");

        //PROBLEM 2
        int numSensors = 8;
        Sensor[] sensors = new Sensor[numSensors];
        for(int i = 0; i < numSensors; i++) {
            sensors[i] = new Sensor();
            sensors[i].start();
        } // starting all the sensors up

        try {
            for (int i = 0; i < sensors.length; i++) {
                sensors[i].join(); // joining them to make sure each is done
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION CAUGHT");
            e.printStackTrace();
        }

        LinkedList<Integer> highInterval = new LinkedList<Integer>();
        ListIterator<Integer> it = Sensor.temps.listIterator();
        int max = -100;

        while(it.hasNext()) {
            int curr = it.next();
            if(curr > max) max = curr;
        } // finds the max temperature 

        int index = Sensor.temps.indexOf(max);
        if(index < 10) { // if we're going to go out of bounds
            for(int i = index+1; i < 10; i++) {
                highInterval.add(Sensor.temps.get(i));
            } // grab the upper temps
            for(int i = index; i > -1; i--) {
                highInterval.addFirst(Sensor.temps.get(i));
            } // and the rest
        } else {
            for(int i = index; i > index - 10; i--) {
                highInterval.addFirst(Sensor.temps.get(i));
            } // or grab everything below
        }

        LinkedList<Integer> highestTemps = new LinkedList<Integer>();
        LinkedList<Integer> lowestTemps = new LinkedList<Integer>();
        Collections.sort(Sensor.temps); // set up the list for grabbing highest and lowest

        for(int i = 0; i < 5; i++) {
            highestTemps.addFirst(Sensor.temps.removeLast());
            lowestTemps.add(Sensor.temps.removeFirst());
        } // and remove each from the sorted list

        System.out.println("Highest Temperatures Recorded: " + highestTemps + "\nLowest Temperatures Recorded: " + lowestTemps);
        System.out.println("High Temp Interval: " + highInterval);
    }
}