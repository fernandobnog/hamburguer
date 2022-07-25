<script>
import { defineComponent } from "vue";
import { pessoaStore } from "../../stores/pessoa";
import { mapState, mapActions } from "pinia";

export default defineComponent({

  data() {
    return {
      displayEditar: false,
      displayExcluir: false,
      pessoaPropsData: null,
    };
  },

  computed: {
    ...mapState(pessoaStore, [
      "listaPessoa",
      "pessoa",
      "statusServer",
      "pessoaRetorno",
    ]),
  },

  methods: {
    ...mapActions(pessoaStore, [
      "listarPessoa",
      "salvarPessoa",
      "deletarPessoa",
      "resetPessoa",
      "editarPessoa",
    ]),

    async cadastrarPessoa() {
      await this.salvarPessoa();

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Salvo com Sucesso",
          detail: "Pessoa inserida no banco de dados",
          life: 3000,
        });

        this.$emit('savedPessoa');
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
    this.listarPessoa();
    this.resetPessoa();
    },
    
    confirmarEditar(obj){
      this.pessoaPropsData = obj;
      this.displayEditar = !this.displayEditar;
    },

    async mudarPessoa(objEditar) {
      
      this.pessoa.id = objEditar.id;
      this.pessoa.nome = objEditar.nome;
      console.log('objEditar:' + this.pessoa)

      await this.editarPessoa(this.pessoa);

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Editado com Sucesso",
          detail: "Pessoa Editado no banco de dados",
          life: 3000,
        });

        this.$emit('editedPessoa');
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
    this.listarPessoa()
    this.pessoaPropsData=null;
    this.resetPessoa()
    },

    confirmarExcluir(obj){
      this.pessoaPropsData = obj;
      this.displayExcluir = !this.displayExcluir;
    },

    async excluirPessoa(objExcluir) {
      this.pessoa.id = objExcluir.id;
      this.pessoa.tipo = objExcluir.tipo;

      await this.deletarPessoa(this.pessoa);

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Excluído com Sucesso",
          detail: "Pessoa excluída do banco de dados",
          life: 3000,
        });

        this.$emit('deletedPessoa');
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

    this.listarPessoa()
    this.pessoaPropsData=null;
    this.resetPessoa()
    },

  },

  mounted() {
    this.listarPessoa()
  },
});
</script>
<template>
    <div class="card mt-0">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <InputText type="text" v-model="pessoa.nome" />
      </div>
      <div class="col-12 flex justify-content-center">
        <Button
          @click="cadastrarPessoa()"
          label="Salvar"
        />
      </div>
    </div>
    <div class="card mt-0">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <DataTable
          :style="{width: '350px'}"
          :value="listaPessoa"
          responsiveLayout="scroll"
        >
          <Column field="id" header="ID"></Column>
          <Column field="nome" header="Nome"></Column>
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
      <h2 class="flex justify-content-center m-0">Editar Pessoa</h2>
      <div class="confirmation-content">
        <div class="flex flex-wrap align-items-center justify-content-center mt-6">
          <InputText type="text" placeholder="peso em gramas" v-model="pessoaPropsData.nome" />
        </div>
      </div>
      <template #footer>
          <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayEditar=false, pessoaPropsData=null"/>
          <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="mudarPessoa(pessoaPropsData), displayEditar=false" />
      </template>
    </Dialog>



  <Dialog v-model:visible="displayExcluir" :style="{width: '450px'}" :modal="true" :closable="false">
    <h2 class="flex justify-content-center m-0">Excluir Pessoa</h2>
    <div class="confirmation-content">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
        <div >Excluir: '<b>{{pessoaPropsData.nome}}</b>' ?</div>
      </div>
    </div>
    <template #footer>
        <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayExcluir=false, pessoaPropsData=null"/>
        <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="excluirPessoa(pessoaPropsData), displayExcluir=false" />
    </template>
  </Dialog>

</template>
