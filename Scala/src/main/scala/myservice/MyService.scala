package myservice

import sso._

class MyService(singleSignOnRegistry: SingleSignOnRegistry) {

  def handleRequest(request: Request): Response = {
    if (!singleSignOnRegistry.isValid(request.token)) {
      Response("Invalid Token")
    } else {
      Response(s"Hello ${request.name}!")
    }
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
