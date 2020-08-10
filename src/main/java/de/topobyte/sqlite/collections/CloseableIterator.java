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

import java.io.Closeable;
import java.io.IOException;

import de.topobyte.luqe.iface.IPreparedStatement;
import de.topobyte.luqe.iface.IResultSet;
import de.topobyte.luqe.iface.QueryException;
import de.topobyte.luqe.util.ResultsIterator;

public class CloseableIterator<T> extends ResultsIterator<T>
		implements Closeable
{

	private IPreparedStatement stmt;
	private IResultSet results;
	private ResultGetter<T> resultsGetter;

	public CloseableIterator(IPreparedStatement stmt, IResultSet results,
			ResultGetter<T> resultsGetter)
	{
		super(results);
		this.stmt = stmt;
		this.results = results;
		this.resultsGetter = resultsGetter;
	}

	@Override
	public T retrieve(IResultSet results) throws QueryException
	{
		return resultsGetter.getResult(results);
	}

	@Override
	public void close() throws IOException
	{
		if (results != null) {
			try {
				results.close();
			} catch (QueryException e) {
				// ignore silently
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (QueryException e) {
				// ignore silently
			}
		}
	}

}
