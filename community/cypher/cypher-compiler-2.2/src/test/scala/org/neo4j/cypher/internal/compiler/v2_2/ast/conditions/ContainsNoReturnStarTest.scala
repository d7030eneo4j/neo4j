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
package org.neo4j.cypher.internal.compiler.v2_2.ast.conditions

import org.neo4j.cypher.internal.commons.CypherFunSuite
import org.neo4j.cypher.internal.compiler.v2_2.ast._

class ContainsNoReturnStarTest extends CypherFunSuite with AstConstructionTestSupport {

  test("Happy when not finding ReturnAll()") {
    val ast: ASTNode = Match(optional = false, Pattern(Seq(EveryPath(NodePattern(None, Seq(), None, naked = true)_)))_, Seq(), None)_

    containsNoReturnStar(ast) should equal(Seq())
  }

  test("Fails when finding ReturnAll()") {
    val ast: ASTNode = Return(false, ReturnAll()_, None, None, None)_

    containsNoReturnStar(ast) should equal(Seq("Expected none but found ReturnAll at position line 1, column 0"))
  }
}
