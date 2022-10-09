<script>
import { defineComponent } from "vue";
import { carneStore } from "../../stores/carne";
import { mapState, mapActions } from "pinia";

export default defineComponent({

  data() {
    return {
      displayEditar: false,
      displayExcluir: false,
      carnePropsData: null,
    };
  },

  computed: {
    ...mapState(carneStore, [
      "listaCarne",
      "carne",
      "statusServer",
      "carneRetorno",
    ]),
  },

  methods: {
    ...mapActions(carneStore, [
      "listarCarne",
      "salvarCarne",
      "deletarCarne",
      "resetCarne",
      "editarCarne",
    ]),

    async cadastrarCarne() {
      await this.salvarCarne();

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Salvo com Sucesso",
          detail: "Carne inserida no banco de dados",
          life: 3000,
        });

        this.$emit('savedCarne');
      }

      if (this.statusServer.status == "Alerta") {
        this.$toast.add({
          severity: "warn",
          summary: "Aviso!",
          detail: this.statusServer.mensagem,
          life: 8000,
        });
      }

      if (this.statusServer.status == "Erro") {
        this.$toast.add({
          severity: "error",
          summary: "Erro ao salvar",
          detail: this.statusServer.mensagem,
          life: 8000,
        });
      }
    this.listarCarne();
    this.resetCarne();
    },
    
    confirmarEditar(obj){
      this.carnePropsData = obj;
      this.displayEditar = !this.displayEditar;
    },

    async mudarCarne(objEditar) {
      
      this.carne.id = objEditar.id;
      this.carne.pesoGramas = objEditar.pesoGramas;
      this.carne.estoque = objEditar.estoque

      await this.editarCarne(this.carne);

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Editado com Sucesso",
          detail: "Carne Editado no banco de dados",
          life: 3000,
        });

        this.$emit('editedCarne');
      }

      if (this.statusServer.status == "Alerta") {
        this.$toast.add({
          severity: "warn",
          summary: "Aviso!",
          detail: this.statusServer.mensagem,
          life: 8000,
        });
      }

      if (this.statusServer.status == "Erro") {
        this.$toast.add({
          severity: "error",
          summary: "Erro ao salvar",
          detail: this.statusServer.mensagem,
          life: 8000,
        });
      }
    this.listarCarne()
    this.carnePropsData=null;
    this.resetCarne()
    },

    confirmarExcluir(obj){
      this.carnePropsData = obj;
      this.displayExcluir = !this.displayExcluir;
    },

    async excluirCarne(objExcluir) {
      this.carne.id = objExcluir.id;
      this.carne.tipo = objExcluir.tipo;

      await this.deletarCarne(this.carne);

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Excluído com Sucesso",
          detail: "Carne excluída do banco de dados",
          life: 3000,
        });

        this.$emit('deletedCarne');
      }

      if (this.statusServer.status == "Alerta") {
        this.$toast.add({
          severity: "warn",
          summary: "Aviso!",
          detail: this.statusServer.mensagem,
          life: 8000,
        });
      }

      if (this.statusServer.status == "Erro") {
        this.$toast.add({
          severity: "error",
          summary: "Erro ao salvar",
          detail: this.statusServer.mensagem,
          life: 8000,
        });
      }

    this.listarCarne()
    this.carnePropsData=null;
    this.resetCarne()
    },

  },

  mounted() {
    this.listarCarne()
  },
});
</script>
<template>
    <div class="card mt-0">
      <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
        <InputText type="text" placeholder="peso em gramas" v-model="carne.pesoGramas" class="m-3 w-9"/>
        <InputText type="text" placeholder="quantidade" v-model="carne.estoque" class="m-3 w-9"/>
      </div>
      <div class="col-12 flex justify-content-center">
        <Button
          @click="cadastrarCarne()"
          label="Salvar"
        />
      </div>
    </div>
    <div class="card mt-0">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <DataTable
          :style="{width: '350px'}"
          :value="listaCarne"
          responsiveLayout="scroll"
        >
          <Column field="id" header="ID"></Column>
          <Column field="pesoGramas" header="GRAMAS"></Column>
          <Column field="estoque" header="QTD"></Column>
          <Column>
            <template #body="slotProps">
              <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="confirmarEditar(slotProps.data)" />
              <Button icon="pi pi-trash" class="p-button-rounded p-button-warning m-0" @click="confirmarExcluir(slotProps.data)" />
            </template>
          </Column>
        </DataTable>
      </div>
    </div>

    
    <Dialog v-model:visible="displayEditar" :style="{width: '450px'}" :modal="true" :closable="false">
      <h2 class="flex justify-content-center m-0">Editar Carne</h2>
      <div class="confirmation-content">
        <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
          <InputText type="text" placeholder="peso em gramas" v-model="carnePropsData.pesoGramas" class="m-3 w-9"/>
          <InputText type="text" placeholder="quantidade" v-model="carnePropsData.estoque" class="m-3 w-9"/>
        </div>
      </div>
      <template #footer>
          <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayEditar=false, carnePropsData=null"/>
          <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="mudarCarne(carnePropsData), displayEditar=false" />
      </template>
    </Dialog>



  <Dialog v-model:visible="displayExcluir" :style="{width: '450px'}" :modal="true" :closable="false">
    <h2 class="flex justify-content-center m-0">Excluir Carne</h2>
    <div class="confirmation-content">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
        <div >Excluir: '<b>{{carnePropsData.tipo}}</b>' ?</div>
      </div>
    </div>
    <template #footer>
        <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayExcluir=false, carnePropsData=null"/>
        <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="excluirCarne(carnePropsData), displayExcluir=false" />
    </template>
  </Dialog>

</template>
