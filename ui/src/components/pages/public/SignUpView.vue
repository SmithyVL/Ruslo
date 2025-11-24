<script lang="ts" setup>
import {ref} from "vue"
import RusloTextField from "@/components/form/RusloTextField.vue"
import RusloSubmitBtn from "@/components/containment/button/RusloSubmitBtn.vue"
import RusloPlainBtn from "@/components/containment/button/RusloPlainBtn.vue"
import RusloSignForm from "@/components/form/RusloSignForm.vue"
import {SignUpDto} from "@/api/dto/request/auth/SignUpDto"
import authApi from "@/api/authApi"
import rules from "@/util/rules"
import RusloDatePicker from "@/components/form/RusloDatePicker.vue";

const form = ref()
const loading = ref()
const errMsg = ref()
const signUpDto = ref(new SignUpDto())

/**
 * Регистрирует пользователя, проверяя валидность введённых данных.
 */
function signUp() {
  if (!form.value) {
    return
  }

  loading.value = true
  authApi.signUp(signUpDto.value)
    .then(() => console.log(`Регистрация успешно завершена`))
    .catch(error => errMsg.value = error.message)
    .finally(() => loading.value = false)
}

/**
 * Динамическое правило, проверяющее уникальность введённого имени пользователя.
 */
const uniqueUsername = () => authApi.isUniqueUsername(signUpDto.value.username).then((value) =>
  value || "Это имя пользователя уже занято. Попробуйте добавить цифры, буквы, нижнее " +
  "подчёркивание или точки"
)
</script>

<template>
  <ruslo-sign-form
    v-model="form"
    :errorMessage="errMsg"
    prependIcon="mdi-account-plus"
    subtitle="Создать учётную запись"
    @submit="signUp"
  >
    <ruslo-text-field
      v-model="signUpDto.email"
      :rules="[rules.required, rules.email]"
      label="Электронная почта"
      required
    />

    <ruslo-text-field
      v-model="signUpDto.username"
      :rules="[rules.required, rules.minLength, rules.maxLength, rules.username, uniqueUsername]"
      hint="Используйте только цифры, буквы, нижнее подчёркивание и точки"
      label="Имя пользователя"
      required
    />

    <ruslo-text-field
      v-model="signUpDto.password"
      :rules="[rules.required, rules.minLength, rules.maxLength]"
      label="Пароль"
      password
      required
    />

    <ruslo-date-picker
      v-model:day="signUpDto.day"
      v-model:month="signUpDto.month"
      v-model:year="signUpDto.year"
      required
    />

    <template #submitAction>
      <ruslo-submit-btn :loading="loading" text="Продолжить"/>
    </template>

    <template #anotherAction>
      <ruslo-plain-btn text="Уже зарегистрированы? Войти" to="sign-in"/>
    </template>
  </ruslo-sign-form>
</template>
