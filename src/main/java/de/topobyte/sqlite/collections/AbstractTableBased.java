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

import de.topobyte.jsqltables.query.Delete;
import de.topobyte.jsqltables.query.Select;
import de.topobyte.jsqltables.query.select.CountAllColumn;
import de.topobyte.jsqltables.query.select.NormalColumn;
import de.topobyte.jsqltables.query.where.Comparison;
import de.topobyte.jsqltables.query.where.SingleCondition;
import de.topobyte.jsqltables.table.Table;
import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.iface.IPreparedStatement;
import de.topobyte.luqe.iface.IResultSet;
import de.topobyte.luqe.iface.QueryException;

public class AbstractTableBased
{

	protected IConnection connection;
	protected Table table;

	public AbstractTableBased(IConnection connection, Table table)
	{
		this.connection = connection;
		this.table = table;
	}

	public int size()
	{
		try {
			return trySize();
		} catch (QueryException e) {
			throw new RuntimeException("Error in size()", e);
		}
	}

	private int trySize() throws QueryException
	{
		Select select = new Select(table);
		select.addSelectColumn(new CountAllColumn("count"));
		try (IPreparedStatement stmt = connection
				.prepareStatement(select.sql())) {
			try (IResultSet results = stmt.executeQuery()) {
				results.next();
				return results.getInt(1);
			}
		}
	}

	public boolean isEmpty()
	{
		return size() == 0;
	}

	protected Select selectColumn(String col)
	{
		Select select = new Select(table);
		select.addSelectColumn(new NormalColumn(select.getMainTable(), col));
		select.where(new SingleCondition(select.getMainTable(), col,
				Comparison.EQUAL));
		select.limit(1);
		return select;
	}

	protected <E> boolean tryContainsColumn(int index, Object object,
			ArgumentSetter<E> argSetter) throws QueryException
	{
		Select select = selectColumn(table.getColumn(index).getName());
		try (IPreparedStatement stmt = connection
				.prepareStatement(select.sql())) {
			argSetter.setArguments(stmt, 1, (E) object);
			try (IResultSet results = stmt.executeQuery()) {
				return results.next();
			}
		}
	}

	public void clear()
	{
		try {
			tryClear();
		} catch (QueryException e) {
			throw new RuntimeException("Error in clear()", e);
		}
	}

	private void tryClear() throws QueryException
	{
		Delete delete = new Delete(table);
		connection.execute(delete.sql());
	}

}
