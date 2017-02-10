/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

/**
 * @author Julian
 */
public class SimpleQueue<T> {
    int size;
    Node<T> head;
    Node<T> tail;
    
    public SimpleQueue() {
        size = 0;
        head = null;
        tail = null;
    }

    /*******************************************************************
     * Adds an object to the queue
     * @param obj Object we are adding to the queue
     * @return true :)
     */
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
        return true; // I see no reason to return false
    }

    
    /*******************************************************************
     * Remove an object from the queue
     * @return the object we removed
     */
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

    /*******************************************************************
     * See what's at the start of the queue
     * @return Object at the head of the queue
     */
    public Object peek() {
        return head;
    }

    /*******************************************************************
     * Return the size of the queue
     * @return Size of the queue
     */
    public int size() {
        return size;
    }

    /*******************************************************************
     * Determines if the queue is empty
     * @return true if the queue is empty, otherwise returns false
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /*******************************************************************
     * Removes an object from the queue
     * @param o Object we are removing
     * @return true if we have removed the object, false if the object
     * is not in the queue
     */
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
            if(curr.prev != null) {
                curr.prev.next = curr.next;
            }
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

    public void clear() {
        head = tail = null;
    }

}
