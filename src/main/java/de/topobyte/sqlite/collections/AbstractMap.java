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
import java.util.Set;

import de.topobyte.jsqltables.dialect.SqliteDialect;
import de.topobyte.jsqltables.query.Delete;
import de.topobyte.jsqltables.query.Select;
import de.topobyte.jsqltables.query.Update;
import de.topobyte.jsqltables.query.select.NormalColumn;
import de.topobyte.jsqltables.query.where.Comparison;
import de.topobyte.jsqltables.query.where.SingleCondition;
import de.topobyte.jsqltables.table.QueryBuilder;
import de.topobyte.jsqltables.table.Table;
import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.iface.IPreparedStatement;
import de.topobyte.luqe.iface.IResultSet;
import de.topobyte.luqe.iface.QueryException;

public abstract class AbstractMap<K, V> extends AbstractTableBased
		implements Map<K, V>
{

	protected int indexKeys;
	protected int indexValues;

	public AbstractMap(IConnection connection, Table table)
	{
		this(connection, table, 1, 2);
	}

	public AbstractMap(IConnection connection, Table table, int indexKeys,
			int indexValues)
	{
		super(connection, table);
		this.indexKeys = indexKeys;
		this.indexValues = indexValues;
	}

	public void createTable() throws QueryException
	{
		QueryBuilder qb = new QueryBuilder(new SqliteDialect());
		String create = qb.create(table);
		connection.execute(create);
	}

	protected boolean tryContainsKey(ArgumentSetter argSetter)
			throws QueryException
	{
		return tryContainsColumn(indexKeys, argSetter);
	}

	protected boolean tryContainsValue(ArgumentSetter argSetter)
			throws QueryException
	{
		return tryContainsColumn(indexValues, argSetter);
	}

	protected Select selectGet()
	{
		String colKey = table.getColumn(indexKeys).getName();
		String colValue = table.getColumn(indexValues).getName();

		Select select = new Select(table);
		select.addSelectColumn(
				new NormalColumn(select.getMainTable(), colValue));
		select.where(new SingleCondition(select.getMainTable(), colKey,
				Comparison.EQUAL));
		select.limit(1);
		return select;
	}

	protected V tryGet(ArgumentSetter argSetter, ResultGetter<V> resultGetter)
			throws QueryException
	{
		Select select = selectGet();
		try (IPreparedStatement stmt = connection
				.prepareStatement(select.sql())) {
			argSetter.setArguments(stmt);
			try (IResultSet results = stmt.executeQuery()) {
				if (results.next()) {
					return resultGetter.getResult(results);
				}
				return null;
			}
		}
	}

	protected String insert()
	{
		QueryBuilder qb = new QueryBuilder(new SqliteDialect());
		return qb.insert(table);
	}

	protected String update()
	{
		String colKey = table.getColumn(indexKeys).getName();
		String colValue = table.getColumn(indexValues).getName();

		Update update = new Update(table);
		update.addColum(colKey);
		update.addColum(colValue);
		update.where(new SingleCondition(null, colKey, Comparison.EQUAL));

		return update.sql();
	}

	protected void tryInsert(ArgumentSetter argSetter) throws QueryException
	{
		String sql = insert();

		try (IPreparedStatement stmt = connection.prepareStatement(sql)) {
			argSetter.setArguments(stmt);
			stmt.execute();
		}
	}

	protected String tryUpdate(ArgumentSetter argSetter) throws QueryException
	{
		String sql = update();

		try (IPreparedStatement stmt = connection.prepareStatement(sql)) {
			argSetter.setArguments(stmt);
			stmt.execute();
		}
		return null;
	}

	protected void tryPutAll(Map<? extends K, ? extends V> m,
			BiArgumentSetter<K, V> argSetter) throws QueryException
	{
		IPreparedStatement stmtInsert = null;
		IPreparedStatement stmtUpdate = null;

		Set<? extends K> keys = m.keySet();
		for (K key : keys) {
			V value = m.get(key);

			V stored = get(key);
			if (stored == null) {
				if (stmtInsert == null) {
					String insert = insert();
					stmtInsert = connection.prepareStatement(insert);
				}
				argSetter.setArguments(stmtInsert, key, value);
				stmtInsert.execute();
			} else {
				if (stmtUpdate == null) {
					String update = update();
					stmtUpdate = connection.prepareStatement(update);
				}
				argSetter.setArguments(stmtUpdate, key, value);
				stmtUpdate.execute();
			}
		}
	}

	protected void tryRemove(ArgumentSetter argSetter) throws QueryException
	{
		Delete delete = new Delete(table);
		delete.where(new SingleCondition(null,
				table.getColumn(indexKeys).getName(), Comparison.EQUAL));

		try (IPreparedStatement stmt = connection
				.prepareStatement(delete.sql())) {
			argSetter.setArguments(stmt);
			stmt.execute();
		}
	}

	@Override
	public Set<Entry<K, V>> entrySet()
	{
		throw new UnsupportedOperationException();
	}

}
