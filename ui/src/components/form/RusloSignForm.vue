<script lang="ts" setup>
defineProps({
  prependIcon: {
    type: String,
    default: "mdi-account",
  },
  subtitle: {
    type: String,
    default: "С возвращением! Мы так рады видеть вас снова!",
  },
  errorMessage: String,
})

const model = defineModel({required: true, default: false})
const emits = defineEmits(["submit"])
</script>

<template>
  <v-container class="fill-height">
    <v-responsive class="mx-auto" max-width="450">
      <v-form v-model="model" fast-fail @submit.prevent="emits('submit')">
        <v-card :prepend-icon="prependIcon" :subtitle="subtitle" title="РУСЛО">
          <template v-slot:prepend>
            <v-icon color="green-darken-2" size="x-large"/>
          </template>

          <v-card-text class="pl-2 pr-2 pb-0">
            <slot/>
          </v-card-text>

          <v-card-actions>
            <v-col>
              <v-row>
                <slot name="submitAction"/>
              </v-row>

              <v-row v-show="errorMessage" class="mt-5">
                <v-spacer/>

                <v-label class="text-error">
                  <v-icon icon="mdi-flash"/>
                  {{ errorMessage }}
                  <v-icon icon="mdi-flash"/>
                </v-label>

                <v-spacer/>
              </v-row>

              <v-row class="mt-4">
                <slot name="anotherAction"/>
              </v-row>
            </v-col>
          </v-card-actions>
        </v-card>
      </v-form>
    </v-responsive>
  </v-container>
</template>
