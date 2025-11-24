/**
 * Правила для текстовых полей.
 */
export default {
  required: (value: any) => !!value || 'Вы пропустили это поле',
  minLength: (value: string) => value.length >= 2 || 'Введите не менее 2 знаков',
  maxLength: (value: string) => value.length <= 32 || 'Введите не более 32 знаков',
  username: (value: string) => /^[a-zA-Zа-яА-Я0-9_.]+$/.test(value) ||
    'Используйте только цифры, буквы, нижнее подчёркивание и точки',
  email: (value: string) => /^[a-z.-]+@[a-z.-]+\.[a-z]+$/i.test(value) ||
    'Адрес электронной почти должен быть описан в корректном формате',
}
