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
import de.topobyte.jsqltables.index.Indexes;
import de.topobyte.jsqltables.query.Delete;
import de.topobyte.jsqltables.query.Select;
import de.topobyte.jsqltables.query.Update;
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

public class TableMap<K, V> extends AbstractTableBased implements Map<K, V>
{

	protected ArgumentSetter<K> argSetterKeys;
	protected ResultGetter<K> resultGetterKeys;
	protected ArgumentSetter<V> argSetterValues;
	protected ResultGetter<V> resultGetterValues;
	protected ArgumentSetter<Entry<K, V>> argSetterEntries;
	protected int indexKeys;
	protected int indexValues;

	public TableMap(IConnection connection, Table table,
			ArgumentSetter<K> argSetterKeys, ResultGetter<K> resultGetterKeys,
			ArgumentSetter<V> argSetterValues,
			ResultGetter<V> resultGetterValues,
			ArgumentSetter<Entry<K, V>> argSetterEntries)
	{
		this(connection, table, argSetterKeys, resultGetterKeys,
				argSetterValues, resultGetterValues, argSetterEntries, 1, 2);
	}

	public TableMap(IConnection connection, Table table,
			ArgumentSetter<K> argSetterKeys, ResultGetter<K> resultGetterKeys,
			ArgumentSetter<V> argSetterValues,
			ResultGetter<V> resultGetterValues,
			ArgumentSetter<Entry<K, V>> argSetterEntries, int indexKeys,
			int indexValues)
	{
		super(connection, table);
		this.argSetterKeys = argSetterKeys;
		this.resultGetterKeys = resultGetterKeys;
		this.argSetterValues = argSetterValues;
		this.resultGetterValues = resultGetterValues;
		this.argSetterEntries = argSetterEntries;
		this.indexKeys = indexKeys;
		this.indexValues = indexValues;
	}

	public void createTable() throws QueryException
	{
		QueryBuilder qb = new QueryBuilder(new SqliteDialect());
		String create = qb.create(table);
		connection.execute(create);
	}

	public void createKeyIndex() throws QueryException
	{
		TableColumn column = table.getColumn(indexKeys);
		String create = Indexes.createStatement(table.getName(), "index_keys",
				column.getName());
		connection.execute(create);
	}

	public void createKeyValues() throws QueryException
	{
		TableColumn column = table.getColumn(indexValues);
		String create = Indexes.createStatement(table.getName(), "index_values",
				column.getName());
		connection.execute(create);
	}

	@Override
	public boolean containsKey(Object key)
	{
		try {
			return tryContainsKey(key);
		} catch (QueryException e) {
			throw new RuntimeException("Error in containsKey()", e);
		}
	}

	protected boolean tryContainsKey(Object key) throws QueryException
	{
		return tryContainsColumn(indexKeys, key, argSetterKeys);
	}

	@Override
	public boolean containsValue(Object value)
	{
		try {
			return tryContainsValue(value);
		} catch (QueryException e) {
			throw new RuntimeException("Error in containsValue()", e);
		}
	}

	protected boolean tryContainsValue(Object value) throws QueryException
	{
		return tryContainsColumn(indexValues, value, argSetterValues);
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

	@Override
	public V get(Object key)
	{
		try {
			return tryGet(key);
		} catch (QueryException e) {
			throw new RuntimeException("Error in get()", e);
		}
	}

	protected V tryGet(Object key) throws QueryException
	{
		Select select = selectGet();
		try (IPreparedStatement stmt = connection
				.prepareStatement(select.sql())) {
			argSetterKeys.setArguments(stmt, 1, (K) key);
			try (IResultSet results = stmt.executeQuery()) {
				if (results.next()) {
					return resultGetterValues.getResult(results, 1);
				}
				return null;
			}
		}
	}

	@Override
	public V put(K key, V value)
	{
		try {
			return tryPut(key, value);
		} catch (QueryException e) {
			throw new RuntimeException("Error in put()", e);
		}
	}

	private V tryPut(K key, V value) throws QueryException
	{
		V stored = get(key);
		if (stored == null) {
			tryInsert(key, value);
		} else {
			tryUpdate(key, value);
		}
		return stored;
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

	protected void tryInsert(K key, V value) throws QueryException
	{
		String sql = insert();

		try (IPreparedStatement stmt = connection.prepareStatement(sql)) {
			argSetterEntries.setArguments(stmt, 1, new MapEntry<>(key, value));
			stmt.execute();
		}
	}

	protected String tryUpdate(K key, V value) throws QueryException
	{
		String sql = update();

		try (IPreparedStatement stmt = connection.prepareStatement(sql)) {
			argSetterEntries.setArguments(stmt, 1, new MapEntry<>(key, value));
			stmt.execute();
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m)
	{
		try {
			tryPutAll(m);
		} catch (QueryException e) {
			throw new RuntimeException("Error in putAll()", e);
		}
	}

	protected void tryPutAll(Map<? extends K, ? extends V> m)
			throws QueryException
	{
		IPreparedStatement stmtInsert = null;
		IPreparedStatement stmtUpdate = null;
		try {
			Set<? extends K> keys = m.keySet();
			for (K key : keys) {
				V value = m.get(key);

				V stored = get(key);
				if (stored == null) {
					if (stmtInsert == null) {
						String insert = insert();
						stmtInsert = connection.prepareStatement(insert);
					}
					argSetterEntries.setArguments(stmtInsert, 1,
							new MapEntry<>(key, value));
					stmtInsert.execute();
				} else {
					if (stmtUpdate == null) {
						String update = update();
						stmtUpdate = connection.prepareStatement(update);
					}
					argSetterEntries.setArguments(stmtUpdate, 1,
							new MapEntry<>(key, value));
					stmtUpdate.execute();
				}
			}
		} catch (Throwable t) {
			throw (t);
		} finally {
			CloseUtils.closeSilently(stmtInsert);
			CloseUtils.closeSilently(stmtUpdate);
		}
	}

	@Override
	public V remove(Object key)
	{
		V stored = get(key);
		if (stored == null) {
			return null;
		}
		try {
			tryRemove(key);
		} catch (QueryException e) {
			throw new RuntimeException("Error in remove()", e);
		}
		return stored;
	}

	protected void tryRemove(Object key) throws QueryException
	{
		Delete delete = new Delete(table);
		delete.where(new SingleCondition(null,
				table.getColumn(indexKeys).getName(), Comparison.EQUAL));

		try (IPreparedStatement stmt = connection
				.prepareStatement(delete.sql())) {
			argSetterKeys.setArguments(stmt, 1, (K) key);
			stmt.execute();
		}
	}

	@Override
	public Set<Entry<K, V>> entrySet()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public TableSet<K> keySet()
	{
		return new TableSet<>(connection, table, argSetterKeys,
				resultGetterKeys, 1);
	}

	@Override
	public TableSet<V> values()
	{
		return new TableSet<>(connection, table, argSetterValues,
				resultGetterValues, 2);
	}

}
