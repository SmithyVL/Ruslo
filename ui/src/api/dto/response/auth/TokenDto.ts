/**
 * DTO с информацией о токене авторизации пользователя.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
// noinspection JSUnusedGlobalSymbols
export class TokenDto {
  /**
   * Токен авторизации пользователя.
   */
  readonly token: string

  /**
   * Инициализирует токен авторизации пользователя.
   *
   * @param token токен пользователя.
   */
  // noinspection JSUnusedGlobalSymbols
  constructor(token: string) {
    this.token = token;
  }
}
