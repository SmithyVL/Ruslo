<script lang="ts" setup>
import {ref} from "vue"
import {useSnackbarStore} from "@/store/snackbarStore"
import authApi from "@/api/authApi"
import customRules from "@/util/rules"
import {SignUpDto} from "@/api/dto/request/auth/SignUpDto"

const snackbars = useSnackbarStore()
const form = ref(false)
const signUpDto = ref(new SignUpDto())
const showPassword = ref(false)

/**
 * Регистрирует пользователя, проверяя валидность введённых данных.
 */
function signUp() {
  if (!form.value) {
    return
  }

  authApi.signUp(signUpDto.value)
    .then(() => snackbars.addSuccess(`Регистрация успешно завершена`))
    .catch(error => snackbars.addError(error.message))
}
</script>

<template>
  <v-container class="fill-height">
    <v-responsive class="mx-auto" max-width="450">
      <v-form v-model="form" @submit.prevent="signUp">
        <v-card prepend-icon="mdi-account-plus" subtitle="Создать учётную запись">
          <template v-slot:prepend>
            <v-icon color="green-darken-2" size="x-large"/>
          </template>

          <template v-slot:title>
            <span class="font-weight-black">РУСЛО</span>
          </template>

          <v-card-item class="pb-0 pt-0 pl-2 pr-2">
            <span class="text-subtitle-1">
              Имя пользователя <span class="text-red-lighten-1">*</span>
            </span>
            <v-text-field
              v-model="signUpDto.username"
              :rules="[customRules.required, customRules.username, customRules.usernameSize]"
              clearable
              density="compact"
              hint="Используйте только цифры, буквы, нижнее подчёркивание и точки"
            />

            <span class="text-subtitle-1">
              Пароль <span class="text-red-lighten-1">*</span>
            </span>
            <v-text-field
              v-model="signUpDto.password"
              :append-inner-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
              :rules="[customRules.required, customRules.passwordSize]"
              :type="showPassword ? 'text' : 'password'"
              clearable
              density="compact"
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
                  type="submit"
                  variant="flat"
                />
              </v-row>

              <v-row class="mt-4">
                <v-btn
                  color="blue-lighten-1"
                  size="small"
                  text="Уже зарегистрированы? Войти"
                  to="sign-in"
                  variant="plain"
                />
              </v-row>
            </v-col>
          </v-card-actions>
        </v-card>
      </v-form>
    </v-responsive>
  </v-container>
</template>
