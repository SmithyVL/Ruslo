import {createApp} from "vue"
import "unfonts.css"
import App from "@/App.vue"

// Плагины.
import vuetify from "@/plugins/vuetify"
import router from "@/plugins/router"
import pinia from "@/plugins/pinia"

// Настройка приложения.
createApp(App)
  .use(vuetify)
  .use(router)
  .use(pinia)
  .mount("#app")
