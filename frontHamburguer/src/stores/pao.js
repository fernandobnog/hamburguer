import { defineStore } from 'pinia'
import { paoService } from '../service/pao'

export const paoStore = defineStore('idPaoStore', {
  state: () => ({
    listaPao: [],
    pao: {
      id: null,
      tipo: null,
      estoque: null,
    },

    statusServer: {
      status: null,
      mensagem: null,
    },

    paoRetorno: {},
  }),

  getters: {
    getPao: state => {
      return state.pao;
    }
  },

  actions: {
    async listarPao() {
      try {
        const res = await paoService.list();
        this.listaPao = res.data.object;
        this.listaPao = this.listaPao.map(pao => {
          return {
            id: pao.id,
            tipo: pao.tipo,
            estoque: pao.estoque,
          }
        })
      } catch (error) {
        console.error(error);
      }
    },

    async salvarPao() {
      try {
        const res = await paoService.insert(this.pao);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.paoRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.paoRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },
    
    async editarPao(paoV) {
      try {
        const res = await paoService.edit(paoV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.paoRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.paoRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    
    async deletarPao(paoV) {
      try {
        const res = await paoService.delete(paoV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.paoRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.paoRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    resetPao() {
      return (
        this.pao.id = null,
        this.pao.tipo = null,
        this.pao.estoque = null
      )
    }
  }
})