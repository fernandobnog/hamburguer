<script>
import { defineComponent } from "vue";
import { queijoStore } from "../../stores/queijo";
import { mapState, mapActions } from "pinia";

export default defineComponent({

  data() {
    return {
      displayEditar: false,
      displayExcluir: false,
      queijoPropsData: null,
    };
  },

  computed: {
    ...mapState(queijoStore, [
      "listaQueijo",
      "queijo",
      "statusServer",
      "queijoRetorno",
    ]),
  },

  methods: {
    ...mapActions(queijoStore, [
      "listarQueijo",
      "salvarQueijo",
      "deletarQueijo",
      "resetQueijo",
      "editarQueijo",
    ]),

    async cadastrarQueijo() {
      await this.salvarQueijo();

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Salvo com Sucesso",
          detail: "Queijo inserido no banco de dados",
          life: 3000,
        });

        this.$emit('savedQueijo');
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
    this.listarQueijo();
    this.resetQueijo();
    },
    
    confirmarEditar(obj){
      this.queijoPropsData = obj;
      this.displayEditar = !this.displayEditar;
    },

    async mudarQueijo(objEditar) {
      
      this.queijo.id = objEditar.id;
      this.queijo.tipo = objEditar.tipo;
      this.queijo.estoque = objEditar.estoque;

      await this.editarQueijo(this.queijo);

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Editado com Sucesso",
          detail: "Queijo Editado no banco de dados",
          life: 3000,
        });

        this.$emit('editedQueijo');
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
    this.listarQueijo()
    this.queijoPropsData=null;
    this.resetQueijo()
    },

    confirmarExcluir(obj){
      this.queijoPropsData = obj;
      this.displayExcluir = !this.displayExcluir;
    },

    async excluirQueijo(objExcluir) {
      this.queijo.id = objExcluir.id;
      this.queijo.tipo = objExcluir.tipo;

      await this.deletarQueijo(this.queijo);

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Excluído com Sucesso",
          detail: "Queijo excluída do banco de dados",
          life: 3000,
        });

        this.$emit('deletedQueijo');
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

    this.listarQueijo()
    this.queijoPropsData=null;
    this.resetQueijo()
    },

  },

  mounted() {
    this.listarQueijo()
  },
});
</script>
<template>
    <div class="card mt-0">
      <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
        <InputText type="text" placeholder="tipo" v-model="queijo.tipo" class="m-3 w-5"/>
        <InputText type="text" placeholder="quantidade" v-model="queijo.estoque" class="m-3 w-5"/>
      </div>
      <div class="col-12 flex justify-content-center">
        <Button
          @click="cadastrarQueijo()"
          label="Salvar"
        />
      </div>
    </div>
    <div class="card mt-0">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <DataTable
          :style="{width: '350px'}"
          :value="listaQueijo"
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
      <h2 class="flex justify-content-center m-0">Editar Queijo</h2>
      <div class="confirmation-content">
        <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
          <InputText type="text" placeholder="tipo" v-model="queijoPropsData.tipo" class="m-3 w-5"/>
          <InputText type="text" placeholder="quantidade" v-model="queijoPropsData.estoque" class="m-3 w-5"/>
        </div>
      </div>
      <template #footer>
          <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayEditar=false, queijoPropsData=null"/>
          <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="mudarQueijo(queijoPropsData), displayEditar=false" />
      </template>
    </Dialog>



  <Dialog v-model:visible="displayExcluir" :style="{width: '450px'}" :modal="true" :closable="false">
    <h2 class="flex justify-content-center m-0">Excluir Queijo</h2>
    <div class="confirmation-content">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
        <div >Excluir: '<b>{{queijoPropsData.tipo}}</b>' ?</div>
      </div>
    </div>
    <template #footer>
        <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayExcluir=false, queijoPropsData=null"/>
        <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="excluirQueijo(queijoPropsData), displayExcluir=false" />
    </template>
  </Dialog>

</template>
