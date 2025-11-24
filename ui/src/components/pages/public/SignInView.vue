<script lang="ts" setup>
import {ref} from "vue"
import RusloTextField from "@/components/form/RusloTextField.vue"
import RusloSubmitBtn from "@/components/containment/button/RusloSubmitBtn.vue"
import RusloPlainBtn from "@/components/containment/button/RusloPlainBtn.vue"
import RusloSignForm from "@/components/form/RusloSignForm.vue"
import {SignInDto} from "@/api/dto/request/auth/SignInDto"
import rules from "@/util/rules"
import authApi from "@/api/authApi"

const form = ref()
const loading = ref()
const errMsg = ref()
const signInDto = ref(new SignInDto())

function signIn() {
  if (!form.value) {
    return
  }

  loading.value = true
  authApi.signIn(signInDto.value)
    .then(() => console.log(`Выполнен вход`))
    .catch((error) => errMsg.value = error.message)
    .finally(() => loading.value = false)
}
</script>

<template>
  <ruslo-sign-form v-model="form" :errorMessage="errMsg" @submit="signIn">
    <ruslo-text-field
      v-model="signInDto.username"
      :rules="[rules.required]"
      label="Имя пользователя"
      required
    />

    <ruslo-text-field
      v-model="signInDto.password"
      :rules="[rules.required]"
      label="Пароль"
      password
      required
    />

    <template #submitAction>
      <ruslo-submit-btn :loading="loading" text="Вход"/>
    </template>

    <template #anotherAction>
      <ruslo-plain-btn text="Зарегистрироваться" to="sign-up"/>
    </template>
  </ruslo-sign-form>
</template>
