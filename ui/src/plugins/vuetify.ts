import {createVuetify} from "vuetify"
import "vuetify/styles"
import "@mdi/font/css/materialdesignicons.css"
import * as components from "vuetify/components"
import * as directives from "vuetify/directives"

/**
 * Создание плагина Vuetify.
 */
export default createVuetify({
  theme: {
    defaultTheme: "dark",
  },
  components,
  directives,
})
