import axios from 'axios'

export const saladaService = {
  list: () => {
    return axios.get('salada/listar');
  },
  insert: (salada) => {
    return axios.post('salada/inserir', salada);
  },
  
  edit: (salada) => {
    return axios.post('salada/editar', salada);
  },
  delete: (salada) => {
    return axios.post('salada/excluir', salada);
  },
}