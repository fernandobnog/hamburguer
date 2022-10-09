import { defineStore } from 'pinia'
import { hamburguerService } from '../service/hamburguer'
import { errorType } from '../helpers/catchErrors';

export const hamburguerStore = defineStore('idHamburguerStore', {
  state: () => ({
    listaHamburguer: [],
    hamburguer: {
      id: null,
      pao: null,
      carne: null,
      queijo: null,
      saladas: [],
      opcionais: [],
    },

    statusServer: {
      status: null,
      mensagem: null,
    },

    hamburguerRetorno: {},
  }),

  getters: {
    getHamburguer: state => {
      return state.hamburguer;
    }
  },

  actions: {
    async listarHamburguer() {
      try {
        const res = await hamburguerService.list();
        this.listaHamburguer = res.data.object;
        this.listaHamburguer = this.listaHamburguer.map(hamburguer => {
          return {
            id: hamburguer.id,
            pao: hamburguer.pao,
            carne: hamburguer.carne,
            queijo: hamburguer.queijo,
            saladas: hamburguer.saladas,
            opcionais: hamburguer.opcionais,
          }
        })
      } catch (error) {
        console.error(error);
      }
    },

    async salvarHamburguer() {
      try {
        const res = await hamburguerService.insert(this.hamburguer);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.hamburguerRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.hamburguerRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },
    
    async editarHamburguer(hamburguerV) {
      try {
        const res = await hamburguerService.edit(hamburguerV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.hamburguerRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.hamburguerRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    
    async deletarHamburguer(hamburguerV) {
      try {
        const res = await hamburguerService.delete(hamburguerV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.hamburguerRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.hamburguerRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    resetHamburguer() {
      return (
        this.hamburguer.id = null,
        this.hamburguer.pao = null,
        this.hamburguer.carne = null,
        this.hamburguer.queijo = null,
        this.hamburguer.saladas = [],
        this.hamburguer.opcionais = []
      )
    }
  }
})