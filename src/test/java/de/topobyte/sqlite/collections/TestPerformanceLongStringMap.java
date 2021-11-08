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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.topobyte.luqe.iface.QueryException;
import de.topobyte.luqe.jdbc.database.SqliteDatabase;
import de.topobyte.sqlite.collections.testutil.DurationUtil;
import de.topobyte.sqlite.collections.testutil.RandomData;

public class TestPerformanceLongStringMap
{

	private Path file;
	private SqliteDatabase database;
	private TableMap<Long, String> map;

	private TableMap<Long, String> map(SqliteDatabase database)
	{
		return SqliteCollections.getLongStringMap(database.getConnection(),
				"map", "key", "value");
	}

	// We achieve optimal performance, when creating an index (1) and not
	// committing during insertion (2).

	@Before
	public void setup() throws IOException, QueryException
	{
		file = Files.createTempFile("sqlite-map", ".sqlite");
		database = new SqliteDatabase(file);

		map = map(database);
		map.createTable();
		// (1) create the index
		map.createIndexKeys();
	}

	@After
	public void tearDown() throws SQLException, IOException
	{
		database.getJdbcConnection().close();
		Files.delete(file);
	}

	@Test
	public void test() throws SQLException
	{
		LocalDateTime start = LocalDateTime.now();

		Random r = new Random();
		int i;
		for (i = 0; i < 10000; i++) {
			long key = r.nextLong();
			String value = RandomData.string(r, 100);
			map.put(key, value);

			if (i > 0 && (i % 1000) != 0) {
				continue;
			}

			DurationUtil.printStatus(start, i);
		}

		// (2) only commit once
		database.getJdbcConnection().commit();
		DurationUtil.printStatus(start, i);
	}

}
