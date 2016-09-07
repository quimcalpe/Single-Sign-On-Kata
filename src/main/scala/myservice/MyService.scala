package myservice

import sso._

class MyService(singleSignOnRegistry: SingleSignOnRegistry) {

  def handleRequest(request: Request): Response = singleSignOnRegistry.isValid(request.token) match {
    case true => Response(s"Hello ${request.name}!")
    case false => Response("Invalid Token")
  }

  def handleRequest(request: NewSessionRequest): Response = {
    try {
      val token = singleSignOnRegistry.registerNewSession(request.name, request.pass)
      Response(s"OK ${token.uuid}")
    } catch {
      case e: BadCredentials => Response("KO")
    }
  }

  def handleRequest(request: LogoutRequest): Response = {
    singleSignOnRegistry.unregister(request.token)
    Response("OK")
  }

}
