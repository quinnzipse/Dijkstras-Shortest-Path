// Quinn Zipse Homework6 CS340

import java.util.Arrays;

public class DijkstraHeap {
    private class Item implements Comparable<Item> {
        private int node;
        private int distance;

        private Item(int n, int d) {
            node = n;
            distance = d;
        }

        @Override
        public int compareTo(Item o) {
            return this.distance - o.distance;
        }
    }

    private final Item[] items;
    private final int[] locations;

    private int size;

    public DijkstraHeap(int s) {
        items = new Item[s + 1];
        locations = new int[s];
        // initially no nodes have been inserted
        Arrays.fill(locations, -1);
        size = 0;
    }

    public void removeMin() {
        //PRE: getSize() != 0
        // removes the highest priority item
        locations[items[1].node] = -1;
        if(size == 1){
            size = 0;
            items[1] = null;
            return;
        }
        items[1] = items[size];
        items[size] = null;
        locations[items[1].node] = 1;
        size--;
        int loc = 1;
        while (loc * 2 <= size) {
            int left = loc*2, right = loc*2+1;
            if (items[loc].compareTo(items[left]) > 0) {
                // Left child is bigger than parent
                items[0] = items[loc];
                items[loc] = items[left];
                items[left] = items[0];
                // Swap the locations!
                locations[items[loc].node] = loc;
                locations[items[left].node] = left;

                // Set loc to the left child and continue
                loc = left;
            }
            else if ( right <= size && items[loc].compareTo(items[right]) > 0 ) {
                // Right child is bigger than parent (and the left child is smaller)
                items[0] = items[loc];
                items[loc] = items[right];
                items[right] = items[0];
                // Swap the locations!
                locations[items[loc].node] = loc;
                locations[items[right].node] = right;

                // Set loc to the right child and continue
                loc = right;
            } else break;
        }
    }

    public int getMinNode() {
        //PRE: getSize() != 0
        // returns the highest priority node
        return items[1].node;
    }

    public int getMinDistance() {
        //PRE: getSize() != 0
        // returns the distance of highest priority node
        return items[1].distance;
    }

    public boolean full() {
        // returns true if the heap is full otherwise returns false
        return size == locations.length;
    }

    public void insert(int n, int d) {
        //PRE !full() and !inserted(n)
        size++;
        int loc = size;
        locations[n] = loc;
        items[loc] = new Item(n, d);
        heapify(loc);
    }

    private void heapify(int loc) {
        while (loc > 1) {
            int parent = loc / 2;
            if (items[parent].compareTo(items[loc]) < 0) {
                // swap and keep looking
                items[0] = items[parent];
                items[parent] = items[loc];
                items[loc] = items[0];

                // Swap the locations!
                locations[items[loc].node] = loc;
                locations[items[parent].node] = parent;

                loc = parent;
            } else break;
        }
    }

    public void decreaseKey(int n, int d) {
        //PRE: inserted(n) and d < the distance associated with n
        // replace the current distance associated with n with d
        int loc = locations[n];
        items[loc].distance = d;

        // Adjust Heap
        heapify(loc);
    }

    public int getSize() {
        // return the number of items in the heap
        return size;
    }

    public boolean inserted(int n) {
        // return true if n has been inserted otherwise return false
        return locations[n] != -1;
    }
}
