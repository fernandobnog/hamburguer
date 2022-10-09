import { defineStore } from 'pinia'
import { pessoaService } from '../service/pessoa'

export const pessoaStore = defineStore('idPessoaStore', {
  state: () => ({
    listaPessoa: [],
    pessoa: {
      id: null,
      nome: null
    },

    statusServer: {
      status: null,
      mensagem: null,
    },

    pessoaRetorno: {},
  }),

  getters: {
    getPessoa: state => {
      return state.pessoa;
    }
  },

  actions: {
    async listarPessoa() {
      try {
        const res = await pessoaService.list();
        this.listaPessoa = res.data.object;
        this.listaPessoa = this.listaPessoa.map(pessoa => {
          return {
            id: pessoa.id,
            nome: pessoa.nome,
          }
        })
      } catch (error) {
        console.error(error);
      }
    },

    async salvarPessoa() {
      try {
        const res = await pessoaService.insert(this.pessoa);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.pessoaRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.pessoaRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },
    
    async editarPessoa(pessoaV) {
      try {
        const res = await pessoaService.edit(pessoaV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.pessoaRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.pessoaRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    
    async deletarPessoa(pessoaV) {
      try {
        const res = await pessoaService.delete(pessoaV);
        this.statusServer.status = res.data.status;
        this.statusServer.mensagem = res.data.mensagem;

        switch (this.statusServer.status) {
          case "OK":
            this.pessoaRetorno = res.data.object
            break;
          case "Erro":
            this.statusServer.mensagem = res.data.mensagem;
            break;
          default:
            this.pessoaRetorno = res.data.object
            break;
        }

        return res
      } catch (error) {
        console.log(error);
      }
    },

    resetPessoa() {
      return (
        this.pessoa.id = null,
        this.pessoa.nome = null
      )
    }
  }
})