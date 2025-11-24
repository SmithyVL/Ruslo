<script lang="ts" setup>
defineProps({
  text: String,
  loading: Boolean,
})
</script>

<template>
  <v-btn :loading="loading" :text="text" block color="green-darken-2" type="submit" variant="flat"/>
</template>
