<script>
import { defineComponent } from "vue";
import { saladaStore } from "../../stores/salada";
import { mapState, mapActions } from "pinia";

export default defineComponent({

  data() {
    return {
      displayEditar: false,
      displayExcluir: false,
      saladaPropsData: null,
    };
  },

  computed: {
    ...mapState(saladaStore, [
      "listaSalada",
      "salada",
      "statusServer",
      "saladaRetorno",
    ]),
  },

  methods: {
    ...mapActions(saladaStore, [
      "listarSalada",
      "salvarSalada",
      "deletarSalada",
      "resetSalada",
      "editarSalada",
    ]),

    async cadastrarSalada() {
      await this.salvarSalada();

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Salvo com Sucesso",
          detail: "Salada inserida no banco de dados",
          life: 3000,
        });

        this.$emit('savedSalada');
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
    this.listarSalada();
    this.resetSalada();
    },
    
    confirmarEditar(obj){
      this.saladaPropsData = obj;
      this.displayEditar = !this.displayEditar;
    },

    async mudarSalada(objEditar) {
      
      this.salada.id = objEditar.id;
      this.salada.tipo = objEditar.tipo;
      console.log('objEditar:' + this.salada)

      await this.editarSalada(this.salada);

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Editado com Sucesso",
          detail: "Salada Editada no banco de dados",
          life: 3000,
        });

        this.$emit('savedSalada');
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
    this.listarSalada()
    this.saladaPropsData=null;
    this.resetSalada()
    },

    confirmarExcluir(obj){
      this.saladaPropsData = obj;
      this.displayExcluir = !this.displayExcluir;
    },

    async excluirSalada(objExcluir) {
      this.salada.id = objExcluir.id;
      this.salada.tipo = objExcluir.tipo;

      await this.deletarSalada(this.salada);

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Excluído com Sucesso",
          detail: "Salada excluída do banco de dados",
          life: 3000,
        });

        this.$emit('deletedSalada');
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

    this.listarSalada()
    this.saladaPropsData=null;
    this.resetSalada()
    },

  },

  mounted() {
    this.listarSalada()
  },
});
</script>
<template>
    <div class="card mt-0">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <InputText type="text" v-model="salada.tipo" placeholder="tipo" class="m-3 w-5"/>
      </div>
      <div class="col-12 flex justify-content-center">
        <Button
          @click="cadastrarSalada()"
          label="Salvar"
        />
      </div>
    </div>
    <div class="card mt-0">
      <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
        <DataTable
          :style="{width: '350px'}"
          :value="listaSalada"
          responsiveLayout="scroll"
        >
          <Column field="id" header="ID"></Column>
          <Column field="tipo" header="TIPO"></Column>
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
      <h2 class="flex justify-content-center m-0">Editar Salada</h2>
      <div class="confirmation-content">
        <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
          <div class="card">
            <InputText type="text" v-model="saladaPropsData.tipo" placeholder="tipo" class="m-3 w-5"/>
          </div>
        </div>
      </div>
      <template #footer>
          <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayEditar=false, saladaPropsData=null"/>
          <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="mudarSalada(saladaPropsData), displayEditar=false" />
      </template>
    </Dialog>



  <Dialog v-model:visible="displayExcluir" :style="{width: '450px'}" :modal="true" :closable="false">
    <h2 class="flex justify-content-center m-0">Excluir Salada</h2>
    <div class="confirmation-content">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
        <div >Excluir: '<b>{{saladaPropsData.tipo}}</b>' ?</div>
      </div>
    </div>
    <template #footer>
        <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayExcluir=false, saladaPropsData=null"/>
        <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="excluirSalada(saladaPropsData), displayExcluir=false" />
    </template>
  </Dialog>

</template>
