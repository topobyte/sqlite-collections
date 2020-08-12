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

import de.topobyte.luqe.iface.IPreparedStatement;
import de.topobyte.luqe.iface.QueryException;

public class ArgumentSetterBlob implements ArgumentSetter<byte[]>
{

	@Override
	public void setArguments(IPreparedStatement stmt, int index, byte[] object)
			throws QueryException
	{
		stmt.setBlob(index, object);
	}

}
