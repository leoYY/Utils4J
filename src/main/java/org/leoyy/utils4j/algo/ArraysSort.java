package org.leoyy.utils4j.algo;

import java.util.Comparator;

/**
 * Created by yuanyi on 2018/1/18.
 */
public class ArraysSort<T> {

    private static final int MIN_LEN = 16;

    private static int lg(int length) {
        return (int)Math.floor(Math.log(length) / Math.log(2));
    }

    public void introSort(T[] array, int from, int to, Comparator<T> comp) {
        int depLimit = lg(to - from) * 2;
        introSortLoop(array, from, to, comp, depLimit);
    }

    private void swap(T[] array, int l, int r) {
        T temp = array[l];
        array[l] = array[r];
        array[r] = temp;
    }

    private void introSortLoop(T[] array, int from, int to, Comparator<T> comp, int depLimit) {
        if (depLimit <= 0) {
            heapSort(array, from, to, comp);
            return;
        }
        while (to - from < MIN_LEN) {
            int mid = from + (to - from) / 2;
            {
                if (comp.compare(array[from], array[to]) < 0) {
                    if (comp.compare(array[mid], array[from]) < 0) {
                        mid = from;
                    } else if (comp.compare(array[to], array[mid]) < 0) {
                        mid = to;
                    }
                } else if (comp.compare(array[mid], array[to]) < 0) {
                    mid = to;
                } else if (comp.compare(array[from], array[mid]) < 0) {
                    mid = from;
                } else {
                    mid = mid;
                }
            }
            if (mid != from) {
                swap(array, mid, from);
            }
            int ltr = from + 1;
            int rtr = to;
            int privot;
            while (true) {
                while (comp.compare(array[ltr], array[mid]) < 0) {
                    ltr++;
                }
                rtr--;
                while (comp.compare(array[mid], array[rtr]) < 0) {
                    rtr--;
                }

                if (ltr >= rtr) {
                    privot = ltr;
                    break;
                }
                swap(array, ltr, rtr);
                ltr++;
            }
            introSortLoop(array, privot, to, comp, depLimit - 1);
            to = privot;
        }
        if (to - from > 1) {
            selectSort(array, from, to, comp);
        }
        return;
    }

    private int leafSearch(T[] array, int from, int to, Comparator<T> comp) {

    }

    private void siftDown(T[] array, int from, int to, Comparator<T> comp) {

    }

    private void makeHeap(T[] array, int from, int to, Comparator<T> comp) {}

    public void heapSort(T[] array, int from, int to, Comparator<T> comp) {
    }

    public void selectSort(T[] array, int from, int to, Comparator<T> comp) {

    }
}

