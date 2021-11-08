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
import java.util.Set;

import de.topobyte.jsqltables.dialect.SqliteDialect;
import de.topobyte.jsqltables.index.Indexes;
import de.topobyte.jsqltables.query.Delete;
import de.topobyte.jsqltables.query.Select;
import de.topobyte.jsqltables.query.select.NormalColumn;
import de.topobyte.jsqltables.query.where.Comparison;
import de.topobyte.jsqltables.query.where.SingleCondition;
import de.topobyte.jsqltables.table.QueryBuilder;
import de.topobyte.jsqltables.table.Table;
import de.topobyte.jsqltables.table.TableColumn;
import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.iface.IPreparedStatement;
import de.topobyte.luqe.iface.IResultSet;
import de.topobyte.luqe.iface.QueryException;

public class TableSet<E> extends AbstractTableBased implements Set<E>
{

	protected int indexValues;

	private ArgumentSetter<E> argSetter;
	private ResultGetter<E> resultGetter;

	public TableSet(IConnection connection, Table table,
			ArgumentSetter<E> argSetter, ResultGetter<E> resultGetter)
	{
		this(connection, table, argSetter, resultGetter, 1);
	}

	public TableSet(IConnection connection, Table table,
			ArgumentSetter<E> argSetter, ResultGetter<E> resultGetter,
			int indexValues)
	{
		super(connection, table);
		this.argSetter = argSetter;
		this.resultGetter = resultGetter;
		this.indexValues = indexValues;
	}

	public void createTable() throws QueryException
	{
		createTable(false);
	}

	public void createTable(boolean ignoreExisting) throws QueryException
	{
		QueryBuilder qb = new QueryBuilder(new SqliteDialect());
		String create = qb.create(table, ignoreExisting);
		connection.execute(create);
	}

	public void createIndex() throws QueryException
	{
		createIndex(false);
	}

	public void createIndex(boolean ignoreExisting) throws QueryException
	{
		TableColumn column = table.getColumn(indexValues);
		String create = Indexes.createStatement(table.getName(), "index",
				ignoreExisting, column.getName());
		connection.execute(create);
	}

	@Override
	public boolean contains(Object o)
	{
		try {
			return tryContains(o);
		} catch (QueryException e) {
			throw new RuntimeException("Error in contains()", e);
		}
	}

	protected boolean tryContains(Object o) throws QueryException
	{
		return tryContainsColumn(indexValues, o, argSetter);
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
	public boolean add(E value)
	{
		if (contains(value)) {
			return false;
		}
		try {
			tryInsert(value);
		} catch (QueryException e) {
			throw new RuntimeException("Error in add()", e);
		}
		return true;
	}

	protected String insert()
	{
		QueryBuilder qb = new QueryBuilder(new SqliteDialect());
		return qb.insert(table);
	}

	protected void tryInsert(E value) throws QueryException
	{
		String sql = insert();

		try (IPreparedStatement stmt = connection.prepareStatement(sql)) {
			argSetter.setArguments(stmt, 1, value);
			stmt.execute();
		}
	}

	@Override
	public boolean remove(Object o)
	{
		try {
			return tryRemove(o);
		} catch (QueryException e) {
			throw new RuntimeException("Error in remove()", e);
		}
	}

	private boolean tryRemove(Object o) throws QueryException
	{
		if (!contains(o)) {
			return false;
		}

		Delete delete = new Delete(table);
		delete.where(new SingleCondition(null,
				table.getColumn(indexValues).getName(), Comparison.EQUAL));

		try (IPreparedStatement stmt = connection
				.prepareStatement(delete.sql())) {
			argSetter.setArguments(stmt, 1, (E) o);
			stmt.execute();
		}
		return true;
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		try {
			return tryAddAll(c);
		} catch (Throwable e) {
			throw new RuntimeException("Error in addAll()", e);
		}
	}

	private boolean tryAddAll(Collection<? extends E> c) throws Throwable
	{
		boolean changed = false;
		IPreparedStatement stmtInsert = null;
		try {
			for (E value : c) {
				// TODO: use prepared-optimized contains()
				if (contains(value)) {
					continue;
				}
				if (stmtInsert == null) {
					String insert = insert();
					stmtInsert = connection.prepareStatement(insert);
					changed = true;
				}
				argSetter.setArguments(stmtInsert, 1, value);
				stmtInsert.execute();
			}
			return changed;
		} catch (Throwable t) {
			throw (t);
		} finally {
			CloseUtils.closeSilently(stmtInsert);
		}
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
	public CloseableIterator<E> iterator()
	{
		try {
			return tryIterator();
		} catch (QueryException e) {
			throw new RuntimeException("Error in iterator()", e);
		}
	}

	protected CloseableIterator<E> tryIterator() throws QueryException
	{
		String col = table.getColumn(indexValues).getName();
		Select select = selectAll(col);
		IPreparedStatement stmt = connection.prepareStatement(select.sql());
		IResultSet results = stmt.executeQuery();
		return Iterators.iterator(stmt, results, resultGetter);
	}

	protected Select selectAll(String col)
	{
		Select select = new Select(table);
		select.addSelectColumn(new NormalColumn(select.getMainTable(), col));
		return select;
	}

}
