package sorting;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Class full of static sorting methods. Used to sort packets received from a
 * server containing image metadata.
 * 
 * TODO: Implement mergeSort() and selectionSort().
 * 
 * insertionSort is implemented for you as an example.
 * 
 * @author pattersp
 *
 */

public class PacketSorter {
    /**
     * Sorts the given array of packets in ascending order according to the
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
     *            the packets to sort
     * @param comparator
     *            The comparator the will be used to compare two packets.
     */
    public static void mergeSort(Packet[] array, Comparator<Packet> comparator) {
		Packet[] result = mergeSortHelper(array, comparator);
		for (int i = 0; i < result.length; i++) {
			array[i] = result[i];
		}
    }
    
	private static Packet[] mergeSortHelper(Packet[] array, Comparator<Packet> comparator) {
		if (array.length < 2) {
			return array;
		}
		int mid = array.length / 2;
		Packet[] smallHalf = mergeSortHelper(Arrays.copyOfRange(array, 0, mid), comparator);
		Packet[] largeHalf = mergeSortHelper(Arrays.copyOfRange(array, mid, array.length), comparator);
		return merge(smallHalf, largeHalf, comparator);
	}
	
	private static Packet[] merge(Packet[] arr1, Packet[] arr2, Comparator<Packet> comparator) {
		int resultSize = arr1.length + arr2.length;
		Packet[] result = new Packet[resultSize];
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
	
	private static Packet[] finishTheRest(Packet[] result, int resultIndex, Packet[] arr, int arrIndex) {
		while (arrIndex < arr.length) {
			result[resultIndex] = arr[arrIndex];
			resultIndex++;
			arrIndex++;
		}
		return result;
	}

    /**
     * Sort the array of packets in ascending order using selection sort.
     * 
     * A note about ascending order:
     * 
     * When the method is finished, it should be true that:
     * comparator.compare(array[i - 1], array[i]) <= 0 for all i from 1 through
     * array.length.
     * 
     * @param array
     *            the array of packets that will be sorted.
     * @param comparator
     *            The comparator the will be used to compare two packets.
     */
    public static void selectionSort(Packet[] array,
            Comparator<Packet> comparator) {
		for (int i = 0; i < array.length - 1; i++) {
			int nextIndex = i + 1;
			int minIndex = i;
			for (int j = nextIndex; j < array.length; j++) {
				int result = comparator.compare(array[minIndex], array[j]);
				if (result > 0)
					minIndex = j;
			}
			Packet temp = array[i];
			array[i] = array[minIndex];
			array[minIndex] = temp;
		}
		// throw new UnsupportedOperationException();
    }

    /**
     * Sort the array of packets in ascending order using insertion sort.
     * 
     * A note about ascending order:
     * 
     * When the method is finished, it should be true that:
     * comparator.compare(array[i - 1], array[i]) <= 0 for all i from 1 through
     * array.length.
     * 
     * @param array
     *            the array of packets that will be sorted.
     * @param comparator
     *            The comparator the will be used to compare two packets.
     */
    public static void insertionSort(Packet[] array,
            Comparator<Packet> comparator) {
        for (int outerIndex = 1; outerIndex < array.length; outerIndex++) {
            Packet currentPacket = array[outerIndex];
            int innerIndex = outerIndex - 1;
            while (innerIndex >= 0
                    && comparator.compare(currentPacket, array[innerIndex]) < 0) {
                array[innerIndex + 1] = array[innerIndex];
                innerIndex--;
            }
            array[innerIndex + 1] = currentPacket;
        }
    }
}
