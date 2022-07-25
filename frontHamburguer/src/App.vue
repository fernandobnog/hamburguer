<script>
import { defineComponent } from "vue";
import { RouterView } from "vue-router";
import MenuBar from "./components/menus/Navbar.vue";

export default defineComponent({
  components: {
    RouterView,
    MenuBar,
  },
});
</script>

<template>
  <Toast />
  <MenuBar />
  <router-view />
</template>
