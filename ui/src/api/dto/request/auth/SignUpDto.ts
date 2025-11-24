/**
 * DTO с информацией о новом пользователе.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
export class SignUpDto {
  /**
   * Электронная почта нового пользователя.
   */
  email: string = ""

  /**
   * Логин нового пользователя.
   */
  username: string = ""

  /**
   * Пароль нового пользователя.
   */
  password: string = ""

  /**
   * День рождения пользователя.
   */
  day: number | undefined = undefined

  /**
   * Месяц рождения пользователя.
   */
  month: number | undefined = undefined

  /**
   * Год рождения пользователя.
   */
  year: number | undefined = undefined
}
