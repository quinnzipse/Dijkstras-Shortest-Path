// Quinn Zipse Homework6 CS340
import java.util.Arrays;

public class DijkstraHeap {
    private class Item implements Comparable<Item>{
        private int node;
        private int distance;

        private Item(int n, int d){
            node = n;
            distance = d;
        }

        @Override
        public int compareTo(Item o) {
            return this.distance - o.distance;
        }
    }

    private Item items[];
    private int locations[];

    private int size;

    public DijkstraHeap(int s){
        items = new Item[s+1];
        locations = new int[s];
        // initially no nodes have been inserted
        Arrays.fill(locations, -1);
        size = 0;
    }

    public void removeMin(){
        //PRE: getSize() != 0
        // removes the highest priority item in the queue.

    }

    public int getMinNode(){
        //PRE: getSize() != 0
        // returns the highest priority node
        return -99;
    }

    public int getMinDistance(){
        //PRE: getSize() != 0
        // returns the distance of highest priority node
        return -99;
    }

    public boolean full(){
        // returns true if the heap is full otherwise returns false
        return false;
    }

    public void insert(int n, int d){
        //PRE !full() and !inserted(n)
    }

    public void decreaseKey(int n, int d){
        //PRE: inserted(n) and d < the distance associated with n
        // replace the current distance associated with n with d
        // and adjust the heap
        // Don't just remove and reinsert

        int loc = locations[n];
        items[loc].distance = d;

        // Might not be ok with parents
        // So let's check

    }

    public int getSize(){
        // return the number of items in the heap
        return size;
    }

    public boolean inserted(int n){
        // return true if n has been inserted otherwise return false
        return locations[n] != -1;
    }
}
