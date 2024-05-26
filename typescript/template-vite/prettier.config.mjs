import signed from '@signed/prettier-config'

const config = {
  ...signed,
  plugins: ['prettier-plugin-tailwindcss'],
}

export default config
