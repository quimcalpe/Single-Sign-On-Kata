package myservice

import sso.Request
import sso.Response
import sso.SingleSignOnRegistry

class MyService(singleSignOnRegistry: SingleSignOnRegistry) {

  def handleRequest(request: Request): Response = {
    if (!singleSignOnRegistry.isValid(request.token)) {
      Response("Invalid Token")
    } else {
      Response(s"Hello ${request.name}!")
    }
  }
}
