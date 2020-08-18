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

public enum Type {

	INT("int", "Integer", ColumnClass.INT, "Int"),
	LONG("long", "Long", ColumnClass.LONG, "Long"),
	DOUBLE("double", "Double", ColumnClass.DOUBLE, "Double"),
	STRING("String", "String", ColumnClass.VARCHAR, "String"),
	BLOB("byte[]", "byte[]", ColumnClass.BLOB, "Blob");

	public String primitive;
	public String boxedType;
	public ColumnClass cc;
	public String sqlName;

	Type(String primitive, String boxedType, ColumnClass cc, String sqlName)
	{
		this.primitive = primitive;
		this.boxedType = boxedType;
		this.cc = cc;
		this.sqlName = sqlName;
	}

}
