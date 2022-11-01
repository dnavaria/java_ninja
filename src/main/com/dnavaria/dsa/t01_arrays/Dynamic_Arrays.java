package com.dnavaria.dsa.t01_arrays;

import javax.naming.InterruptedNamingException;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class Dynamic_Arrays<T> implements Iterable<T> {

    private static final int DEFAULT_CAPACITY = 1 << 3;
    private T[] arr;
    private int len = 0;
    private int capacity = 0;

    // Initialize the array with a default capacity
    public Dynamic_Arrays() {
        this(DEFAULT_CAPACITY);
    }

    // Initialize the array with certain capacity
    public Dynamic_Arrays(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        this.capacity = capacity;
        arr = (T[]) new Object[capacity];
    }

    // Given an int array make it a dynamic array
    public Dynamic_Arrays(int[] array) {
        if (array == null) throw new IllegalArgumentException("Array cannot be null");
        arr = (T[]) new Object[array.length];
        capacity = len = array.length;
        for (int i = 0; i < array.length; i++) {
            // Object element = (Integer)array[i];
            arr[i] = (T) Integer.valueOf(array[i]);
        }
    }

    // Returns the size of the array
    public int size() {
        return len;
    }

    // Returns true/false on whether the array is empty
    public boolean isEmpty() {
        return size() == 0;
    }

    /* To get/set values without method call overhead you can do
        array_object.arr[index] instead, you can gain about 10x more speed
    */
    public T get(int index) {
        if (index < 0 && index >= len) throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        return arr[index];
    }

    public void set(int index, T elem) {
        if (index < 0 && index >= len) throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        arr[index] = elem;
    }

    // Clear the whole array and replace the values with null.
    public void clear() {
        for (int i = 0; i < capacity; i++)
            arr[i] = null;
        len = 0;
    }

    // Add an element to this dynamic array
    public void add(T element) {
        // Time to resize !
        if (len + 1 >= capacity) {
            if (capacity == 0) capacity = 1;
            else capacity *= 2; // double the size
            T[] new_array = (T[]) new Object[capacity];
            /*
             if (len >= 0) System.arraycopy(arr, 0, new_array, 0, len);
             arr = new_array; // arr has extra nulls padded
            */
            arr = java.util.Arrays.copyOf(arr, capacity); // pads with extra 0/null elements
        }
        arr[len++] = element;
    }

    // Removes the element at the specified index in this list.
    public void removeAt(int rm_index) {
        if (rm_index >= len && rm_index < 0) throw new IndexOutOfBoundsException();
        //  T data = arr[rm_index];
        T[] new_array = (T[]) new Object[len - 1];
        for (int i = 0, j = 0; i < len; i++, j++) {
            if (i == rm_index) j--; // skip over rm_index by fixing j temporarily
            else new_array[j] = arr[i];
        }
        arr = new_array;
        capacity = --len;
    }

    public boolean remove(Object obj) {
        for (int i = 0; i < len; i++) {
            if (arr[i].equals(obj)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    public int indexOf(Object obj) {
        for (int i = 0; i < len; i++) {
            if (arr[i].equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = 0;

            public boolean hasNext() {
                return index < len;
            }

            public T next() {
                return arr[index++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        if (len == 0) return "[]";
        else {
            StringBuilder sb = new StringBuilder(len).append("[");
            for (int i = 0; i < len - 1; i++) {
                sb.append(arr[i]).append(", ");
            }
            return sb.append(arr[len - 1]).append("]").toString();
        }
    }

    public static void main(String[] args) {
        Dynamic_Arrays<Integer> da = new Dynamic_Arrays<>(5);
        da.add(1);
        da.add(2);
        System.out.println(da);
        System.out.println(da.get(0));
//        da.removeAt(0);
        da.remove((Object) 1);
        System.out.println(da);

    }
}
