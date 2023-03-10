package com.raghu.reader;

import java.util.List;

import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;

public class InMemoryReader extends AbstractItemStreamItemReader<Integer> {

	Integer array[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	List<Integer> list = List.of(array);
	int index = 0;

	@Override
	public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		Integer nextItem = null;
		if (index < list.size()) {
			nextItem = list.get(index);
			index++;
		} else {
			index = 0;
		}
		return nextItem;
	}
}
