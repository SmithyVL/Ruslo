import {createRouter, createWebHistory} from "vue-router"
import WelcomeView from "@/components/pages/public/WelcomeView.vue"
import SignUpView from "@/components/pages/public/SignUpView.vue"
import SignInView from "@/components/pages/public/SignInView.vue"

/**
 * Создание плагина Router.
 */
const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      name: "welcome",
      component: WelcomeView,
    },
    {
      path: "/sign-up",
      name: "sign-up",
      component: SignUpView,
    },
    {
      path: "/sign-in",
      name: "sign-in",
      component: SignInView,
    },
  ],
})

router.beforeEach(
  (
    to,
    from,
    next,
  ) => {
    //const authToken = localStorage.getItem("token")
    const authToken = null
    const publicPages = new Set<string>(["/", "/sign-in", "/sign-up"])

    if (authToken !== null && authToken !== "undefined") {
      const homeAuthPage = {path: "/home"}
      if (publicPages.has(to.path)) {
        next(homeAuthPage)
      } else {
        next()
      }
    } else {
      if (publicPages.has(to.path)) {
        next()
      } else {
        next({path: "/"})
      }
    }
  }
)

export default router
