<script>
import { defineComponent } from "vue";
import { menuItems } from './mixin'

export default defineComponent({
  data() {
    return {
      items: menuItems,
    };
  },
});
</script>

<template>
  <div>
    <Menubar :model="items" orientation="vertical" />
  </div>
</template>

<style lang="scss">
.p-menubar {
  justify-content: space-between;

  ul {
    z-index: 100;
  }
}
</style>
