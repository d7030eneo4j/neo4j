/**
 * Copyright (c) 2002-2014 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
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
package org.neo4j.kernel.impl.nioneo.xa;

import org.junit.Test;

import org.neo4j.kernel.impl.nioneo.xa.command.PhysicalLogNeoCommandReaderV0;
import org.neo4j.kernel.impl.nioneo.xa.command.PhysicalLogNeoCommandReaderV1;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CommandReaderFactoryTest
{
    @Test
    public void testReturnsV0ReaderForVersion0() throws Exception
    {
        // GIVEN
        CommandReaderFactory factory = new CommandReaderFactory.Default();

        // WHEN
        CommandReader reader = factory.newInstance( (byte) 0 );

        // THEN
        assertTrue( reader instanceof PhysicalLogNeoCommandReaderV0 );
    }

    @Test
    public void testReturnsV1ReaderForVersion1() throws Exception
    {
        // GIVEN
        CommandReaderFactory factory = new CommandReaderFactory.Default();

        // WHEN
        CommandReader reader = factory.newInstance( (byte) -1 );

        // THEN
        assertTrue( reader instanceof PhysicalLogNeoCommandReaderV1 );
    }

    @Test
    public void testThrowsExceptionForNonExistingVersion() throws Exception
    {
        // GIVEN
        CommandReaderFactory factory = new CommandReaderFactory.Default();

        // WHEN
        try
        {
            factory.newInstance( (byte) -5 );
            fail();
        }
        catch( IllegalArgumentException e)
        {
            // THEN
            // good
        }
    }
}
