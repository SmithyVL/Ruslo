import {createApp} from "vue"
import "./assets/main.css"
import App from "@/App.vue"

// Плагины.
import vuetify from "@/plugins/vuetify"
import router from "@/plugins/router"
import pinia from "@/plugins/pinia.ts"

// Настройка приложения.
createApp(App)
  .use(vuetify)
  .use(router)
  .use(pinia)
  .mount("#app")
