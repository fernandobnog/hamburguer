<script>
import { defineComponent } from "vue";
import TituloPagina from "../components/menus/Titulo-Pagina.vue";
import CrudSaladaVue from "../components/Cruds/Salada.vue";
import CrudOpcionaisVue from "../components/Cruds/Opcionais.vue";
import CrudQueijoVue from "../components/Cruds/Queijo.vue";
import CrudCarneVue from "../components/Cruds/Carne.vue";
import CrudPaoVue from "../components/Cruds/Pao.vue";

export default defineComponent({
  components: {
    TituloPagina,
    CrudSaladaVue,
    CrudOpcionaisVue,
    CrudQueijoVue,
    CrudCarneVue,
    CrudPaoVue,
  },
  data() {
    return {
      displayQueijo: false,
      displaySalada: false,
      displayOpcionais: false,
      displayCarne: false,
      displayPao: false,
    };
  },
    methods: {
    verPao(){
      this.displayPao = !this.displayPao;
    },

    verCarne(){
      this.displayCarne = !this.displayCarne;
    },

    verQueijo(){
      this.displayQueijo = !this.displayQueijo;
    },
      
    verSalada(){
      this.displaySalada = !this.displaySalada;
    },
    
    verOpcionais(){
      this.displayOpcionais = !this.displayOpcionais;
    }

    },
  
  mounted() {
  },
});
</script>

<template>
  <TituloPagina :tituloPagina="'Estoque'" />

     <div class="card">
      <div class="flex flex-wrap card-container" style="w-full">
        <div class="flex align-items-center justify-content-center w-full ">
          <Button label="Pão" icon="pi pi-plus" class="p-button-success m-3 w-5 h-3rem" 
          @click="verPao" />
        
        </div>
        <div class="flex align-items-center justify-content-center w-full">
          <Button label="Carne" icon="pi pi-plus" class="p-button-success m-3 w-5 h-3rem" 
          @click="verCarne()" />
        
        </div>
        <div class="flex align-items-center justify-content-center w-full">
          <Button label="Queijo" icon="pi pi-plus" class="p-button-success m-3 w-5 h-3rem" 
          @click="verQueijo()" />
        
        </div>
        <div class="flex align-items-center justify-content-center w-full">
          <Button label="Salada" icon="pi pi-plus" class="p-button-success m-3 w-5 h-3rem" 
          @click="this.verSalada()" />
        
        </div>
        <div class="flex align-items-center justify-content-center w-full">
          <Button label="Opcionais" icon="pi pi-plus" class="p-button-success m-3 w-5 h-3rem" 
          @click="verOpcionais()" />
        
        </div>
    </div>


  </div>
    <Dialog v-model:visible="displayPao" :style="{width: '450px'}" :modal="true" >
      <h2 class="flex justify-content-center m-0">Pão</h2>
      <CrudPaoVue />
    </Dialog>
    <Dialog v-model:visible="displayCarne" :style="{width: '450px'}" :modal="true" >
      <h2 class="flex justify-content-center m-0">Carne</h2>
      <CrudCarneVue />
    </Dialog>
    <Dialog v-model:visible="displayQueijo" :style="{width: '450px'}" :modal="true" >
      <h2 class="flex justify-content-center m-0">Queijo</h2>
      <CrudQueijoVue />
    </Dialog>
    <Dialog v-model:visible="displaySalada" :style="{width: '450px'}" :modal="true" >
      <h2 class="flex justify-content-center m-0">Salada</h2>
      <CrudSaladaVue />
    </Dialog>
    <Dialog v-model:visible="displayOpcionais" :style="{width: '450px'}" :modal="true" >
      <h2 class="flex justify-content-center m-0">Opcionais</h2>
      <CrudOpcionaisVue />
    </Dialog>


  

  
  
</template>
