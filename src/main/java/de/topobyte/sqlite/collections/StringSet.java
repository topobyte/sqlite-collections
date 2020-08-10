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

import de.topobyte.jsqltables.table.Table;
import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.iface.QueryException;

public class StringSet extends AbstractSet<String>
{

	public StringSet(IConnection connection, Table table)
	{
		super(connection, table);
	}

	public StringSet(IConnection connection, Table table, int indexValues)
	{
		super(connection, table, indexValues);
	}

	@Override
	public boolean contains(Object o)
	{
		try {
			return tryContains(stmt -> {
				stmt.setString(1, (String) o);
			});
		} catch (QueryException e) {
			throw new RuntimeException("Error in contains()", e);
		}
	}

	@Override
	public CloseableIterator<String> iterator()
	{
		try {
			return tryIterator(r -> {
				return r.getString(1);
			});
		} catch (QueryException e) {
			throw new RuntimeException("Error in iterator()", e);
		}
	}

}
