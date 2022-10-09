<script store>
import { defineComponent } from "vue";
import TituloPagina from "../components/menus/Titulo-Pagina.vue";

export default defineComponent({
  components: {
    TituloPagina,
  },

  methods: {

  },

  mounted() {
  },
});
</script>

<template>
  <TituloPagina tituloPagina="App Burguer" />
  <img src="../assets/burguerhome.jpg" alt="" class="w-full" >
</template>
