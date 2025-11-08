import './assets/main.css'

import {createApp} from 'vue'
import App from '@/App.vue'

// Плагины.
import vuetify from '@/plugins/vuetify'
import router from '@/plugins/router'
import pinia from '@/plugins/pinia.ts'

createApp(App)
  .use(vuetify)
  .use(router)
  .use(pinia)
  .mount('#app')
