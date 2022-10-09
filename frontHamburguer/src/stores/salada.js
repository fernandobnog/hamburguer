import { defineStore } from 'pinia'
import { saladaService } from '../service/salada'

export const saladaStore = defineStore('idSaladaStore', {
  state: () => ({
    listaSalada: [],
    salada: {
      id: 0,
      tipo: '',
    },

    statusServer: {
      status: null,
      mensagem: null,
    },

    saladaRetorno: {},
  }),

  getters: {
    getSalada: state => {
      return state.salada;
    }
  },

  actions: {
    async listarSalada() {
      try {
        const res = await saladaService.list();
        this.listaSalada = res.data.object;
        this.listaSalada = this.listaSalada.map(salada => {
          return {
            id: salada.id,
            tipo: salada.tipo,
          }
        })
      } catch (error) {
        console.error(error);
      }
    },

    async salvarSalada() {
      try {
        const res = await saladaService.insert(this.salada);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.saladaRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.saladaRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },
    
    async editarSalada(saladaV) {
      try {
        const res = await saladaService.edit(saladaV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.saladaRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.saladaRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    
    async deletarSalada(saladaV) {
      try {
        const res = await saladaService.delete(saladaV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.saladaRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.saladaRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    resetSalada() {
      return (
        this.salada.id = 0,
        this.salada.tipo = ''
      )
    }
  }
})