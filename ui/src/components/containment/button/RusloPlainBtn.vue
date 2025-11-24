<script lang="ts" setup>
defineProps({
  text: String,
  to: String,
})
</script>

<template>
  <v-btn :text="text" :to="to" color="blue-lighten-1" size="small" variant="plain"/>
</template>
