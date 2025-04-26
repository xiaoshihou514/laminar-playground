package chart

import scala.scalajs.js
import scala.scalajs.js.annotation._
import org.scalajs.dom

import com.raquo.laminar.api.L._

object Main {
  val nameVar = Var(initial = "world")
  val app = div(
    label("Your name: "),
    input(
      placeholder := "Enter your name here",
      onInput.mapToValue --> nameVar
    ),
    p(
      "Hello, ",
      text <-- nameVar.signal.map(_.toUpperCase)
    )
  )

  @JSExportTopLevel("main")
  def main(): Unit = {
    renderOnDomContentLoaded(dom.document.getElementById("app"), app)
  }
}
