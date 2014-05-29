package utils;

import java.util.*;

public class Util{

	public static <E> List<E> iterableToList(Iterable<E> iter){
		List<E> list = new ArrayList<E>();
		for(E item : iter){
			list.add(item);
		}
		return list;
	}
}
