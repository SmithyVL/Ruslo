import {createRouter, createWebHistory} from 'vue-router'
import WelcomeView from '@/components/pages/public/WelcomeView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'welcome',
      component: WelcomeView,
    },
  ],
})

router.beforeEach((to, from, next) => {
  const authToken = localStorage.getItem('token')
  const publicPages = ["/", "/login", "/register"]

  if (authToken !== null && authToken !== "undefined") {
    let homeAuthPage = {path: '/home'}
    if (publicPages.includes(to.path)) {
      next(homeAuthPage)
    } else {
      next()
    }
  } else if (publicPages.includes(to.path)) {
    next()
  } else {
    next({path: '/'})
  }
})

router.afterEach(to => {
  console.log("Выполнен переход по пути - " + to.fullPath)
  localStorage.setItem("lastPage", to.fullPath)
})

export default router
