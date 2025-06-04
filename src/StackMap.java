import java.util.*;

//具有堆栈（后进先出，LIFO）特性的 Map
//只保存互不相同的key
public class StackMap<K, V> {
    private final Deque<K> stack;
    private final HashMap<K, V> map;

    public StackMap() {
        this.stack = new LinkedList<>();
        this.map = new HashMap<>();
    }

    // return true if successfully push the key
    // return false if the key already exist
    public boolean push(K key, V value) {
        if (map.containsKey(key)) return false;
        stack.push(key);
        map.put(key, value);
        return true;
    }

    public K pop() {
        if (stack.isEmpty()) return null;
        K key = stack.pop();
        map.remove(key);
        return key;
    }

    public K peek() {
        if (stack.isEmpty()) return null;
        return stack.peek();
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public V get(K key) {
        return map.get(key);
    }

    // return true if override the old value of the key
    // return false if the key does not exist, and do not store (K,V)
    public boolean modifyValue(K key, V value) {
        if (!this.map.containsKey(key)) return false;
        this.map.put(key, value);
        return true;
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public Iterator<K> getKeyIterator(boolean FIFO) {
        if (FIFO)
            return stack.descendingIterator();
        else
            return stack.iterator();
    }
}