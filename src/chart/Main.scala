package chart

import scala.scalajs.js
import scala.scalajs.js.annotation._
import org.scalajs.dom

import com.raquo.laminar.api.L._
import com.raquo.waypoint._

object Main {
  sealed trait Page
  case object AppPage extends Page
  case object UserPage extends Page

  val compileRoute = Route.static(AppPage, root / endOfSegments)
  val bugHuntersRoute =
    Route.static(UserPage, root / "user" / endOfSegments)
  val router = new Router[Page](
    routes = List(compileRoute, bugHuntersRoute),
    getPageTitle = _ => "test page title",
    serializePage = _ match
      case AppPage  => "app page"
      case UserPage => "user page"
    ,
    deserializePage = _ match
      case "app page"  => AppPage
      case "user page" => UserPage
  )

  val splitter = SplitRender[Page, HtmlElement](router.currentPageSignal)
    .collectStatic(AppPage) { div("app page") }
    .collectStatic(UserPage) { renderUserPage() }

  def renderUserPage(): Div = {
    div(
      "User page"
    )
  }

  val app: Div = div(
    child <-- splitter.signal
  )

  @JSExportTopLevel("main")
  def main(): Unit = {
    renderOnDomContentLoaded(dom.document.getElementById("app"), app)
  }
}
