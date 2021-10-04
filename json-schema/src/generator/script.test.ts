import { generateTypesFromSchema } from './script'
import { ensureCorrectTypePropertyInSettings, generateSettingsSchema } from './schema-generator'

test.skip('run it', () => {
  ensureCorrectTypePropertyInSettings()
})

test.skip('run settings schema generator', () => {
  generateSettingsSchema()
})

test.skip('generates the code', async () => {
  await generateTypesFromSchema()
})

test.skip('all', async () => {
  ensureCorrectTypePropertyInSettings()
  generateSettingsSchema()
  await generateTypesFromSchema()
})
