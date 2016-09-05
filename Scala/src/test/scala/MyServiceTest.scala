import myservice.MyService
import org.scalatest.{FlatSpec, Matchers}
import sso._

class MyServiceTest extends FlatSpec with Matchers {

  "MyService" should "reject requests with invalid SSOTokens" in {
    val myService = new MyService(fakeSSO)
    val response = myService.handleRequest(Request("Foo", SSOToken("")))
    response.text should be("Invalid Token")
  }

  "MyService" should "greet with valid SSOToken" in {
    val myService = new MyService(fakeSSO)
    val response = myService.handleRequest(Request("Foo", SSOToken("valid")))
    response.text should be("Hello Foo!")
  }

  "MyService" should "allow to register a new session" in {
    val myService = new MyService(fakeSSO)
    val response = myService.handleRequest(NewSessionRequest("user", "1234"))
    response.text should be("OK valid")
  }

  "MyService" should "return KO on invalid credentials" in {
    val myService = new MyService(fakeSSO)
    val response = myService.handleRequest(NewSessionRequest("user", "badpass"))
    response.text should be("KO")
  }

  "MyService" should "allow to logout an user" in {
    val myService = new MyService(fakeSSO)
    val response = myService.handleRequest(LogoutRequest(SSOToken("valid")))
    response.text should be("OK")
    fakeSSO.unregisterWasCalled should be(true)
  }


}

object fakeSSO extends SingleSignOnRegistry {
  var unregisterWasCalled: Boolean = false

  override def isValid(token: SSOToken): Boolean = token.uuid match {
    case "valid" => true
    case _ => false
  }

  override def registerNewSession(username: String, password: String): SSOToken = (username, password) match {
    case ("user", "1234") => SSOToken("valid")
    case _ => throw new BadCredentials
  }

  override def unregister(token: SSOToken): Unit = {
    unregisterWasCalled = true
  }
}