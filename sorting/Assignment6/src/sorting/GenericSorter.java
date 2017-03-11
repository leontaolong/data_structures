package sorting;

import java.util.*;

import java.util.Comparator;

/**
 * Generic Class full of static sorting methods. Used to sort generic element E
 * in a aray
 *
 * @author pattersp
 *
 */

public class GenericSorter {
	/**
	 * Sorts the given array of E in ascending order according to the comparator
	 * using mergesort. You may create as many private helper functions as you
	 * wish to implement this method.
	 * 
	 * A note about ascending order:
	 * 
	 * When the method is finished, it should be true that:
	 * comparator.compare(array[i - 1], array[i]) <= 0 for all i from 1 through
	 * array.length.
	 * 
	 * @param <E>
	 * 
	 * @param array
	 *            the generic element E to sort
	 * @param comparator
	 *            The comparator of E that will be used to compare two Es
	 */
	public static <E> void mergeSort(E[] array, Comparator<E> comparator) {
		E[] result = mergeSortHelper(array, comparator);
		for (int i = 0; i < result.length; i++) {
			array[i] = result[i];
		}
	}

	private static <E> E[] mergeSortHelper(E[] array, Comparator<E> comparator) {
		if (array.length < 2) {
			return array;
		}
		int mid = array.length / 2;
		E[] smallHalf = mergeSortHelper(Arrays.copyOfRange(array, 0, mid), comparator);
		E[] largeHalf = mergeSortHelper(Arrays.copyOfRange(array, mid, array.length), comparator);
		return merge(smallHalf, largeHalf, comparator);
	}

	private static <E> E[] merge(E[] arr1, E[] arr2, Comparator<E> comparator) {
		int resultSize = arr1.length + arr2.length;
		@SuppressWarnings("unchecked")
		E[] result = (E[]) new Object[resultSize];
		int arr1Index = 0;
		int arr2Index = 0;
		int resultIndex = 0;
		boolean oneFinish = false;
		while (!oneFinish) {
			int diff = comparator.compare(arr1[arr1Index], arr2[arr2Index]);
			if (diff <= 0) {
				result[resultIndex] = arr1[arr1Index];
				arr1Index++;
			} else {
				result[resultIndex] = arr2[arr2Index];
				arr2Index++;
			}
			resultIndex++;
			if (arr1Index == arr1.length) {
				oneFinish = true;
				result = finishTheRest(result, resultIndex, arr2, arr2Index);
			}
			if (arr2Index == arr2.length) {
				oneFinish = true;
				result = finishTheRest(result, resultIndex, arr1, arr1Index);
			}
		}
		return result;
	}

	private static <E> E[] finishTheRest(E[] result, int resultIndex, E[] arr, int arrIndex) {
		while (arrIndex < arr.length) {
			result[resultIndex] = arr[arrIndex];
			resultIndex++;
			arrIndex++;
		}
		return result;
	}

	/**
	 * Sort the array of E in ascending order according to the comparator using
	 * selection sort.
	 * 
	 * A note about ascending order:
	 * 
	 * When the method is finished, it should be true that:
	 * comparator.compare(array[i - 1], array[i]) <= 0 for all i from 1 through
	 * array.length.
	 * 
	 * @param <E>
	 * 
	 * @param array
	 *            the array of E that will be sorted.
	 * @param comparator
	 *            The comparator of E that will be used to compare two Es
	 */
	public static <E> void selectionSort(E[] array, Comparator<E> comparator) {
		for (int i = 0; i < array.length - 1; i++) {
			int nextIndex = i + 1;
			int minIndex = i;
			for (int j = nextIndex; j < array.length; j++) {
				int result = comparator.compare(array[minIndex], array[j]);
				if (result > 0)
					minIndex = j;
			}
			E temp = array[i];
			array[i] = array[minIndex];
			array[minIndex] = temp;
		}
	}
}
