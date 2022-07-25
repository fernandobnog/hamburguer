import axios from 'axios'

export const cozinhaService = {
  list: () => {
    return axios.get('cozinha/listar');
  },
  insert: (cozinha) => {
    return axios.post('cozinha/inserir', cozinha);
  },
  
  edit: (cozinha) => {
    return axios.post('cozinha/editar', cozinha);
  },
  delete: (cozinha) => {
    return axios.post('cozinha/excluir', cozinha);
  },
}