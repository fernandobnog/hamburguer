<script>
import { defineComponent } from "vue";

export default defineComponent({
  props: {
    tituloPagina: String
  },

  data() {
    return {
      classes: [
        'surface-0',
        'box',
        'shadow-2',
        'mb-3',
        'flex',
        'justify-content-center',
        'border-round-bottom'
      ],
    };
  },
});
</script>

<template>
   <div :class="classes">
      <h2 class="text-700">
        {{ tituloPagina }}
      </h2>
    </div>
</template>

<style>
h2 {
  margin-top: 0.75rem;
  margin-bottom: 0.75rem;
}
</style>
