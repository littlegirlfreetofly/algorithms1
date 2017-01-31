import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size = 0;
    private int capacity = 1;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[capacity];
    }
        
    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // return the number of items on the queue
    public int size() {
        return size;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException("Item cannot be null.");
        }
        if (size + 1 > capacity) {
            resizingUp();
        }
        queue[size++] = item;
    }
    
    // remove and return a random item
    public Item dequeue() {
        throwEmptyQueue();
        int i = StdRandom.uniform(size);
        Item item = queue[i];
        queue[i] = queue[--size];
        queue[size] = null;
        
        if (size < capacity / 4 && size > 0) {
            resizingDown();
        }
        return item;
    }
    
    // return (but do not remove) a random item
    public Item sample() {
        throwEmptyQueue();
        return queue[StdRandom.uniform(size)];
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }
    
    private class RandomIterator implements Iterator<Item> {
        private int current = 0;
        private int[] shuffledIndexes = new int[size];
        
        public RandomIterator() {
            for (int i = 0; i < size; i++) {
                shuffledIndexes[i] = i;
            }
            StdRandom.shuffle(shuffledIndexes);
        }
        
        public boolean hasNext() {
            return current < size;
        }
        
        public void remove() {
            throw new UnsupportedOperationException(); 
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("There is no next.");
            }
            return queue[shuffledIndexes[current++]];
        }
    }
   
    private void resizingUp() {
        capacity *= 2;
        queue = copyQueue();
    }
    
    private void resizingDown() {
        capacity /= 2;
        queue = copyQueue();
    }
    
    private Item[] copyQueue() {
        Item[] newQueue = (Item[]) new Object[capacity];
        int j = 0;
        for (int i = 0; i < size; i++) {
            newQueue[j++] = queue[i];
        }
        return newQueue;
    }
    
    private void throwEmptyQueue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Dequeue from empty randomized queue");
        }
    }
    
    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        
        System.out.println("Test isEmpty");
        System.out.println("RandomizedQueue is empty: " + rq.isEmpty());
        
        System.out.println("Test enqueue/size");
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        System.out.println("RandomizedQueue size: " + rq.size());
        System.out.println("RandomizedQueue is empty: " + rq.isEmpty());
        
        System.out.println("Test sample");
        System.out.println("Sample: " + rq.sample());
        
        System.out.println("Test iterator");
        Iterator<Integer> iter0 = rq.iterator();
        while (iter0.hasNext()) { 
            System.out.print(iter0.next() + " ");
        }
        
        System.out.println("Test Dequeue");
        rq.dequeue();
        Iterator<Integer> iter1 = rq.iterator();
        while (iter1.hasNext()) { 
            System.out.print(iter1.next() + " ");
        }
        rq.dequeue();
        rq.dequeue();
        System.out.println("RandomizedQueue is empty: " + rq.isEmpty());
    }
}
