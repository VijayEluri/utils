/*
 * utils - QueryTest.java - Copyright © 2011 David Roden
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.pterodactylus.util.database;

import java.io.IOException;
import java.io.StringWriter;

import junit.framework.TestCase;
import net.pterodactylus.util.database.Join.JoinType;
import net.pterodactylus.util.database.OrderField.Order;
import net.pterodactylus.util.database.Query.Type;
import net.pterodactylus.util.io.Closer;

/**
 * Test case for {@link Query} and related classes.
 *
 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
 */
public class QueryTest extends TestCase {

	/**
	 * Tests creation of simple SELECT queries.
	 */
	public void testSimpleSelectQueries() {
		Query query;

		query = new Query(Type.SELECT, "TEST");
		assertEquals("SELECT * FROM TEST", query);

		query.addField(new Field("ID"));
		assertEquals("SELECT ID FROM TEST", query);

		query.addField(new Field("TYPE"));
		assertEquals("SELECT ID, TYPE FROM TEST", query);

		query.addField(new Field("DATA D"));
		assertEquals("SELECT ID, TYPE, DATA D FROM TEST", query);
	}

	/**
	 * Tests SELECT queries with ORDER BY clauses.
	 */
	public void testSelectWithOrderBy() {
		Query query = new Query(Type.SELECT, "TEST");
		query.addOrderField(new OrderField(new Field("CRIT")));
		assertEquals("SELECT * FROM TEST ORDER BY CRIT ASC", query);

		query.addOrderField(new OrderField(new Field("CRIT2"), Order.DESCENDING));
		assertEquals("SELECT * FROM TEST ORDER BY CRIT ASC, CRIT2 DESC", query);

		query.addOrderField(new OrderField(new Field("CRIT3"), Order.ASCENDING));
		assertEquals("SELECT * FROM TEST ORDER BY CRIT ASC, CRIT2 DESC, CRIT3 ASC", query);

		query.addField(new Field("T"));
		assertEquals("SELECT T FROM TEST ORDER BY CRIT ASC, CRIT2 DESC, CRIT3 ASC", query);
	}

	/**
	 * Tests SELECT queries with JOINs.
	 */
	public void testSelectWithJoin() {
		Query query = new Query(Type.SELECT, "TEST");
		query.addJoin(new Join(JoinType.LEFT, "TEST2", new Field("TEST.ID"), new Field("TEST2.TEST")));
		assertEquals("SELECT * FROM TEST LEFT JOIN TEST2 ON (TEST.ID = TEST2.TEST)", query);

		query.addJoin(new Join(JoinType.RIGHT, "TEST3", new Field("TEST2.ID"), new Field("TEST3.TEST2")));
		assertEquals("SELECT * FROM TEST LEFT JOIN TEST2 ON (TEST.ID = TEST2.TEST) RIGHT JOIN TEST3 ON (TEST2.ID = TEST3.TEST2)", query);
	}

	//
	// PRIVATE METHODS
	//

	/**
	 * Asserts that the generated query matches the expected query.
	 *
	 * @param expected
	 *            The expected query
	 * @param query
	 *            The generated query
	 */
	private void assertEquals(String expected, Query query) {
		StringWriter writer = new StringWriter();
		try {
			query.render(writer);
		} catch (IOException ioe1) {
			/* won’t occur but throw anyway. */
			throw new RuntimeException("Could not render query.", ioe1);
		} finally {
			Closer.close(writer);
		}
		assertEquals(expected, writer.toString());
	}

}
