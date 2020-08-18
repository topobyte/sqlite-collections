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

public class GenerateSetFactories
{

	public static void main(String[] args)
	{
		for (Type a : Type.values()) {
			System.out.println(generate(a));
		}
	}

	private static String generate(Type a)
	{
		StringBuilder buffer = new StringBuilder();

		line(buffer, String.format("public static TableSet<%s> get%sSet(",
				a.boxedType, a.sqlName));
		line(buffer,
				"    IConnection connection, String table, String columnValues)");
		line(buffer, "{");

		line(buffer, "  Table t = new Table(table);");
		line(buffer, String
				.format("  t.addColumn(ColumnClass.%s, columnValues);", a.cc));
		line(buffer, String.format(
				"  ArgumentSetter%s setterValues = new ArgumentSetter%1$s();",
				a.sqlName));
		line(buffer, String.format(
				"  ResultGetter%s getterValues = new ResultGetter%1$s();",
				a.sqlName));
		line(buffer,
				"  return new TableSet<>(connection, t, setterValues, getterValues);");
		line(buffer, "}");
		return buffer.toString();
	}

	private static void line(StringBuilder buffer, String line)
	{
		buffer.append(line);
		buffer.append("\n");
	}

}
