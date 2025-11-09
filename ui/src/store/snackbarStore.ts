import {ref} from "vue";
import {defineStore} from "pinia";
import {Snackbar} from "@/model/Snackbar";

export const useSnackbarStore = defineStore("snackbar", () => {
  const queue = ref([] as Snackbar[])

  function addSuccess(text: string) {
    queue.value.push(new Snackbar(text, "success"))
  }

  function addError(text: string) {
    queue.value.push(new Snackbar(text, "error"))
  }

  return {queue, addSuccess, addError}
})
