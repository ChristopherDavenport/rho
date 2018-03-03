package org.http4s

import org.http4s.rho.bits.PathAST._
import shapeless.HList

package object rho extends Http4s {
  type RhoMiddleware[F[_]] = Seq[RhoRoute[F, _ <: HList]] => Seq[RhoRoute[F, _ <: HList]]

  val PathEmpty: PathRule = PathMatch("")
}

