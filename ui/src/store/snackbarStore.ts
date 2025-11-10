import {ref} from "vue";
import {defineStore} from "pinia";
import {Snackbar} from "@/model/Snackbar";

/**
 * Хранилище snackbar сообщений для показа уведомлений пользователю.
 */
export const useSnackbarStore = defineStore("snackbar", () => {
  /**
   * Очередь с сообщениями snackbar.
   */
  const queue = ref([] as Snackbar[])

  /**
   * Добавляет snackbar с сообщением об успешном действии.
   *
   * @param text текст сообщения.
   */
  function addSuccess(text: string) {
    queue.value.push(new Snackbar(text, "success"))
  }

  /**
   * Добавляет snackbar с сообщением об ошибочном действии.
   *
   * @param text текст сообщения.
   */
  function addError(text: string) {
    queue.value.push(new Snackbar(text, "error"))
  }

  return {queue, addSuccess, addError}
})
