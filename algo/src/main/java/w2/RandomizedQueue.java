package w2; /**
 *
 * Copyright (c) Ericsson AB, 2011.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 *
 * ERICSSON MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. ERICSSON SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * User: Nicolas
 * Date: 9/11/13
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * comments.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size = 0;
    private Item[] q;
    private Random random = new Random(1234L);

    // construct an empty randomized queue
    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        q = (Item[]) new Object[1];
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
            throw new NullPointerException("item is null.");
        }
        if (size == q.length) {
            resize(2 * q.length);
        }
        q[size++] = item;
        // do one swap Knuth shuffle
        int r = random.nextInt(size);
        Item temp = q[r];
        int last = size - 1;
        if (r != last) {
            q[r] = q[last];
            q[last] = temp;
        }
    }

    // delete and return a random item
    public Item dequeue() {
        Item item = q[--size];
        q[size] = null;
        if (size > 0 && size == q.length / 4) {
            resize(q.length / 2);
        }
        return item;
    }

    // return (but do not delete) a random item
    public Item sample() {
        int r = random.nextInt(size);
        return q[r];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private void resize(int capacity) {
        @SuppressWarnings("unchecked")
        Item[] copy = (Item[]) new Object[capacity];
        System.arraycopy(q, 0, copy, 0, size);
        q = copy;
    }

    private class ReverseArrayIterator implements Iterator<Item> {

        private int i = size;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            if (i == 0) {
                throw new NoSuchElementException("As per instructions.");
            }
            return q[--i];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("as per instructions");
        }
    }
}
