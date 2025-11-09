/**
 * Информация об уведомлении snackbar.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
export class Snackbar {
  /**
   * Текст уведомления.
   */
  readonly text: string

  /**
   * Время жизни уведомления.
   */
  readonly timeout: number = 3000

  /**
   * Цвет уведомления.
   */
  readonly color: string

  /**
   * Инициализирует snackbar.
   *
   * @param text текст уведомления.
   * @param color цвет уведомления.
   * @param timeout время жизни уведомления.
   */
  constructor(text: string, color: string, timeout: number | null = null) {
    this.text = text
    this.color = color

    if (timeout !== null) {
      this.timeout = timeout
    }
  }
}
