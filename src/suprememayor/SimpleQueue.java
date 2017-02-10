/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * @author Julian
 */
public class SimpleQueue<T> implements Queue {
    int size;
    Node<T> head;
    Node<T> tail;
    
    public SimpleQueue() {
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    public boolean add(Object obj) {
        if(head == null) {
            head = tail = new Node<>((T)obj);
        }
        else {
            tail.next = new Node<>((T)obj);
            tail.next.prev = tail;
            tail = tail.next;
        }
        size++;
        return true; // We should always return true, right? We're not limiting this queue... yet
    }

    @Override
    public boolean offer(Object e) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public Object remove() {
        if (head == null) {
            return null;
        } else {
            Object temp = head.obj;
            if (head.next == null) {
                head = null;
            } else {
                head = head.next;
            }
            size--;
            return temp;
        }
    }

    @Override
    public Object poll() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public Object element() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public Object peek() {
        return head;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] toArray(Object[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Object o) {
        if(o == null)
            return false;
        
        Node curr = head;
        while(curr.obj != o && curr != null) {
            curr = curr.next;
        }
        if(curr != null) {
            if(curr == head) {
                head = head.next;
            }
            curr.prev.next = curr.next;
            if(curr.next != null) {
                curr.next.prev = curr.next;
            }
            else {
                tail = curr.prev;
            }
            size--;
            return true; // We found it and corrected it
        }
        return false; // We haven't found it
    }

    @Override
    public boolean containsAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        head = tail = null;
    }

    @Override
    public void forEach(Consumer action) {
        Queue.super.forEach(action); //To change body of generated methods, choose Tools | Templates.
    }

}
