import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;
    
    private class Node {
        private Item item;
        private Node previous;
        private Node next;
    }
    
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // return the number of items on the deque
    public int size() {
        return size;
    }
    
    // add the item to the front
    public void addFirst(Item item) {
        throwNull(item);
        
        Node oldFirst = first;
            
        first = new Node();
        first.item = item;
        first.previous = null;
        first.next = oldFirst;
        
        if (isEmpty()) {
            last = first;
        } 
        else {
            oldFirst.previous = first;
        }
        
        size++;
    }
    
    // add the item to the end
    public void addLast(Item item) {
        throwNull(item);
        
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.previous = oldLast;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } 
        else {
            oldLast.next = last;
        }
        size++;
    }
    
    // remove and return the item from the front
    public Item removeFirst() {
        throwRemoveEmpty();
        Item item = first.item;
        Node oldFirst = first;
        oldFirst = null; // Clear the old first node, so it can be GC
        first = first.next;
        if (first == null) {
            last = null;
        } 
        else {
            first.previous = null;
        }
        size--;
        return item;
    }
    
    // remove and return the item from the end
    public Item removeLast() {
        throwRemoveEmpty();
        Item item = last.item;
        Node oldLast = last;
        oldLast = null; // Clear the old last node, so it can be GC
        last = last.previous;
        if (last == null) {
            first = null;   
        } 
        else {
            last.next = null;
        }
        size--;
        return item;
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException("Remove is not supported.");
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("There is no next.");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    
    private void throwNull(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException("Item cannot be null.");
        }
    }
            
    private void throwRemoveEmpty() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Attempting to remove item from empty deque");
        }
    }
        
   // unit testing (optional)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        System.out.println("Deque is empty: " + deque.isEmpty());
        
        System.out.println("Test addFirst");
        deque.addFirst(1);
        System.out.println("Deque's first item is: " + deque.first.item);
        System.out.println("Deque's last item is: "  +deque.last.item);
        System.out.println("Deque is empty: " + deque.isEmpty());
        System.out.println("Deque's size is: " + deque.size());
        
        System.out.println("Test addLast");
        deque.addLast(2);
        System.out.println("Deque's first item is: " + deque.first.item);
        System.out.println("Deque's last item is: "  +deque.last.item);
        System.out.println("Deque is empty: " + deque.isEmpty());
        System.out.println("Deque's size is: " + deque.size());
        
        System.out.println("Test iterator");
        Iterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        
        System.out.println("Test removeLast");
        deque.removeLast();
        System.out.println("Deque's first item is: " + deque.first.item);
        System.out.println("Deque's last item is: "  +deque.last.item);
        System.out.println("Deque is empty: " + deque.isEmpty());
        System.out.println("Deque's size is: " + deque.size());
        
        System.out.println("Test removeFirst");
        System.out.println("Deque's first item is: " + deque.first.item);
        System.out.println("Deque's last item is: "  +deque.last.item);
        System.out.println("Deque is empty: " + deque.isEmpty());
        System.out.println("Deque's size is: " + deque.size());
    }
}
