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
import java.util.Map;
import java.util.Set;

import de.topobyte.jsqltables.table.Table;
import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.iface.QueryException;

public class LongStringMap extends AbstractMap<Long, String>
{

	public LongStringMap(IConnection connection, Table table)
	{
		super(connection, table);
	}

	public LongStringMap(IConnection connection, Table table, int indexKeys,
			int indexValues)
	{
		super(connection, table, indexKeys, indexValues);
	}

	@Override
	public boolean containsKey(Object key)
	{
		try {
			return tryContainsKey(stmt -> {
				stmt.setLong(1, (Long) key);
			});
		} catch (QueryException e) {
			throw new RuntimeException("Error in containsKey()", e);
		}
	}

	@Override
	public boolean containsValue(Object value)
	{
		try {
			return tryContainsValue(stmt -> {
				stmt.setString(1, (String) value);
			});
		} catch (QueryException e) {
			throw new RuntimeException("Error in containsValue()", e);
		}
	}

	@Override
	public String get(Object key)
	{
		try {
			return tryGet(stmt -> {
				stmt.setLong(1, (Long) key);
			}, results -> {
				return results.getString(1);
			});
		} catch (QueryException e) {
			throw new RuntimeException("Error in get()", e);
		}
	}

	@Override
	public String put(Long key, String value)
	{
		try {
			return tryPut(key, value);
		} catch (QueryException e) {
			throw new RuntimeException("Error in put()", e);
		}
	}

	private String tryPut(Long key, String value) throws QueryException
	{
		String stored = get(key);
		if (stored == null) {
			tryInsert(stmt -> {
				stmt.setLong(1, key);
				stmt.setString(2, value);
			});
		} else {
			tryUpdate(stmt -> {
				stmt.setLong(1, key);
				stmt.setString(2, value);
			});
		}
		return stored;
	}

	@Override
	public void putAll(Map<? extends Long, ? extends String> m)
	{
		try {
			tryPutAll(m, (stmt, key, value) -> {
				stmt.setLong(1, key);
				stmt.setString(2, value);
			});
		} catch (QueryException e) {
			throw new RuntimeException("Error in putAll()", e);
		}
	}

	@Override
	public String remove(Object key)
	{
		String stored = get(key);
		if (stored == null) {
			return null;
		}
		try {
			tryRemove(stmt -> {
				stmt.setLong(1, (Long) key);
			});
		} catch (QueryException e) {
			throw new RuntimeException("Error in remove()", e);
		}
		return stored;
	}

	@Override
	public Set<Long> keySet()
	{
		return new LongSet(connection, table, 1);
	}

	@Override
	public Collection<String> values()
	{
		return new StringSet(connection, table, 2);
	}

}
