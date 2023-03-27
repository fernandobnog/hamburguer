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
      listaPedidosLocal: [],
      filtro: false,
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
      await this.listarPedido();
      let pedido = this.listaPedido.find((pedido) => pedido.id == pedidoV.id)
      pedido.statusPedido = pedidoV.statusPedido;
      console.log(pedido);
      await this.editarPedido(pedido);

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

      this.montarListaLocal();

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

    async montarListaLocal() {
      await this.listarPedido();
      this.listaPedidosLocal = Object.assign([], this.listaPedido);
      this.listaPedidosLocal.forEach((pedido) => {
        pedido.hamburguer.saladas = this.formatSaladas(pedido.hamburguer.saladas);
        pedido.hamburguer.opcionais = this.formatOpcionais(pedido.hamburguer.opcionais);
      })
    },
    filtrarLista() {
      if (this.filtro) {
        this.listaPedidosLocal = this.listaPedidosLocal.filter((pedido) => pedido.statusPedido == false);
      } else {
        this.montarListaLocal();
      }
    }

  },
  mounted() {
    this.montarListaLocal();
  },
});
</script>
<template>
  <div class="card mt-0">
    <Button class="m-3" label="Atualizar Pedidos" @click="listarPedido" />
    <ToggleButton class="m-3" v-model="filtro" onLabel="Pendentes" offLabel="Todos" @click="filtrarLista()" />
    <div class="flex flex-wrap align-items-center justify-content-center mt-6">
      <DataTable class="w-12" :value="listaPedidosLocal" responsiveLayout="scroll" removableSort>
        <Column field="pessoa.nome" header="NOME" sortable></Column>
        <Column field="hamburguer.pao.tipo" header="PÃO" sortable></Column>
        <Column field="hamburguer.carne.pesoGramas" header="CARNE (gramas)" sortable></Column>
        <Column field="hamburguer.queijo.tipo" header="QUEIJO"></Column>

        <Column field="hamburguer.saladas" header="SALADAS"></Column>
        <Column field="hamburguer.opcionais" header="OPCIONAIS"></Column>
        <Column field="statusPedido" header="STATUS" sortable>
          <template #body="slotProps">
            <ToggleButton v-model="slotProps.data.statusPedido" onLabel="Feito" offLabel="Pendente" onIcon="pi pi-check"
              offIcon="pi pi-times" @click="mudarPedido(slotProps.data)" />
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>
