/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.kernel.impl.store.format.highlimit;

import org.neo4j.kernel.impl.store.format.LimitedRecordGenerators;
import org.neo4j.kernel.impl.store.format.RecordFormatTest;
import org.neo4j.kernel.impl.store.format.RecordGenerators;
import org.neo4j.kernel.impl.store.format.highlimit.HighLimit;

public class HighLimitRecordFormatTest extends RecordFormatTest
{
    protected static final RecordGenerators _50_BIT_LIMITS = new LimitedRecordGenerators( random, 50, 50, 50, 16, NULL );

    public HighLimitRecordFormatTest()
    {
        super( HighLimit.RECORD_FORMATS, _50_BIT_LIMITS );
    }
}