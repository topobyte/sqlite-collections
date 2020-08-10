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
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import de.topobyte.luqe.iface.QueryException;
import de.topobyte.luqe.jdbc.database.SqliteDatabase;

public class TestLongStringMap
{

	private TableMap<Long, String> map(SqliteDatabase database)
	{
		return SqliteCollections.getLongStringMap(database.getConnection(),
				"map", "key", "value");
	}

	@Test
	public void testBasics() throws IOException, QueryException, SQLException
	{
		Path file = Files.createTempFile("sqlite-map", ".sqlite");
		SqliteDatabase database = new SqliteDatabase(file);

		TableMap<Long, String> map = map(database);
		map.createTable();
		database.getJdbcConnection().commit();

		Assert.assertTrue(map.isEmpty());
		Assert.assertEquals(0, map.size());

		map.put(1L, "value 1");
		map.put(2L, "value 2");

		Assert.assertEquals("value 1", map.get(1L));
		Assert.assertEquals("value 2", map.get(2L));

		Assert.assertTrue(map.containsKey(1L));
		Assert.assertTrue(map.containsKey(2L));
		Assert.assertFalse(map.containsKey(3L));

		Assert.assertTrue(map.containsValue("value 1"));
		Assert.assertTrue(map.containsValue("value 2"));
		Assert.assertFalse(map.containsValue("value 3"));

		Assert.assertFalse(map.isEmpty());
		Assert.assertEquals(2, map.size());

		database.getJdbcConnection().close();
		Files.delete(file);
	}

	@Test
	public void testKeySet() throws IOException, QueryException, SQLException
	{
		Path file = Files.createTempFile("sqlite-map", ".sqlite");
		SqliteDatabase database = new SqliteDatabase(file);

		TableMap<Long, String> map = map(database);
		map.createTable();
		database.getJdbcConnection().commit();

		map.put(1L, "value 1");
		map.put(2L, "value 2");

		TableSet<Long> keys = map.keySet();
		Assert.assertTrue(keys.contains(1L));
		Assert.assertTrue(keys.contains(2L));
		Assert.assertFalse(keys.contains(3L));

		try (CloseableIterator<Long> iterator = keys.iterator()) {
			Set<Long> iterated = IteratorUtil.toSet(keys.iterator());
			Assert.assertEquals(2, iterated.size());
		}

		try (CloseableIterator<?> iterator = keys.iterator()) {
			Assert.assertEquals(2, IteratorUtil.count(iterator));
		}

		database.getJdbcConnection().close();
		Files.delete(file);
	}

	@Test
	public void testValues() throws IOException, QueryException, SQLException
	{
		Path file = Files.createTempFile("sqlite-map", ".sqlite");
		SqliteDatabase database = new SqliteDatabase(file);

		TableMap<Long, String> map = map(database);
		map.createTable();
		database.getJdbcConnection().commit();

		map.put(1L, "value 1");
		map.put(2L, "value 2");

		TableSet<String> values = map.values();
		Assert.assertTrue(values.contains("value 1"));
		Assert.assertTrue(values.contains("value 2"));
		Assert.assertFalse(values.contains("value 3"));

		try (CloseableIterator<?> iterator = values.iterator()) {
			Set<String> iterated = IteratorUtil.toSet(values.iterator());
			Assert.assertEquals(2, iterated.size());
		}

		try (CloseableIterator<String> iterator = values.iterator()) {
			Assert.assertEquals(2, IteratorUtil.count(iterator));
		}

		database.getJdbcConnection().close();
		Files.delete(file);
	}

}
