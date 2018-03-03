package org.http4s.rho.swagger.demo

import cats.Monad
import org.http4s.rho._
import cats.implicits._

abstract class TestService[F[_] : Monad]
  extends RhoService[F] {
    GET / "hello" |>> Ok("Hello world!")
}
