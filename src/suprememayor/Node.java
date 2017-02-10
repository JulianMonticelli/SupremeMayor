/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

/**
 * @author Julian
 */
public class Node<T> {
    public Node prev;
    public Node next;
    public T obj;
    
    public Node(T ob) {
        obj = ob;
    }
}
