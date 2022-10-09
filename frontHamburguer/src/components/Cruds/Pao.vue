<script>
import { defineComponent } from "vue";
import { paoStore } from "../../stores/pao";
import { mapState, mapActions } from "pinia";

export default defineComponent({

  data() {
    return {
      displayEditar: false,
      displayExcluir: false,
      paoPropsData: null,
    };
  },

  computed: {
    ...mapState(paoStore, [
      "listaPao",
      "pao",
      "statusServer",
      "paoRetorno",
    ]),
  },

  methods: {
    ...mapActions(paoStore, [
      "listarPao",
      "salvarPao",
      "deletarPao",
      "resetPao",
      "editarPao",
    ]),

    async cadastrarPao() {
      await this.salvarPao();

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Salvo com Sucesso",
          detail: "Pao inserido no banco de dados",
          life: 3000,
        });

        this.$emit('savedPao');
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
    this.listarPao();
    this.resetPao();
    },
    
    confirmarEditar(obj){
      this.paoPropsData = obj;
      this.displayEditar = !this.displayEditar;
    },

    async mudarPao(objEditar) {
      
      this.pao.id = objEditar.id;
      this.pao.tipo = objEditar.tipo;
      this.pao.estoque = objEditar.estoque;

      await this.editarPao(this.pao);

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Editado com Sucesso",
          detail: "pao Editado no banco de dados",
          life: 3000,
        });

        this.$emit('editedPao');
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
    this.listarPao()
    this.paoPropsData=null;
    this.resetPao()
    },

    confirmarExcluir(obj){
      this.paoPropsData = obj;
      this.displayExcluir = !this.displayExcluir;
    },

    async excluirPao(objExcluir) {
      this.pao.id = objExcluir.id;
      this.pao.tipo = objExcluir.tipo;

      await this.deletarPao(this.pao);

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Excluído com Sucesso",
          detail: "Pao excluída do banco de dados",
          life: 3000,
        });

        this.$emit('deletedPao');
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

    this.listarPao()
    this.paoPropsData=null;
    this.resetPao()
    },

  },

  mounted() {
    this.listarPao()
  },
});
</script>
<template>
    <div class="card mt-0">
      <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
        <InputText type="text" placeholder="tipo" v-model="pao.tipo" class="m-3 w-5"/>
        <InputText type="text" placeholder="quantidade" v-model="pao.estoque" class="m-3 w-5"/>
      </div>
      <div class="col-12 flex justify-content-center">
        <Button
          @click="cadastrarPao()"
          label="Salvar"
        />
      </div>
    </div>
    <div class="card mt-0">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <DataTable
          :style="{width: '350px'}"
          :value="listaPao"
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
      <h2 class="flex justify-content-center m-0">Editar Pao</h2>
      <div class="confirmation-content">
        <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
          <InputText type="text" placeholder="tipo" v-model="paoPropsData.tipo" class="m-3 w-5"/>
          <InputText type="text" placeholder="quantidade" v-model="paoPropsData.estoque" class="m-3 w-5"/>
        </div>
      </div>
      <template #footer>
          <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayEditar=false, paoPropsData=null"/>
          <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="mudarPao(paoPropsData), displayEditar=false" />
      </template>
    </Dialog>



  <Dialog v-model:visible="displayExcluir" :style="{width: '450px'}" :modal="true" :closable="false">
    <h2 class="flex justify-content-center m-0">Excluir Pao</h2>
    <div class="confirmation-content">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
        <div >Excluir: '<b>{{paoPropsData.tipo}}</b>' ?</div>
      </div>
    </div>
    <template #footer>
        <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayExcluir=false, paoPropsData=null"/>
        <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="excluirPao(paoPropsData), displayExcluir=false" />
    </template>
  </Dialog>

</template>
