import shared from '@signed/prettier-config' assert { type: 'json'}

export default {
  ...shared,
  plugins: ['prettier-plugin-tailwindcss'],
}
