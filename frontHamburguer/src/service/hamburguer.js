import axios from 'axios'

export const hamburguerService = {
  list: () => {
    return axios.get('hamburguer/listar');
  },
  insert: (hamburguer) => {
    return axios.post('hamburguer/inserir', hamburguer);
  },
  
  edit: (hamburguer) => {
    return axios.post('hamburguer/editar', hamburguer);
  },
  delete: (hamburguer) => {
    return axios.post('hamburguer/excluir', hamburguer);
  },
}