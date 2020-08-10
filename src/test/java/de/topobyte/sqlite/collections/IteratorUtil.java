// Copyright 2020 Sebastian Kuerten
//
// This file is part of sqlite-collections.
//
// sqlite-collections is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// sqlite-collections is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with sqlite-collections. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.sqlite.collections;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class IteratorUtil
{

	public static int count(Iterator<?> iterator)
	{
		int count = 0;
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		return count;
	}

	public static <E> Set<E> toSet(Iterator<E> iterator)
	{
		Set<E> set = new HashSet<>();
		while (iterator.hasNext()) {
			set.add(iterator.next());
		}
		return set;
	}

}
