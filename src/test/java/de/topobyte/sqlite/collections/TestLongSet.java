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
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.topobyte.luqe.iface.QueryException;
import de.topobyte.luqe.jdbc.database.SqliteDatabase;

public class TestLongSet
{

	private Path file;
	private SqliteDatabase database;
	private TableSet<Long> set;

	private TableSet<Long> set(SqliteDatabase database)
	{
		return SqliteCollections.getLongSet(database.getConnection(), "tset",
				"value");
	}

	@Before
	public void setup() throws IOException, QueryException
	{
		file = Files.createTempFile("sqlite-set", ".sqlite");
		database = new SqliteDatabase(file);

		set = set(database);
		set.createTable();
	}

	@After
	public void tearDown() throws SQLException, IOException
	{
		database.getJdbcConnection().close();
		Files.delete(file);
	}

	private void insertSomeData(Set<Long> set)
	{
		set.add(1L);
		set.add(2L);
	}

	@Test
	public void testBasics()
	{
		Assert.assertTrue(set.isEmpty());
		Assert.assertEquals(0, set.size());

		insertSomeData(set);

		testAssumptionsAfterInsertion(set);
	}

	private void testAssumptionsAfterInsertion(TableSet<Long> set)
	{
		Assert.assertTrue(set.contains(1L));
		Assert.assertTrue(set.contains(2L));
		Assert.assertFalse(set.contains(3L));

		Assert.assertFalse(set.isEmpty());
		Assert.assertEquals(2, set.size());
	}

	private void testClear(TableSet<Long> set)
	{
		Assert.assertFalse(set.contains(1L));
		Assert.assertFalse(set.contains(2L));
		Assert.assertFalse(set.contains(3L));

		Assert.assertTrue(set.isEmpty());
		Assert.assertEquals(0, set.size());
	}

	@Test
	public void testClear()
	{
		Assert.assertTrue(set.isEmpty());
		Assert.assertEquals(0, set.size());

		insertSomeData(set);

		testAssumptionsAfterInsertion(set);

		set.clear();

		testClear(set);
	}

	@Test
	public void testRemove()
	{
		Assert.assertTrue(set.isEmpty());
		Assert.assertEquals(0, set.size());

		insertSomeData(set);

		testAssumptionsAfterInsertion(set);

		set.remove(1L);

		Assert.assertFalse(set.contains(1L));
		Assert.assertTrue(set.contains(2L));
		Assert.assertFalse(set.contains(3L));

		Assert.assertFalse(set.isEmpty());
		Assert.assertEquals(1, set.size());
	}

	@Test
	public void testRemoveAll()
	{
		Assert.assertTrue(set.isEmpty());
		Assert.assertEquals(0, set.size());

		insertSomeData(set);

		testAssumptionsAfterInsertion(set);

		set.remove(1L);
		set.remove(2L);

		testClear(set);
	}

	@Test
	public void testAddAll()
	{
		Assert.assertTrue(set.isEmpty());
		Assert.assertEquals(0, set.size());

		Set<Long> temp = new HashSet<>();
		insertSomeData(temp);
		set.addAll(temp);

		testAssumptionsAfterInsertion(set);
	}

}
