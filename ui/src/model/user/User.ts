/**
 * Информация о пользователе.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
export class User {
  /**
   * Идентификатор пользователя.
   */
  private readonly _id: number

  /**
   * Логин пользователя.
   */
  private readonly _username: string

  /**
   * Инициализирует пользователя.
   *
   * @param id идентификатор пользователя.
   * @param username логин пользователя.
   */
  constructor(id: number, username: string) {
    this._id = id
    this._username = username
  }

  /**
   * Возвращает идентификатор пользователя.
   */
  get id(): number {
    return this._id;
  }

  /**
   * Возвращает логин пользователя.
   */
  get username(): string {
    return this._username;
  }
}
