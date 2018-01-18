package org.leoyy.utils4j.algo;

import java.util.Comparator;

/**
 * Created by yuanyi on 2018/1/18.
 */
public class ArraysSort {

    private static final int MIN_LEN = 16;

    private static int lg(int length) {
        return (int)Math.floor(Math.log(length) / Math.log(2));
    }

    public static void introSort(int[] array, int from, int to, final IntCompartor comp) {
        introSortLoop(array, from, to, comp, lg(to - from) * 2);
    }

    private static void swap(int[] array, int l, int r) {
        int temp = array[l];
        array[l] = array[r];
        array[r] = temp;
    }

    private static void introSortLoop(int[] array, int from, int to, final IntCompartor comp, int depLimit) {
        if (to <= from) {
            return;
        }
        depLimit --;
        if (depLimit <= 0) {
            heapSort(array, from, to, comp);
            return;
        }
        while (to - from > MIN_LEN) {
            int mid = from + (to - from) / 2;
            {
                int beg = from + 1;
                int end = to - 1;
                if (comp.compare(array[beg], array[mid]) < 0) {
                    if (comp.compare(array[mid], array[end]) < 0) {
                        mid = mid;
                    } else if (comp.compare(array[beg], array[end]) < 0) {
                        mid = end;
                    } else {
                        mid = beg;
                    }
                } else if (comp.compare(array[beg], array[end]) < 0) {
                    mid = beg;
                } else if (comp.compare(array[mid], array[end]) < 0) {
                    mid = end;
                } else {
                    mid = mid;
                }
            }
            int mVal = array[mid];
            if (mid != from) {
                swap(array, mid, from);
            }
            int ltr = from + 1;
            int rtr = to;
            int privot;
            while (true) {
                while (comp.compare(array[ltr], mVal) < 0) {
                    ltr++;
                }
                rtr--;
                while (comp.compare(mVal, array[rtr]) < 0) {
                    rtr--;
                }

                if (ltr >= rtr) {
                    privot = ltr;
                    break;
                }
                swap(array, ltr, rtr);
                ltr++;
            }
            introSortLoop(array, privot, to, comp, depLimit);
            to = privot;
        }
        if (to - from > 1) {
            selectSort(array, from, to, comp);
        }
        return;
    }

    private static int leafSearch(int[] array, int from, int to, final IntCompartor comp) {
        int start = from;
        int lchild = 2 * start + 1;
        int rchild = 2 * start + 2;
        while (lchild <= to) {
            if (rchild <= to && comp.compare(array[rchild], array[lchild]) > 0) {
                start = rchild;
            } else {
                start = lchild;
            }
            lchild = 2 * start + 1;
            rchild = 2 * start + 2;
        }
        return start;
    }

    private static void siftDown(int[] array, int from, int to, final IntCompartor comp) {
        int start = leafSearch(array, from, to, comp);
        while (comp.compare(array[from], array[start]) > 0) {
            start = (int)Math.floor((start - 1) / 2);
        }
        int temp = array[start];
        array[start] = array[from];
        while (start > from) {
            int parent = (int) Math.floor((start - 1) / 2);
            int t = array[parent];
            array[parent] = temp;
            temp = t;
            start = parent;
        }
    }

    private static void makeHeap(int[] array, int from, int to, final IntCompartor comp) {
        int start = (int)Math.floor((to - 1 - 1) / 2);
        while (start >= from) {
            siftDown(array, start, to - 1, comp);
            start --;
        }
    }

    public static void heapSort(int[] array, int from, int to, final IntCompartor comp) {
        makeHeap(array, from, to, comp);
        int end = to - 1;
        while (end > from) {
            swap(array, end, from);
            end --;
            siftDown(array, from, end, comp);
        }
    }

    public static void selectSort(int[] array, int from, int to, final IntCompartor comp) {
        for (int i = from; i < to - 1; ++i) {
            int m = i;
            for (int j = i + 1; j < to; ++j) {
                if (comp.compare(array[j], array[m]) < 0) {
                    m = j;
                }
            }
            if (m != i) {
                swap(array, i, m);
            }
        }
    }
}

