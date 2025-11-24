/**
 * DTO с информацией для авторизации существующего пользователя.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
export class UsernameDto {
  /**
   * Имя пользователя.
   */
  readonly username: string = ""

  constructor(username: string) {
    this.username = username;
  }
}
