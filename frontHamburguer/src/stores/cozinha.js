import { defineStore } from 'pinia'
import { cozinhaService } from '../service/cozinha'
import { errorType } from '../helpers/catchErrors';

export const cozinhaStore = defineStore('idCozinhaStore', {
  state: () => ({
    listaCozinha: [],
    cozinha: {
      id: null,
      pessoa: null,
      hamburguer: null
    },

    statusServer: {
      status: null,
      mensagem: null,
    },

    cozinhaRetorno: {},
  }),

  getters: {
    getCozinha: state => {
      return state.cozinha;
    }
  },

  actions: {
    async listarCozinha() {
      try {
        const res = await cozinhaService.list();
        this.listaCozinha = res.data.object;
        this.listaCozinha = this.listaCozinha.map(cozinha => {
          return {
            id: cozinha.id,
            pessoa: cozinha.pessoa,
            hamburguer: cozinha.hamburguer,
          }
        })
      } catch (error) {
        console.error(error);
      }
    },

    async salvarCozinha() {
      try {
        const res = await cozinhaService.insert(this.cozinha);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.cozinhaRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.cozinhaRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },
    
    async editarCozinha(cozinhaV) {
      try {
        const res = await cozinhaService.edit(cozinhaV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.cozinhaRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.cozinhaRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    
    async deletarCozinha(cozinhaV) {
      try {
        const res = await cozinhaService.delete(cozinhaV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.cozinhaRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.cozinhaRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    resetCozinha() {
      return (
        this.cozinha.id = null,
        this.cozinha.pessoa = null,
        this.cozinha.hamburguer = null
      )
    }
  }
})