import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int n;

    private Node<Item> first;
    private Node<Item> last;

    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node<Item> newItem = new Node<>();
        newItem.item = item;
        newItem.next = first;
        newItem.prev = null;

        if (isEmpty())
            last = newItem;
        else
            first.prev = newItem;

        first = newItem;
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node<Item> newItem = new Node<>();
        newItem.item = item;
        newItem.next = null;
        newItem.prev = last;

        if (isEmpty())
            first = newItem;
        else
            last.next = newItem;

        last = newItem;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        Item item = first.item;
        first = first.next;
        n--;

        if (isEmpty())
            last = null;
        else
            first.prev = null;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        Item item = last.item;
        last = last.prev;
        n--;

        if (isEmpty())
            first = null;
        else
            last.next = null;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator<>(first);
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        private DequeIterator(Node<Item> item) {
            current = item;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;

            return item;
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
    }

}