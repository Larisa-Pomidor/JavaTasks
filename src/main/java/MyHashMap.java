import java.util.ArrayList;

class MyHashMap {

    ArrayList<Integer> keys = new ArrayList<>();
    ArrayList<Integer> vals = new ArrayList<>();

    public MyHashMap() {

    }

    public void put(int key, int value) {
        if (keys.contains(key)) vals.set(keys.indexOf(key), value);
        else { keys.add(key); vals.add(value); }
    }

    public int get(int key) {
       if (keys.contains(key)) return vals.get(keys.indexOf(key)); else return -1;
    }

    public void remove(int key) {
        if (keys.contains(key)) { vals.remove(keys.indexOf(key)); keys.remove(keys.indexOf(key)); }
    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */