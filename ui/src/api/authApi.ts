import {SignUpDto} from "@/api/dto/request/auth/SignUpDto"
import {SignInDto} from "@/api/dto/request/auth/SignInDto"
import fetchWrapper from "@/api/fetchWrapper"

// Префикс API запросов для авторизации/аутентификации пользователя.
const authPrefix = "auth"

/**
 * Методы для обращений к API авторизации/аутентификации пользователей.
 */
export default {
  /**
   * Регистрирует нового пользователя.
   *
   * @param signUpDto информация о новом пользователе.
   * @return токен авторизации.
   */
  signUp: (signUpDto: SignUpDto) => fetchWrapper.post(`${authPrefix}/sign-up`, signUpDto)
    .then(response => {
      if (response.ok) {
        return response
      }

      if (response.status == 409) {
        throw new Error("Такой пользователь уже существует")
      }

      throw new Error(response.statusText)
    })
    .then(response => response.json())
    .then(json => json.token),

  /**
   * Авторизует пользователя.
   *
   * @param signInDto информация авторизации пользователя.
   * @return токен авторизации.
   */
  signIn: (signInDto: SignInDto) => fetchWrapper.post(`${authPrefix}/sign-in`, signInDto)
    .then(response => {
      if (response.ok) {
        return response
      }

      if (response.status == 404) {
        throw new Error("Пользователь не существует")
      }

      if (response.status == 401) {
        throw new Error("Не верный пароль")
      }

      throw new Error(response.statusText)
    })
    .then(response => response.json())
    .then(json => json.token),
}
