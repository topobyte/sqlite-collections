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

import java.util.Map;

import de.topobyte.jsqltables.table.Table;
import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.iface.QueryException;

public class StringLongMap extends AbstractMap<String, Long>
{

	public StringLongMap(IConnection connection, Table table)
	{
		super(connection, table);
	}

	public StringLongMap(IConnection connection, Table table, int indexKeys,
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
	public Long get(Object key)
	{
		try {
			return tryGet(stmt -> {
				stmt.setString(1, (String) key);
			}, results -> {
				return results.getLong(1);
			});
		} catch (QueryException e) {
			throw new RuntimeException("Error in get()", e);
		}
	}

	@Override
	public Long put(String key, Long value)
	{
		try {
			return tryPut(key, value);
		} catch (QueryException e) {
			throw new RuntimeException("Error in put()", e);
		}
	}

	private Long tryPut(String key, Long value) throws QueryException
	{
		Long stored = get(key);
		if (stored == null) {
			tryInsert(stmt -> {
				stmt.setString(1, key);
				stmt.setLong(2, value);
			});
		} else {
			tryUpdate(stmt -> {
				stmt.setString(1, key);
				stmt.setLong(2, value);
			});
		}
		return stored;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Long> m)
	{
		try {
			tryPutAll(m, (stmt, key, value) -> {
				stmt.setString(1, key);
				stmt.setLong(2, value);
			});
		} catch (QueryException e) {
			throw new RuntimeException("Error in putAll()", e);
		}
	}

	@Override
	public Long remove(Object key)
	{
		Long stored = get(key);
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
	public StringSet keySet()
	{
		return new StringSet(connection, table, 1);
	}

	@Override
	public LongSet values()
	{
		return new LongSet(connection, table, 2);
	}

}
