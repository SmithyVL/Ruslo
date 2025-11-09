<script lang="ts" setup>
import {ref} from "vue"
import {useSnackbarStore} from "@/store/snackbarStore"
import authApi from "@/api/authApi"
import {SignUpDto} from "@/api/dto/request/auth/SignUpDto"

const snackbars = useSnackbarStore()
const signUpDto = ref(new SignUpDto())
const showPassword = ref(false)

function signUp() {
  authApi.signUp(signUpDto.value)
    .then(() => snackbars.addSuccess(`Регистрация успешно завершена`))
    .catch(error => snackbars.addError(error.message))
}
</script>

<template>
  <v-container class="fill-height">
    <v-responsive class="mx-auto" max-width="450">
      <v-card prepend-icon="mdi-account-plus" subtitle="Создание нового аккаунта">
        <template v-slot:prepend>
          <v-icon color="green-darken-2" size="x-large"/>
        </template>

        <template v-slot:title>
          <span class="font-weight-black">РУСЛО</span>
        </template>

        <v-card-item class="pb-0 pt-0 pl-2 pr-2">
          <v-text-field
            v-model="signUpDto.username"
            label="Имя пользователя"
          />

          <v-text-field
            v-model="signUpDto.password"
            :append-inner-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
            :type="showPassword ? 'text' : 'password'"
            label="Пароль"
            @click:append-inner="showPassword = !showPassword"
          />
        </v-card-item>

        <v-card-actions>
          <v-col>
            <v-row>
              <v-btn
                block
                color="green-darken-2"
                text="Продолжить"
                variant="flat"
                @click="signUp"
              />
            </v-row>

            <v-row class="mt-4">
              <v-btn
                color="blue-lighten-1"
                size="small"
                text="Уже есть аккаунт"
                to="sign-in"
                variant="plain"
              />
            </v-row>
          </v-col>
        </v-card-actions>
      </v-card>
    </v-responsive>
  </v-container>
</template>
