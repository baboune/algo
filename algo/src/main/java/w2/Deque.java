package w2; /**
 *
 * Copyright (c) Ericsson AB, 2013.
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
 * User: lmcnise
 * Date: 9/11/13
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue that supports
 * inserting and removing items from either the front or the back of the data structure..
 */
public class Deque<Item> implements Iterable<Item> {

    // Use linked list
    private Node<Item> head;
    private Node<Item> tail;
    private int size;


    // construct an empty deque
    public Deque() {
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("item is null.");
        }
        Node<Item> node = new Node<Item>();
        node.item = item;
        // First so nothing before
        node.previous = null;
        if (head == null) {
            node.next = null;
            tail = node;
        } else {
            // relink
            head.previous = node;
            node.next = head;
        }
        head = node;
        size++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("item is null.");
        }
        Node<Item> node = new Node<Item>();
        node.item = item;
        // Last item in chain
        node.next = null;
        // First elem
        if (head == null) {
            node.next = null;
            node.previous = null;
            head = node;
        } else {
            tail.next = node;
            node.previous = tail;
        }
        tail = node;
        size++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Item> node = head;
        if (head == tail) {
            tail = null;
            head = null;
        } else {
            // move head
            head = node.next;
            head.previous = null;
        }

        // Clean up ref on removed elem
        node.next = null;
        node.previous = null;
        size--;
        return node.item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Item> node = tail;
        if (head == tail) {
            tail = null;
            head = null;
        } else {
            // Back 1
            tail = tail.previous;
            tail.next = null;
        }
        // Clean up ref on removed elem
        node.next = null;
        node.previous = null;
        size--;
        return node.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> previous;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("item=").append(item);
            sb.append(", next=").append(next);
            sb.append(", previous=").append(previous);
            sb.append('}');
            return sb.toString();
        }
    }


    private class DequeIterator implements Iterator<Item> {

        private Node<Item> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) throw new NoSuchElementException("End of de deque");
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("as per instructions");
        }
    }
}
