<template>
  <v-container class="fill-height">
    <v-responsive>
      <div class="text-center">
        <div class="text-body-2 font-weight-light mb-n1">Добро пожаловать в</div>

        <h1 class="text-h2 font-weight-bold">РУСЛО</h1>

        <v-container>
          <v-row align="center" justify="center">
            <v-col cols="auto">
              <v-btn density="comfortable" prepend-icon="mdi-account" to="sign-in">
                <template v-slot:prepend>
                  <v-icon color="green-darken-2"></v-icon>
                </template>

                Войти
              </v-btn>
            </v-col>

            <v-col cols="auto">
              <v-btn density="comfortable" prepend-icon="mdi-account-plus" to="sign-up">
                <template v-slot:prepend>
                  <v-icon color="blue-darken-2"></v-icon>
                </template>

                Регистрация
              </v-btn>
            </v-col>
          </v-row>
        </v-container>
      </div>
    </v-responsive>
  </v-container>
</template>
