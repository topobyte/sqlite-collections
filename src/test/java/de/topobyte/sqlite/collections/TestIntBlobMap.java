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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.topobyte.luqe.iface.QueryException;
import de.topobyte.luqe.jdbc.database.SqliteDatabase;

public class TestIntBlobMap
{

	private Path file;
	private SqliteDatabase database;
	private TableMap<Integer, byte[]> map;

	private TableMap<Integer, byte[]> map(SqliteDatabase database)
	{
		return SqliteCollections.getIntBlobMap(database.getConnection(), "map",
				"key", "value");
	}

	@Before
	public void setup() throws IOException, QueryException
	{
		file = Files.createTempFile("sqlite-map", ".sqlite");
		database = new SqliteDatabase(file);

		map = map(database);
		map.createTable();
	}

	@After
	public void tearDown() throws SQLException, IOException
	{
		database.getJdbcConnection().close();
		Files.delete(file);
	}

	private void insertSomeData(Map<Integer, byte[]> map)
	{
		map.put(1, "value 1".getBytes());
		map.put(2, "value 2".getBytes());
	}

	@Test
	public void testBasics()
	{
		Assert.assertTrue(map.isEmpty());
		Assert.assertEquals(0, map.size());

		insertSomeData(map);

		testAssumptionsAfterInsertion(map);
	}

	private void testAssumptionsAfterInsertion(TableMap<Integer, byte[]> map)
	{
		Assert.assertArrayEquals("value 1".getBytes(), map.get(1));
		Assert.assertArrayEquals("value 2".getBytes(), map.get(2));

		Assert.assertTrue(map.containsKey(1));
		Assert.assertTrue(map.containsKey(2));
		Assert.assertFalse(map.containsKey(3));

		Assert.assertTrue(map.containsValue("value 1".getBytes()));
		Assert.assertTrue(map.containsValue("value 2".getBytes()));
		Assert.assertFalse(map.containsValue("value 3".getBytes()));

		Assert.assertFalse(map.isEmpty());
		Assert.assertEquals(2, map.size());
	}

	private void testClear(TableMap<Integer, byte[]> map)
	{
		Assert.assertEquals(null, map.get(1));
		Assert.assertEquals(null, map.get(2));

		Assert.assertFalse(map.containsKey(1));
		Assert.assertFalse(map.containsKey(2));
		Assert.assertFalse(map.containsKey(3));

		Assert.assertFalse(map.containsValue("value 1".getBytes()));
		Assert.assertFalse(map.containsValue("value 2".getBytes()));
		Assert.assertFalse(map.containsValue("value 3".getBytes()));

		Assert.assertTrue(map.isEmpty());
		Assert.assertEquals(0, map.size());
	}

	@Test
	public void testKeySet()
	{
		insertSomeData(map);

		TableSet<Integer> keys = map.keySet();
		Assert.assertTrue(keys.contains(1));
		Assert.assertTrue(keys.contains(2));
		Assert.assertFalse(keys.contains(3));

		keys.iterator();

		try (CloseableIterator<Integer> iterator = keys.iterator()) {
			Set<Integer> iterated = IteratorUtil.toSet(keys.iterator());
			Assert.assertEquals(2, iterated.size());
		}

		try (CloseableIterator<?> iterator = keys.iterator()) {
			Assert.assertEquals(2, IteratorUtil.count(iterator));
		}
	}

	@Test
	public void testValues()
	{
		insertSomeData(map);

		TableSet<byte[]> values = map.values();
		Assert.assertTrue(values.contains("value 1".getBytes()));
		Assert.assertTrue(values.contains("value 2".getBytes()));
		Assert.assertFalse(values.contains("value 3".getBytes()));

		try (CloseableIterator<?> iterator = values.iterator()) {
			Set<byte[]> iterated = IteratorUtil.toSet(values.iterator());
			Assert.assertEquals(2, iterated.size());
		}

		try (CloseableIterator<byte[]> iterator = values.iterator()) {
			Assert.assertEquals(2, IteratorUtil.count(iterator));
		}
	}

	@Test
	public void testClear()
	{
		Assert.assertTrue(map.isEmpty());
		Assert.assertEquals(0, map.size());

		insertSomeData(map);

		testAssumptionsAfterInsertion(map);

		map.clear();

		testClear(map);
	}

	@Test
	public void testRemove()
	{
		Assert.assertTrue(map.isEmpty());
		Assert.assertEquals(0, map.size());

		insertSomeData(map);

		testAssumptionsAfterInsertion(map);

		map.remove(1);

		Assert.assertEquals(null, map.get(1));
		Assert.assertArrayEquals("value 2".getBytes(), map.get(2));

		Assert.assertFalse(map.containsKey(1));
		Assert.assertTrue(map.containsKey(2));
		Assert.assertFalse(map.containsKey(3));

		Assert.assertFalse(map.containsValue("value 1".getBytes()));
		Assert.assertTrue(map.containsValue("value 2".getBytes()));
		Assert.assertFalse(map.containsValue("value 3".getBytes()));

		Assert.assertFalse(map.isEmpty());
		Assert.assertEquals(1, map.size());
	}

	@Test
	public void testRemoveAll()
	{
		Assert.assertTrue(map.isEmpty());
		Assert.assertEquals(0, map.size());

		insertSomeData(map);

		testAssumptionsAfterInsertion(map);

		map.remove(1);
		map.remove(2);

		testClear(map);
	}

	@Test
	public void testPutAll()
	{
		Assert.assertTrue(map.isEmpty());
		Assert.assertEquals(0, map.size());

		Map<Integer, byte[]> temp = new HashMap<>();
		insertSomeData(temp);
		map.putAll(temp);

		testAssumptionsAfterInsertion(map);
	}

}
