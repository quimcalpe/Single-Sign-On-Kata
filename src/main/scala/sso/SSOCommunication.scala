package sso

case class SSOToken(uuid: String) {}

case class Request(name: String, token: SSOToken) {}

case class NewSessionRequest(name: String, pass: String) {}

case class LogoutRequest(token: SSOToken) {}

case class Response(text: String) {}

class BadCredentials extends Throwable {}