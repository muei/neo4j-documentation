/*
 * Copyright (c) 2002-2016 "Neo Technology,"
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
package org.neo4j.cypher.internal.compiler.v3_0.commands.expressions

import org.neo4j.cypher.internal.compiler.v3_0.helpers.IsMap
import org.neo4j.cypher.internal.compiler.v3_0.pipes.QueryState
import org.neo4j.cypher.internal.compiler.v3_0.symbols.SymbolTable
import org.neo4j.cypher.internal.compiler.v3_0.{CRS, CartesianPoint, ExecutionContext, GeographicPoint}
import org.neo4j.cypher.internal.frontend.v3_0.CypherTypeException
import org.neo4j.cypher.internal.frontend.v3_0.symbols._

case class PointFunction(data: Expression) extends NullInNullOutExpression(data) {

  override def compute(value: Any, ctx: ExecutionContext)(implicit state: QueryState): Any = value match {
    case IsMap(mapCreator) =>
      val map = mapCreator(state.query)
      map.getOrElse("crs", CRS.WGS84.name) match {
        case CRS.Cartesian.name =>
          val x = map("x").asInstanceOf[Double]
          val y = map("y").asInstanceOf[Double]
          CartesianPoint(x, y)

        case CRS.WGS84.name =>
          val longitude = map("longitude").asInstanceOf[Double]
          val latitude = map("latitude").asInstanceOf[Double]
          GeographicPoint(longitude, latitude, CRS.WGS84)
      }
    case x => throw new CypherTypeException(s"Expected a map but got $x")
  }

  override def rewrite(f: (Expression) => Expression) = f(PointFunction(data.rewrite(f)))

  override def arguments = data.arguments

  override def calculateType(symbols: SymbolTable) = CTPoint

  override def symbolTableDependencies = data.symbolTableDependencies

  override def toString = "Point(" + data + ")"
}