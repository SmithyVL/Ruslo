<template>
  <v-app @click.right.prevent>
    <router-view/>

    <v-snackbar-queue v-model="snackbars.queue" timer="blue-grey-darken-1"/>
  </v-app>
</template>

<script lang="ts" setup>
import {useSnackbarStore} from "@/store/snackbarStore"

const snackbars = useSnackbarStore()
</script>
