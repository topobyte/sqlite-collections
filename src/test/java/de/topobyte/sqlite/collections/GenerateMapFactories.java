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

public class GenerateMapFactories
{

	enum Type {

		INT("int", "Integer", ColumnClass.INT, "Int"),
		LONG("long", "Long", ColumnClass.LONG, "Long"),
		DOUBLE("double", "Double", ColumnClass.DOUBLE, "Double"),
		STRING("String", "String", ColumnClass.VARCHAR, "String"),
		BLOB("byte[]", "byte[]", ColumnClass.BLOB, "Blob");

		private String primitive;
		private String boxedType;
		private ColumnClass cc;
		private String sqlName;

		Type(String primitive, String boxedType, ColumnClass cc, String sqlName)
		{
			this.primitive = primitive;
			this.boxedType = boxedType;
			this.cc = cc;
			this.sqlName = sqlName;
		}

	}

	public static void main(String[] args)
	{
		for (Type a : Type.values()) {
			for (Type b : Type.values()) {
				System.out.println(generate(a, b));
			}
		}
	}

	private static String generate(Type a, Type b)
	{
		StringBuilder buffer = new StringBuilder();

		line(buffer, String.format("public static TableMap<%s, %s> get%s%sMap(",
				a.boxedType, b.boxedType, a.sqlName, b.sqlName));
		line(buffer,
				"    IConnection connection, String table, String columnKeys,");
		line(buffer, "    String columnValues)");
		line(buffer, "{");

		line(buffer, "  Table t = new Table(table);");
		line(buffer, String.format("  t.addColumn(ColumnClass.%s, columnKeys);",
				a.cc));
		line(buffer, String
				.format("  t.addColumn(ColumnClass.%s, columnValues);", b.cc));
		line(buffer, String.format(
				"  ArgumentSetter%s setterKeys = new ArgumentSetter%1$s();",
				a.sqlName));
		line(buffer, String.format(
				"  ArgumentSetter%s setterValues = new ArgumentSetter%1$s();",
				b.sqlName));
		line(buffer,
				String.format(
						"  ResultGetter%s getterKeys = new ResultGetter%1$s();",
						a.sqlName));
		line(buffer, String.format(
				"  ResultGetter%s getterValues = new ResultGetter%1$s();",
				b.sqlName));
		line(buffer, "  return new TableMap<>(connection, t, //");
		line(buffer, "    setterKeys, getterKeys, setterValues, getterValues,");
		line(buffer,
				"    new ArgumentSetterEntries<>(setterKeys, setterValues));");
		line(buffer, "}");
		return buffer.toString();
	}

	private static void line(StringBuilder buffer, String line)
	{
		buffer.append(line);
		buffer.append("\n");
	}

}
