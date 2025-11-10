/**
 * Правила для текстовых полей.
 */
export default {
  required: (value: any) => !!value || 'Вы пропустили это поле',
  username: (value: string) => /^[a-zA-Zа-яА-Я0-9_.]+$/.test(value) ||
    'Используйте только цифры, буквы, нижнее подчёркивание и точки',
  usernameSize: (value: string) => value.length <= 32 ||
    'Введите не более 32 знаков',
  passwordSize: (value: string) => value.length >= 8 ||
    'Введите не менее 8 знаков',
}
