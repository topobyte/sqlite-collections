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

import java.time.Duration;
import java.time.LocalDateTime;

public class DurationUtil
{

	public static void printStatus(LocalDateTime start, int i)
	{
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(start, now);
		long seconds = duration.getSeconds();
		double s = seconds + duration.getNano() / 1_000_000_000d;
		System.out.println(String.format(
				"Processed %d entries, %.2f entries per second", i, i / s));
	}

}
