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

package de.topobyte.sqlite.collections.testutil;

import java.util.Random;

public class RandomData
{

	public static String string(Random r, int len)
	{
		StringBuilder buffer = new StringBuilder();

		int min = 97; // 'a'
		int max = 122; // 'z'

		for (int i = 0; i < len; i++) {
			buffer.append((char) (min + r.nextInt(max - min)));
		}

		return buffer.toString();
	}

}
