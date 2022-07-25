<script>
import { defineComponent } from "vue";
import TituloPagina from "../components/menus/Titulo-Pagina.vue";
import CrudPedidoVue from "../components/Cruds/Pedido.vue";

export default defineComponent({
  components: {
    TituloPagina,
    CrudPedidoVue,
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
  <TituloPagina :tituloPagina="'Pedidos'" />

  <CrudPedidoVue />


  

  
  
</template>
