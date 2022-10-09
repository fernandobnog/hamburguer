<script>
import { defineComponent } from "vue";
import { opcionaisStore } from "../../stores/opcionais";
import { mapState, mapActions } from "pinia";

export default defineComponent({

  data() {
    return {
      displayEditar: false,
      displayExcluir: false,
      opcionaisPropsData: null,
    };
  },

  computed: {
    ...mapState(opcionaisStore, [
      "listaOpcionais",
      "opcionais",
      "statusServer",
      "opcionaisRetorno",
    ]),
  },

  methods: {
    ...mapActions(opcionaisStore, [
      "listarOpcionais",
      "salvarOpcionais",
      "deletarOpcionais",
      "resetOpcionais",
      "editarOpcionais",
    ]),

    async cadastrarOpcionais() {
      await this.salvarOpcionais();

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Salvo com Sucesso",
          detail: "Opcionais inserida no banco de dados",
          life: 3000,
        });

        this.$emit('savedOpcionais');
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
    this.listarOpcionais();
    this.resetOpcionais();
    },
    
    confirmarEditar(obj){
      this.opcionaisPropsData = obj;
      this.displayEditar = !this.displayEditar;
    },

    async mudarOpcionais(objEditar) {
      
      this.opcionais.id = objEditar.id;
      this.opcionais.tipo = objEditar.tipo;
      this.opcionais.quantidade = objEditar.quantidade;
      this.opcionais.temQuantidade = objEditar.temQuantidade;

      await this.editarOpcionais(this.opcionais);

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Editado com Sucesso",
          detail: "Opcionais Editado no banco de dados",
          life: 3000,
        });

        this.$emit('editedOpcionais');
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
    this.listarOpcionais()
    this.opcionaisPropsData=null;
    this.resetOpcionais()
    },

    confirmarExcluir(obj){
      this.opcionaisPropsData = obj;
      this.displayExcluir = !this.displayExcluir;
    },

    async excluirOpcionais(objExcluir) {
      this.opcionais.id = objExcluir.id;
      this.opcionais.tipo = objExcluir.tipo;

      await this.deletarOpcionais(this.opcionais);

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Excluído com Sucesso",
          detail: "Opcionais excluída do banco de dados",
          life: 3000,
        });

        this.$emit('deletedOpcionais');
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

    this.listarOpcionais()
    this.opcionaisPropsData=null;
    this.resetOpcionais()
    },

  },

  mounted() {
    this.listarOpcionais()
  },
});
</script>
<template>
    <div class="card mt-0">
      <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
          <InputText type="text" v-model="opcionais.tipo" placeholder="tipo" class="m-3 w-5"/>
          <ToggleButton v-model="opcionais.temQuantidade" onLabel="Com quantidade" offLabel="Sem quantidade" class="m-3 w-5"/>
          <InputText type="text" v-model="opcionais.quantidade" placeholder="tipo" class="m-3 w-5"/>
      </div>
      <div class="col-12 flex justify-content-center">
        <Button
          @click="cadastrarOpcionais()"
          label="Salvar"
        />
      </div>
    </div>
    <div class="card mt-0">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <DataTable
          :style="{width: '350px'}"
          :value="listaOpcionais"
          responsiveLayout="scroll"
        >
          <Column field="id" header="ID"></Column>
          <Column field="tipo" header="TIPO"></Column>
          <Column field="quantidade" header="QTD"></Column>
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
      <h2 class="flex justify-content-center m-0">Editar Opcionais</h2>
      <div class="confirmation-content">
        <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
          <div class="card ">
            <InputText type="text" v-model="opcionaisPropsData.tipo" placeholder="tipo" class="m-3 w-5"/>
          <ToggleButton v-model="opcionaisPropsData.temQuantidade" onLabel="Com quantidade" offLabel="Sem quantidade" class="m-3 w-5"/>
          <InputText type="text" v-model="opcionaisPropsData.quantidade" placeholder="tipo" class="m-3 w-5"/>
          </div>
        </div>
      </div>
      <template #footer>
          <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayEditar=false, opcionaisPropsData=null"/>
          <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="mudarOpcionais(opcionaisPropsData), displayEditar=false" />
      </template>
    </Dialog>



  <Dialog v-model:visible="displayExcluir" :style="{width: '450px'}" :modal="true" :closable="false">
    <h2 class="flex justify-content-center m-0">Excluir Opcionais</h2>
    <div class="confirmation-content">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
        <div >Excluir: '<b>{{opcionaisPropsData.tipo}}</b>' ?</div>
      </div>
    </div>
    <template #footer>
        <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayExcluir=false, opcionaisPropsData=null"/>
        <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="excluirOpcionais(opcionaisPropsData), displayExcluir=false" />
    </template>
  </Dialog>

</template>
