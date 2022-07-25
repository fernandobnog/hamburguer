import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/HomeView.vue'

import {
  Estoque,
  Pedido,
  Pessoa,
  Cozinha,

} from './main'

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
  },
  ...Estoque,
  ...Pessoa,
  ...Pedido,
  ...Cozinha,
]


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
