import { defineStore } from 'pinia'
import { pedidoService } from '../service/pedido'

export const pedidoStore = defineStore('idPedidoStore', {
  state: () => ({
    listaPedido: [],
    pedido: {
      id: null,
      pessoa: null,
      hamburguer: null,
      statusPedido: null,
    },

    statusServer: {
      status: null,
      mensagem: null,
    },

    pedidoRetorno: {},
  }),

  getters: {
    getPedido: state => {
      return state.pedido;
    }
  },

  actions: {

    async listarPedido() {
      try {
        const res = await pedidoService.list();
        this.listaPedido = res.data.object;
        this.listaPedido = this.listaPedido.map(pedido => {
          return {
            id: pedido.id,
            pessoa: pedido.pessoa,
            hamburguer: pedido.hamburguer,
            statusPedido: pedido.statusPedido
          }
        })
      } catch (error) {
        console.error(error);
      }
    },

    async salvarPedido() {
      try {
        const res = await pedidoService.insert(this.pedido);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.pedidoRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.pedidoRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },
    
    async editarPedido(pedidoV) {
      try {
        const res = await pedidoService.edit(pedidoV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.pedidoRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.pedidoRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    
    async deletarPedido(pedidoV) {
      try {
        const res = await pedidoService.delete(pedidoV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.pedidoRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.pedidoRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    resetPedido() {
      return (
        this.pedido.id = null,
        this.pedido.pessoa = null,
        this.pedido.hamburguer = null
      )
    }
  }
})