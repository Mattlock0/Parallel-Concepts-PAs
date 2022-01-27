public class Counter2 {
    private int value;

    public synchronized int getAndIncrement() {
            int temp = this.value;
            this.value = temp + 1;
        return temp;
    }

    public Counter2(int val) {
        this.value = val;
    }
}