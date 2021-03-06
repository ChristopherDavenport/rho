package org.http4s.rho

import org.http4s.headers._
import org.http4s.rho.io._
import org.http4s.{AttributeKey, HttpDate}
import org.specs2.mutable.Specification

class ResultSpec extends Specification {

  "ResultSyntax" should {
    "Add headers" in {
      val date = Date(HttpDate.Epoch)
      val resp = Ok("Foo")
        .map(_.putHeaders(date))
        .unsafeRunSync()
        .resp

      resp.headers.get(Date) must beSome(date)

      val respNow = Ok("Foo")
        .map(_.putHeaders(date))
        .unsafeRunSync()
        .resp

      respNow.headers.get(Date) must beSome(date)
    }

    "Add atributes" in {
      val attrKey = AttributeKey[String]
      val resp = Ok("Foo")
        .map(_.withAttribute(attrKey, "foo"))
        .unsafeRunSync()
        .resp

      resp.attributes.get(attrKey) must beSome("foo")

      val resp2 = Ok("Foo")
        .map(_.withAttribute(attrKey, "foo"))
        .unsafeRunSync()
        .resp

      resp2.attributes.get(attrKey) must beSome("foo")
    }

    "Add a body" in {
      val newbody = "foobar"
      val resp = Ok("foo")
        .flatMap(_.withBody(newbody))
        .unsafeRunSync()
        .resp

      new String(resp.body.compile.toVector.unsafeRunSync().toArray) must_== newbody
      resp.headers.get(`Content-Length`) must beSome(`Content-Length`.unsafeFromLong(newbody.getBytes.length))

      val resp2 = Ok("foo")
        .flatMap(_.withBody(newbody))
        .unsafeRunSync()
        .resp

      new String(resp2.body.compile.toVector.unsafeRunSync().toArray) must_== newbody
      resp2.headers.get(`Content-Length`) must beSome(`Content-Length`.unsafeFromLong(newbody.getBytes.length))
    }
  }
}
