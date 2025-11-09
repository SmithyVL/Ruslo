import {ref} from "vue"
import {defineStore} from "pinia"
import type {User} from "@/model/user/User"

export const useUserStore = defineStore("user", () => {
  const user = ref(null as User | null)

  return {user}
})
