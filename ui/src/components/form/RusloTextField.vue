<script lang="ts" setup>
import RusloRequiredStar from "@/components/form/RusloRequiredStar.vue";

defineProps({
  label: String,
  hint: String,
  rules: Array<any>,
  password: Boolean,
  required: Boolean,
  loading: Boolean,
})

const model = defineModel({required: true})
</script>

<template>
  <div>
    <v-label v-show="label" :text="label"/>
    <ruslo-required-star v-show="required"/>
    <v-text-field
      v-model="model"
      :hint="hint"
      :loading="loading"
      :rules="rules"
      :type="password ? 'password' : 'text'"
      class="mt-1"
      density="compact"
    />
  </div>
</template>
