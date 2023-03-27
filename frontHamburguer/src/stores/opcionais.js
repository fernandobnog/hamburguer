import { defineStore } from 'pinia'
import { opcionaisService } from '../service/opcionais'

export const opcionaisStore = defineStore('idOpcionaisStore', {
  state: () => ({
    listaOpcionais: [],
    opcionais: {
      id: 0,
      tipo: '',
      quantidade: 0,
      temQuantidade: false,
    },

    statusServer: {
      status: null,
      mensagem: null,
    },

    opcionaisRetorno: {},
  }),

  actions: {
    async listarOpcionais() {
      try {
        const res = await opcionaisService.list();
        this.listaOpcionais = res.data.object;
        this.listaOpcionais = this.listaOpcionais.map(opcionais => {
          return {
            id: opcionais.id,
            tipo: opcionais.tipo,
            quantidade: opcionais.quantidade,
            temQuantidade: opcionais.temQuantidade,
          }
        })
      } catch (error) {
        console.error(error);
      }
    },

    async salvarOpcionais() {
      if(!this.opcionais.temQuantidade){
        this.opcionais.quantidade = 0;
      }
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
      if(!opcionaisV.temQuantidade){
        opcionaisV.quantidade = 0;
      }
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
        this.opcionais.tipo = '',
        this.opcionais.quantidade = 0,
        this.opcionais.temQuantidade = false
      )
    }
  }
})