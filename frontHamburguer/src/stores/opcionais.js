import { defineStore } from 'pinia'
import { opcionaisService } from '../service/opcionais'
import { errorType } from '../helpers/catchErrors';

export const opcionaisStore = defineStore('idOpcionaisStore', {
  state: () => ({
    listaOpcionais: [],
    opcionais: {
      id: 0,
      tipo: '',
    },

    statusServer: {
      status: null,
      mensagem: null,
    },

    opcionaisRetorno: {},
  }),

  getters: {
    getOpcionais: state => {
      return state.opcionais;
    }
  },

  actions: {
    async listarOpcionais() {
      try {
        const res = await opcionaisService.list();
        this.listaOpcionais = res.data.object;
        this.listaOpcionais = this.listaOpcionais.map(opcionais => {
          return {
            id: opcionais.id,
            tipo: opcionais.tipo,
          }
        })
      } catch (error) {
        console.error(error);
      }
    },

    async salvarOpcionais() {
      try {
        const res = await opcionaisService.insert(this.opcionais);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.opcionaisRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.opcionaisRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },
    
    async editarOpcionais(opcionaisV) {
      try {
        const res = await opcionaisService.edit(opcionaisV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.opcionaisRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.opcionaisRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    
    async deletarOpcionais(opcionaisV) {
      try {
        const res = await opcionaisService.delete(opcionaisV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.opcionaisRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.opcionaisRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    resetOpcionais() {
      return (
        this.opcionais.id = 0,
        this.opcionais.tipo = ''
      )
    }
  }
})