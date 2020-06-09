import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*Just trying an array implementation too */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;

    // Construct an empty randomized queue
    public RandomizedQueue() {
        int defaultSize = 2;

        items = (Item[]) new Object[defaultSize];
        n = 0;
    }

    // If the queue empty
    public boolean isEmpty() {
        return (n == 0);
    }

    // Return the number of items on the queue
    public int size() {
        return n;
    }

    // Add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        if (n == items.length)
            resizeItems(items.length * 2);

        items[n] = item;
        n++;
    }

    // Remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(n);
        Item item = items[index];

        items[index] = items[n - 1];
        items[n - 1] = null;
        n--;

        if (n > 0 && (n == items.length / 4)) {
            resizeItems(items.length / 2);
        }

        return item;
    }

    // Return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(n);

        return items[index];
    }

    // Return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] itemsCopy;
        private int sizeCopy;

        private RandomizedQueueIterator() {
            sizeCopy = n;
            itemsCopy = (Item[]) new Object[sizeCopy];

            for (int i = 0; i < sizeCopy; i++) {
                itemsCopy[i] = items[i];
            }
        }

        public boolean hasNext() {
            return (sizeCopy > 0);
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }

            int index = StdRandom.uniform(sizeCopy);
            Item item = itemsCopy[index];

            // replace taken element with last one
            itemsCopy[index] = itemsCopy[sizeCopy - 1];
            itemsCopy[sizeCopy - 1] = null;
            sizeCopy--;

            return item;
        }
    }

    // Resize items array
    private void resizeItems(int length) {
        Item[] newItems = (Item[]) new Object[length];

        for (int i = 0; i < n; i++) {
            newItems[i] = items[i];
        }

        items = newItems;
    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
    }
}