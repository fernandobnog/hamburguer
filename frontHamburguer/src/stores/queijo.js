import { defineStore } from 'pinia'
import { queijoService } from '../service/queijo'
export const queijoStore = defineStore('idQueijoStore', {
  state: () => ({
    listaQueijo: [],
    queijo: {
      id: null,
      tipo: null,
      estoque: null,
    },

    statusServer: {
      status: null,
      mensagem: null,
    },

    queijoRetorno: {},
  }),

  getters: {
    getQueijo: state => {
      return state.queijo;
    }
  },

  actions: {
    async listarQueijo() {
      try {
        const res = await queijoService.list();
        this.listaQueijo = res.data.object;
        this.listaQueijo = this.listaQueijo.map(queijo => {
          return {
            id: queijo.id,
            tipo: queijo.tipo,
            estoque: queijo.estoque,
          }
        })
      } catch (error) {
        console.error(error);
      }
    },

    async salvarQueijo() {
      try {
        const res = await queijoService.insert(this.queijo);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.queijoRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.queijoRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },
    
    async editarQueijo(queijoV) {
      try {
        const res = await queijoService.edit(queijoV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.queijoRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.queijoRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    
    async deletarQueijo(queijoV) {
      try {
        const res = await queijoService.delete(queijoV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.queijoRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.queijoRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    resetQueijo() {
      return (
        this.queijo.id = null,
        this.queijo.tipo = null,
        this.queijo.estoque = null
      )
    }
  }
})