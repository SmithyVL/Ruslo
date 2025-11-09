/**
 * DTO с информацией о новом пользователе.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
export class SignUpDto {
  /**
   * Логин нового пользователя.
   */
  username: string = ""

  /**
   * Пароль нового пользователя.
   */
  password: string = ""
}
