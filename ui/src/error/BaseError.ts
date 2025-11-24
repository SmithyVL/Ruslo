/**
 * Базовая ошибка с кодом ответа API.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
export class BaseError extends Error {
  /**
   * Код статуса ответа.
   */
  readonly statusCode: number

  /**
   * Инициализирует базовую ошибку.
   *
   * @param statusCode код ошибки.
   * @param message причина ошибки.
   */
  constructor(statusCode: number, message: string) {
    super(message)
    this.statusCode = statusCode
  }
}
