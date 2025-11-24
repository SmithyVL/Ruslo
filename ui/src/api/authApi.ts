import {SignUpDto} from "@/api/dto/request/auth/SignUpDto"
import {SignInDto} from "@/api/dto/request/auth/SignInDto"
import fetchWrapper from "@/api/fetchWrapper"
import {UsernameDto} from "@/api/dto/request/auth/UsernameDto.ts";
import {BaseError} from "@/error/BaseError.ts";

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

      if (response.status.toString().startsWith("4")) {
        throw new BaseError(response.status, "Неверные данные для входа или пароль")
      }

      throw new BaseError(response.status, response.statusText)
    })
    .then(response => response.json())
    .then(json => json.token),

  /**
   * Проверяет уникального выбранного имени пользователя при регистрации.
   *
   * @param username имя пользователя.
   * @return является ли имя пользователя уникальным.
   */
  isUniqueUsername: (username: string) => fetchWrapper
    .post(`${authPrefix}/unique-username`, new UsernameDto(username))
    .then(response => {
      if (response.ok) {
        return response
      }

      throw new Error(response.statusText)
    })
    .then(response => response.json())
    .then(json => json.result as boolean),
}
