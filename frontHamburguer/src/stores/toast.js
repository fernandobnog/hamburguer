import { defineStore } from 'pinia';

export const toastStore = defineStore('toast', {
  state: () => ({
    snack: {
      severity: '',
      summary: '',
      detail: '',
      life: 3000,
    }
  }),
  getters: {
    getSnack: state => state.snack,
  },
  actions: {
    addToast(toast) {
      this.snack = toast;
    }
  }
})