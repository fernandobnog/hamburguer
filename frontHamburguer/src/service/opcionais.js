import axios from 'axios'

export const opcionaisService = {
  list: () => {
    return axios.get('opcionais/listar');
  },
  insert: (opcionais) => {
    return axios.post('opcionais/inserir', opcionais);
  },
  
  edit: (opcionais) => {
    return axios.post('opcionais/editar', opcionais);
  },
  delete: (opcionais) => {
    return axios.post('opcionais/excluir', opcionais);
  },
}