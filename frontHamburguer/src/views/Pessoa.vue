<script>
import { defineComponent } from "vue";
import TituloPagina from "../components/menus/Titulo-Pagina.vue";
import CrudPessoaVue from "../components/Cruds/Pessoa.vue";

export default defineComponent({
  components: {
    TituloPagina,
    CrudPessoaVue,
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
  <TituloPagina :tituloPagina="'Pessoas'" />

  <CrudPessoaVue />


  

  
  
</template>
