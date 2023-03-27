<script>
import { defineComponent } from "vue";
import { pedidoStore } from "../../stores/pedido";
import { mapState, mapActions } from "pinia";

export default defineComponent({
  components: {
  },
  data() {
    return {
      stringOpcionais: '',
      stringSaladas: '',
    };
  },
  computed: {
    ...mapState(pedidoStore, [
      "listaPedido",
      "pedido",
      "statusServer",
      "pedidoRetorno",
    ]),
  },
  methods: {
    ...mapActions(pedidoStore, [
      "listarPedido",
      "salvarPedido",
      "deletarPedido",
      "resetPedido",
      "editarPedido",
    ]),



    async mudarPedido(pedidoV) {
      await this.editarPedido(pedidoV);

      if (this.statusServer.status == "OK") {
        this.$toast.add({
          severity: "success",
          summary: "Editado com Sucesso",
          detail: "Hamburguer Editado no banco de dados",
          life: 3000,
        });

        this.$emit('editedHamburguer');
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

      this.listarPedido();

      this.resetPedido();

    },

    formatSaladas(listaSaladas) {
      this.stringSaladas = '';
      listaSaladas.forEach((salada) => {
        this.stringSaladas += salada.tipo + ', '
      })
      return this.stringSaladas = this.stringSaladas.substring(0, this.stringSaladas.length - 2)

    },
    formatOpcionais(listaOpcionais) {
      this.stringOpcionais = '';
      listaOpcionais.forEach((opcionais) => {
        this.stringOpcionais += opcionais.tipo + ', '
      })
      return this.stringOpcionais = this.stringOpcionais.substring(0, this.stringOpcionais.length - 2)

    },

  },
  mounted() {},
});
</script>
<template>
  <div class="card mt-0">
    <Button label="Atualizar Pedidos"  @click="listarPedido"/>
    <div class="flex flex-wrap align-items-center justify-content-center mt-6">
      <DataTable class="w-12" :value="listaPedido" responsiveLayout="scroll" sortMode="multiple" removableSort>
        <Column rowReorder headerStyle="width: 3rem" :reorderableColumn="false" />
        <Column field="pessoa.nome" sortable header="NOME"></Column>
        <Column field="hamburguer.pao.tipo" header="PÃO"></Column>
        <Column field="hamburguer.carne.pesoGramas" sortable header="CARNE (gramas)"></Column>
        <Column field="hamburguer.queijo.tipo" header="QUEIJO"></Column>

      </DataTable>
    </div>
  </div>
</template>
