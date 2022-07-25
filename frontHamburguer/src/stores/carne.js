import { defineStore } from 'pinia'
import { carneService } from '../service/carne'
import { errorType } from '../helpers/catchErrors';

export const carneStore = defineStore('idCarneStore', {
  state: () => ({
    listaCarne: [],
    carne: {
      id: null,
      pesoGramas: null,
      estoque: null
    },

    statusServer: {
      status: null,
      mensagem: null,
    },

    carneRetorno: {},
  }),

  getters: {
    getCarne: state => {
      return state.carne;
    }
  },

  actions: {
    async listarCarne() {
      try {
        const res = await carneService.list();
        this.listaCarne = res.data.object;
        this.listaCarne = this.listaCarne.map(carne => {
          return {
            id: carne.id,
            pesoGramas: carne.pesoGramas,
            estoque: carne.estoque,
          }
        })
      } catch (error) {
        console.error(error);
      }
    },

    async salvarCarne() {
      try {
        const res = await carneService.insert(this.carne);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.carneRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.carneRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },
    
    async editarCarne(carneV) {
      try {
        const res = await carneService.edit(carneV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.carneRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.carneRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    
    async deletarCarne(carneV) {
      try {
        const res = await carneService.delete(carneV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.carneRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.carneRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    resetCarne() {
      return (
        this.carne.id = null,
        this.carne.pesoGramas = null,
        this.carne.estoque = null
      )
    }
  }
})