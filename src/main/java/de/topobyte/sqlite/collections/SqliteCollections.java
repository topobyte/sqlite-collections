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

	// The following methods are generated, use GenerateMapFactories to
	// regenerate

	public static TableMap<Integer, Integer> getIntIntMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.INT, columnKeys);
		t.addColumn(ColumnClass.INT, columnValues);
		ArgumentSetterInt setterKeys = new ArgumentSetterInt();
		ArgumentSetterInt setterValues = new ArgumentSetterInt();
		ResultGetterInt getterKeys = new ResultGetterInt();
		ResultGetterInt getterValues = new ResultGetterInt();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<Integer, Long> getIntLongMap(IConnection connection,
			String table, String columnKeys, String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.INT, columnKeys);
		t.addColumn(ColumnClass.LONG, columnValues);
		ArgumentSetterInt setterKeys = new ArgumentSetterInt();
		ArgumentSetterLong setterValues = new ArgumentSetterLong();
		ResultGetterInt getterKeys = new ResultGetterInt();
		ResultGetterLong getterValues = new ResultGetterLong();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<Integer, Double> getIntDoubleMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.INT, columnKeys);
		t.addColumn(ColumnClass.DOUBLE, columnValues);
		ArgumentSetterInt setterKeys = new ArgumentSetterInt();
		ArgumentSetterDouble setterValues = new ArgumentSetterDouble();
		ResultGetterInt getterKeys = new ResultGetterInt();
		ResultGetterDouble getterValues = new ResultGetterDouble();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<Integer, String> getIntStringMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.INT, columnKeys);
		t.addColumn(ColumnClass.VARCHAR, columnValues);
		ArgumentSetterInt setterKeys = new ArgumentSetterInt();
		ArgumentSetterString setterValues = new ArgumentSetterString();
		ResultGetterInt getterKeys = new ResultGetterInt();
		ResultGetterString getterValues = new ResultGetterString();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<Integer, byte[]> getIntBlobMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.INT, columnKeys);
		t.addColumn(ColumnClass.VARCHAR, columnValues);
		ArgumentSetterInt setterKeys = new ArgumentSetterInt();
		ArgumentSetterBlob setterValues = new ArgumentSetterBlob();
		ResultGetterInt getterKeys = new ResultGetterInt();
		ResultGetterBlob getterValues = new ResultGetterBlob();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<Long, Integer> getLongIntMap(IConnection connection,
			String table, String columnKeys, String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.LONG, columnKeys);
		t.addColumn(ColumnClass.INT, columnValues);
		ArgumentSetterLong setterKeys = new ArgumentSetterLong();
		ArgumentSetterInt setterValues = new ArgumentSetterInt();
		ResultGetterLong getterKeys = new ResultGetterLong();
		ResultGetterInt getterValues = new ResultGetterInt();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<Long, Long> getLongLongMap(IConnection connection,
			String table, String columnKeys, String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.LONG, columnKeys);
		t.addColumn(ColumnClass.LONG, columnValues);
		ArgumentSetterLong setterKeys = new ArgumentSetterLong();
		ArgumentSetterLong setterValues = new ArgumentSetterLong();
		ResultGetterLong getterKeys = new ResultGetterLong();
		ResultGetterLong getterValues = new ResultGetterLong();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<Long, Double> getLongDoubleMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.LONG, columnKeys);
		t.addColumn(ColumnClass.DOUBLE, columnValues);
		ArgumentSetterLong setterKeys = new ArgumentSetterLong();
		ArgumentSetterDouble setterValues = new ArgumentSetterDouble();
		ResultGetterLong getterKeys = new ResultGetterLong();
		ResultGetterDouble getterValues = new ResultGetterDouble();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

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

	public static TableMap<Long, byte[]> getLongBlobMap(IConnection connection,
			String table, String columnKeys, String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.LONG, columnKeys);
		t.addColumn(ColumnClass.VARCHAR, columnValues);
		ArgumentSetterLong setterKeys = new ArgumentSetterLong();
		ArgumentSetterBlob setterValues = new ArgumentSetterBlob();
		ResultGetterLong getterKeys = new ResultGetterLong();
		ResultGetterBlob getterValues = new ResultGetterBlob();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<Double, Integer> getDoubleIntMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.DOUBLE, columnKeys);
		t.addColumn(ColumnClass.INT, columnValues);
		ArgumentSetterDouble setterKeys = new ArgumentSetterDouble();
		ArgumentSetterInt setterValues = new ArgumentSetterInt();
		ResultGetterDouble getterKeys = new ResultGetterDouble();
		ResultGetterInt getterValues = new ResultGetterInt();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<Double, Long> getDoubleLongMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.DOUBLE, columnKeys);
		t.addColumn(ColumnClass.LONG, columnValues);
		ArgumentSetterDouble setterKeys = new ArgumentSetterDouble();
		ArgumentSetterLong setterValues = new ArgumentSetterLong();
		ResultGetterDouble getterKeys = new ResultGetterDouble();
		ResultGetterLong getterValues = new ResultGetterLong();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<Double, Double> getDoubleDoubleMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.DOUBLE, columnKeys);
		t.addColumn(ColumnClass.DOUBLE, columnValues);
		ArgumentSetterDouble setterKeys = new ArgumentSetterDouble();
		ArgumentSetterDouble setterValues = new ArgumentSetterDouble();
		ResultGetterDouble getterKeys = new ResultGetterDouble();
		ResultGetterDouble getterValues = new ResultGetterDouble();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<Double, String> getDoubleStringMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.DOUBLE, columnKeys);
		t.addColumn(ColumnClass.VARCHAR, columnValues);
		ArgumentSetterDouble setterKeys = new ArgumentSetterDouble();
		ArgumentSetterString setterValues = new ArgumentSetterString();
		ResultGetterDouble getterKeys = new ResultGetterDouble();
		ResultGetterString getterValues = new ResultGetterString();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<Double, byte[]> getDoubleBlobMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.DOUBLE, columnKeys);
		t.addColumn(ColumnClass.VARCHAR, columnValues);
		ArgumentSetterDouble setterKeys = new ArgumentSetterDouble();
		ArgumentSetterBlob setterValues = new ArgumentSetterBlob();
		ResultGetterDouble getterKeys = new ResultGetterDouble();
		ResultGetterBlob getterValues = new ResultGetterBlob();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<String, Integer> getStringIntMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.VARCHAR, columnKeys);
		t.addColumn(ColumnClass.INT, columnValues);
		ArgumentSetterString setterKeys = new ArgumentSetterString();
		ArgumentSetterInt setterValues = new ArgumentSetterInt();
		ResultGetterString getterKeys = new ResultGetterString();
		ResultGetterInt getterValues = new ResultGetterInt();
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

	public static TableMap<String, Double> getStringDoubleMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.VARCHAR, columnKeys);
		t.addColumn(ColumnClass.DOUBLE, columnValues);
		ArgumentSetterString setterKeys = new ArgumentSetterString();
		ArgumentSetterDouble setterValues = new ArgumentSetterDouble();
		ResultGetterString getterKeys = new ResultGetterString();
		ResultGetterDouble getterValues = new ResultGetterDouble();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<String, String> getStringStringMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.VARCHAR, columnKeys);
		t.addColumn(ColumnClass.VARCHAR, columnValues);
		ArgumentSetterString setterKeys = new ArgumentSetterString();
		ArgumentSetterString setterValues = new ArgumentSetterString();
		ResultGetterString getterKeys = new ResultGetterString();
		ResultGetterString getterValues = new ResultGetterString();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<String, byte[]> getStringBlobMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.VARCHAR, columnKeys);
		t.addColumn(ColumnClass.VARCHAR, columnValues);
		ArgumentSetterString setterKeys = new ArgumentSetterString();
		ArgumentSetterBlob setterValues = new ArgumentSetterBlob();
		ResultGetterString getterKeys = new ResultGetterString();
		ResultGetterBlob getterValues = new ResultGetterBlob();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<byte[], Integer> getBlobIntMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.VARCHAR, columnKeys);
		t.addColumn(ColumnClass.INT, columnValues);
		ArgumentSetterBlob setterKeys = new ArgumentSetterBlob();
		ArgumentSetterInt setterValues = new ArgumentSetterInt();
		ResultGetterBlob getterKeys = new ResultGetterBlob();
		ResultGetterInt getterValues = new ResultGetterInt();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<byte[], Long> getBlobLongMap(IConnection connection,
			String table, String columnKeys, String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.VARCHAR, columnKeys);
		t.addColumn(ColumnClass.LONG, columnValues);
		ArgumentSetterBlob setterKeys = new ArgumentSetterBlob();
		ArgumentSetterLong setterValues = new ArgumentSetterLong();
		ResultGetterBlob getterKeys = new ResultGetterBlob();
		ResultGetterLong getterValues = new ResultGetterLong();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<byte[], Double> getBlobDoubleMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.VARCHAR, columnKeys);
		t.addColumn(ColumnClass.DOUBLE, columnValues);
		ArgumentSetterBlob setterKeys = new ArgumentSetterBlob();
		ArgumentSetterDouble setterValues = new ArgumentSetterDouble();
		ResultGetterBlob getterKeys = new ResultGetterBlob();
		ResultGetterDouble getterValues = new ResultGetterDouble();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<byte[], String> getBlobStringMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.VARCHAR, columnKeys);
		t.addColumn(ColumnClass.VARCHAR, columnValues);
		ArgumentSetterBlob setterKeys = new ArgumentSetterBlob();
		ArgumentSetterString setterValues = new ArgumentSetterString();
		ResultGetterBlob getterKeys = new ResultGetterBlob();
		ResultGetterString getterValues = new ResultGetterString();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

	public static TableMap<byte[], byte[]> getBlobBlobMap(
			IConnection connection, String table, String columnKeys,
			String columnValues)
	{
		Table t = new Table(table);
		t.addColumn(ColumnClass.VARCHAR, columnKeys);
		t.addColumn(ColumnClass.VARCHAR, columnValues);
		ArgumentSetterBlob setterKeys = new ArgumentSetterBlob();
		ArgumentSetterBlob setterValues = new ArgumentSetterBlob();
		ResultGetterBlob getterKeys = new ResultGetterBlob();
		ResultGetterBlob getterValues = new ResultGetterBlob();
		return new TableMap<>(connection, t, //
				setterKeys, getterKeys, setterValues, getterValues,
				new ArgumentSetterEntries<>(setterKeys, setterValues));
	}

}
