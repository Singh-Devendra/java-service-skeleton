/**
 * 
 */
package com.metacube.tms.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 21-Sep-2018
 */
public class CollectionUtils
{
	public static <T> Collection<T> iterableToCollection(Iterable<T> iterable)
	{
		// check if iterable is instance of a List
		if (iterable instanceof List) {
			return (List<T>) iterable;
		}

		// convert iterable to spliterator, get stream from spliterator
		// and collect values into a Iterable (say list)
		return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
	}

	// Program to convert an Iterable to a Collection in Java
	public static void main(String[] args)
	{
		Iterable<Integer> iterable = Arrays.asList(1, 2, 3, 4, 5);
		Collection<Integer> collection = iterableToCollection(iterable);
		System.out.println(collection);
	}
}