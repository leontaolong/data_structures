package sorting;

import java.util.*;

import java.util.Comparator;

/**
 * Class full of static sorting methods. Used to sort Integers.
 * 
 * TODO: Implement mergeSort() and selectionSort().
 * 
 * insertionSort is implemented for you as an example.
 * 
 * @author pattersp
 *
 */

public class IntegerSorter {
	/**
	 * Sorts the given array of integers in ascending order according to the
	 * comparator using mergesort. You may create as many private helper
	 * functions as you wish to implement this method.
	 * 
	 * A note about ascending order:
	 * 
	 * When the method is finished, it should be true that:
	 * comparator.compare(array[i - 1], array[i]) <= 0 for all i from 1 through
	 * array.length.
	 * 
	 * @param array
	 *            the integers to sort
	 * @param comparator
	 *            The comparator the will be used to compare two integers.
	 */
	public static void mergeSort(Integer[] array, Comparator<Integer> comparator) {
		Integer[] result = mergeSortHelper(array, comparator);
		for (int i = 0; i < result.length; i++) {
			array[i] = result[i];
		}
		// throw new UnsupportedOperationException();
	}

	private static Integer[] mergeSortHelper(Integer[] array, Comparator<Integer> comparator) {
		if (array.length < 2) {
			return array;
		}
		int mid = array.length / 2;
		Integer[] smallHalf = mergeSortHelper(Arrays.copyOfRange(array, 0, mid), comparator);
		Integer[] largeHalf = mergeSortHelper(Arrays.copyOfRange(array, mid, array.length), comparator);
		return merge(smallHalf, largeHalf, comparator);
	}

	private static Integer[] merge(Integer[] arr1, Integer[] arr2, Comparator<Integer> comparator) {
		int resultSize = arr1.length + arr2.length;
		Integer[] result = new Integer[resultSize];
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

	private static Integer[] finishTheRest(Integer[] result, int resultIndex, Integer[] arr, int arrIndex) {
		while (arrIndex < arr.length) {
			result[resultIndex] = arr[arrIndex];
			resultIndex++;
			arrIndex++;
		}
		return result;
	}

	/**
	 * Sort the array of integers in ascending order according to the comparator
	 * using selection sort.
	 * 
	 * A note about ascending order:
	 * 
	 * When the method is finished, it should be true that:
	 * comparator.compare(array[i - 1], array[i]) <= 0 for all i from 1 through
	 * array.length.
	 * 
	 * @param array
	 *            the array of integer that will be sorted.
	 * @param comparator
	 *            The comparator the will be used to compare two integers.
	 */
	public static void selectionSort(Integer[] array, Comparator<Integer> comparator) {
		for (int i = 0; i < array.length - 1; i++) {
			int nextIndex = i + 1;
			int minIndex = i;
			for (int j = nextIndex; j < array.length; j++) {
				int result = comparator.compare(array[minIndex], array[j]);
				if (result > 0)
					minIndex = j;
			}
			Integer temp = array[i];
			array[i] = array[minIndex];
			array[minIndex] = temp;
		}
		// throw new UnsupportedOperationException();
	}

	/**
	 * Sort the array of integers in ascending order according to the comparator
	 * using insertion sort.
	 * 
	 * A note about ascending order:
	 * 
	 * When the method is finished, it should be true that:
	 * comparator.compare(array[i - 1], array[i]) <= 0 for all i from 1 through
	 * array.length.
	 * 
	 * @param array
	 *            the array of integers that will be sorted.
	 * @param comparator
	 *            The comparator the will be used to compare two integer.
	 */
	public static void insertionSort(Integer[] array, Comparator<Integer> comparator) {
		for (int outerIndex = 1; outerIndex < array.length; outerIndex++) {
			Integer currentInt = array[outerIndex];
			int innerIndex = outerIndex - 1;
			while (innerIndex >= 0 && comparator.compare(currentInt, array[innerIndex]) < 0) {
				array[innerIndex + 1] = array[innerIndex];
				innerIndex--;
			}
			array[innerIndex + 1] = currentInt;
		}
	}
}
