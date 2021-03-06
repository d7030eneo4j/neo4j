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
package org.neo4j.cypher.internal.compiler.v2_2

import org.neo4j.cypher.internal.PlanType
import org.neo4j.cypher.internal.compiler.v2_2.ast.Statement
import org.neo4j.cypher.internal.compiler.v2_2.ast.convert.commands.StatementConverters
import org.neo4j.cypher.internal.compiler.v2_2.commands.AbstractQuery
import org.neo4j.cypher.internal.compiler.v2_2.planner.SemanticTable
import StatementConverters._

case class PreparedQuery(statement: Statement,
                         queryText: String,
                         extractedParams: Map[String, Any],
                         planType: PlanType)(val semanticTable: SemanticTable) {

  def abstractQuery: AbstractQuery = statement.asQuery.setQueryText(queryText)

  def isPeriodicCommit = statement match {
    case ast.Query(Some(_), _) => true
    case _ => false
  }

  def rewrite(rewriter: Rewriter): PreparedQuery = copy(statement = statement.endoRewrite(rewriter))(semanticTable)
}
