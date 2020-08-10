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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import de.topobyte.jsqltables.table.Table;
import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.iface.QueryException;

public abstract class AbstractSet<E> extends AbstractTableBased
		implements Set<E>
{

	protected int indexValues;

	public AbstractSet(IConnection connection, Table table)
	{
		this(connection, table, 1);
	}

	public AbstractSet(IConnection connection, Table table, int indexValues)
	{
		super(connection, table);
		this.indexValues = indexValues;
	}

	protected boolean tryContains(ArgumentSetter argSetter)
			throws QueryException
	{
		return tryContainsColumn(indexValues, argSetter);
	}

	@Override
	public Iterator<E> iterator()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(E e)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear()
	{
		throw new UnsupportedOperationException();
	}

}
