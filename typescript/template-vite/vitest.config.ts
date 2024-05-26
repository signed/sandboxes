import { defineConfig } from 'vitest/config'

export default defineConfig({
  test: {
    include: ['**/*.tests.ts?(x)'],
    environment: 'jsdom',
    setupFiles: ['./src/setup.ts'],
    globals: true,
  },
})
