<script lang="ts" setup>
import RusloSelect from "@/components/form/RusloSelect.vue"
import RusloRequiredStar from "@/components/form/RusloRequiredStar.vue";

defineProps({
  label: {
    type: String,
    default: "Дата рождения",
  },
  required: Boolean,
})

const day = defineModel("day", {type: Number})
const month = defineModel("month", {type: Number})
const year = defineModel("year", {type: Number})

const days = Array.from({length: 31}, (_, i) => i + 1)
const months = [
  {title: 'Январь', value: 1},
  {title: 'Февраль', value: 2},
  {title: 'Март', value: 3},
  {title: 'Апрель', value: 4},
  {title: 'Май', value: 5},
  {title: 'Июнь', value: 6},
  {title: 'Июль', value: 7},
  {title: 'Август', value: 8},
  {title: 'Сентябрь', value: 9},
  {title: 'Октябрь', value: 10},
  {title: 'Ноябрь', value: 11},
  {title: 'Декабрь', value: 12},
]
const years = Array
  .from({length: new Date().getFullYear() - 1909}, (_, i) => i + 1909)
  .reverse()
</script>

<template>
  <div>
    <v-label v-show="label" :text="label"/>
    <ruslo-required-star v-show="required"/>
    <v-row class="ma-0 mt-1">
      <ruslo-select v-model="day" :items="days" label="День"/>
      <ruslo-select v-model="month" :items="months" class="ml-4" label="Месяц"/>
      <ruslo-select v-model="year" :items="years" class="ml-4" label="Год"/>
    </v-row>
  </div>
</template>
