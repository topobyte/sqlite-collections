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

import de.topobyte.jsqltables.table.ColumnClass;
import de.topobyte.jsqltables.table.Table;
import de.topobyte.luqe.iface.IConnection;

public class SqliteCollections
{

	public static TableMap<Long, String> getLongStringMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.LONG, columnKeys);
		t.addColumn(ColumnClass.VARCHAR, columnValues);
		ArgumentSetterLong setterKeys = new ArgumentSetterLong();
		ArgumentSetterString setterValues = new ArgumentSetterString();
		ResultGetterLong getterKeys = new ResultGetterLong();
		ResultGetterString getterValues = new ResultGetterString();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<String, Long> getStringLongMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.VARCHAR, columnKeys);
		t.addColumn(ColumnClass.LONG, columnValues);
		ArgumentSetterString setterKeys = new ArgumentSetterString();
		ArgumentSetterLong setterValues = new ArgumentSetterLong();
		ResultGetterString getterKeys = new ResultGetterString();
		ResultGetterLong getterValues = new ResultGetterLong();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableSet<Integer> intSet(IConnection connection, Table table)
	{
		return new TableSet<>(connection, table, new ArgumentSetterInt(),
				new ResultGetterInt());
	}

	public static TableSet<Integer> intSet(IConnection connection, Table table,
			int indexValues)
	{
		return new TableSet<>(connection, table, new ArgumentSetterInt(),
				new ResultGetterInt(), indexValues);
	}

	public static TableSet<Long> longSet(IConnection connection, Table table)
	{
		return new TableSet<>(connection, table, new ArgumentSetterLong(),
				new ResultGetterLong());
	}

	public static TableSet<Long> longSet(IConnection connection, Table table,
			int indexValues)
	{
		return new TableSet<>(connection, table, new ArgumentSetterLong(),
				new ResultGetterLong(), indexValues);
	}

	public static TableSet<Double> doubleSet(IConnection connection,
			Table table)
	{
		return new TableSet<>(connection, table, new ArgumentSetterDouble(),
				new ResultGetterDouble());
	}

	public static TableSet<Double> doubleSet(IConnection connection,
			Table table, int indexValues)
	{
		return new TableSet<>(connection, table, new ArgumentSetterDouble(),
				new ResultGetterDouble(), indexValues);
	}

	public static TableSet<String> stringSet(IConnection connection,
			Table table)
	{
		return new TableSet<>(connection, table, new ArgumentSetterString(),
				new ResultGetterString());
	}

	public static TableSet<String> stringSet(IConnection connection,
			Table table, int indexValues)
	{
		return new TableSet<>(connection, table, new ArgumentSetterString(),
				new ResultGetterString(), indexValues);
	}

	public static TableSet<byte[]> blobSet(IConnection connection, Table table)
	{
		return new TableSet<>(connection, table, new ArgumentSetterBlob(),
				new ResultGetterBlob());
	}

	public static TableSet<byte[]> blobSet(IConnection connection, Table table,
			int indexValues)
	{
		return new TableSet<>(connection, table, new ArgumentSetterBlob(),
				new ResultGetterBlob(), indexValues);
	}

}
