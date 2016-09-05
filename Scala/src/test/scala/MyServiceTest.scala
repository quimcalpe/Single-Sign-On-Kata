import myservice.MyService
import org.scalatest.{FlatSpec, Matchers}
import sso.{Request, SSOToken, SingleSignOnRegistry}

class MyServiceTest extends FlatSpec with Matchers {

  "MyService" should "reject requests with invalid SSOTokens" in {
    val myService = new MyService(fakerSSO)
    val response = myService.handleRequest(Request("Foo", SSOToken("")))
    response.text should be("Invalid Token")
  }

  "MyService" should "greet with valid SSOToken" in {
    val myService = new MyService(fakerSSO)
    val response = myService.handleRequest(Request("Foo", SSOToken("valid")))
    response.text should be("Hello Foo!")
  }

}

object fakerSSO extends SingleSignOnRegistry {
  override def isValid(token: SSOToken): Boolean = token.uuid match {
    case "valid" => true
    case _ => false
  }

  override def registerNewSession(username: String, password: String): SSOToken = ???

  override def unregister(token: SSOToken): Unit = ???
}