<script>
import { defineComponent } from "vue";
import TituloPagina from "../components/menus/Titulo-Pagina.vue";
import CrudCozinhaVue from "../components/Cruds/Cozinha.vue";

export default defineComponent({
  components: {
    TituloPagina,
    CrudCozinhaVue,
},
  data() {
    return {
    };
  },
    methods: {

    },
  
  mounted() {
  },
});
</script>

<template>
  <TituloPagina :tituloPagina="'Cozinha'" />

  <CrudCozinhaVue />


  

  
  
</template>
