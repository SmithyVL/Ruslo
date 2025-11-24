<script lang="ts" setup>
defineProps({
  label: String,
  items: Array<any>,
})

const model = defineModel()
</script>

<template>
  <v-select
    v-model="model"
    :items="items"
    :label="label"
    density="compact"
    hide-details
  />
</template>
