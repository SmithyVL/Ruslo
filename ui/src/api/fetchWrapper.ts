const baseUrl = "http://localhost:8083/ruslo/gateway-service/"

/**
 * Инициализирует параметры инициализации запроса API.
 *
 * @param method тип запроса.
 * @param body тело запроса.
 * @return параметры запроса.
 */
function initParams(method: string, body: any) {
  return {
    method: method,
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  }
}

export default {
  /**
   * Выполняет POST запрос к серверу.
   *
   * @param path путь запроса.
   * @param body тело запроса.
   * @return токен авторизации.
   */
  post: (path: string, body: any) =>
    fetch(`${baseUrl}${path}`, initParams("POST", body)),
}
