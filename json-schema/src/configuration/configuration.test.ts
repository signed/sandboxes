import { settingFor } from './configuration'

test('return the value from the settings', () => {
  const language = settingFor('general.language', [{ type: 'general.language', value: 'ES' }])
  expect(language).toBe('ES')
})

test('return default if it exists and not explicit setting', () => {
  const language = settingFor('general.language', [])
  expect(language).toBe('EN')
})

test('values without a default can be undefined', () => {
  const setting = settingFor('editor.auto-save', [
    { type: 'editor.auto-save', value: { value: true, interval: 500 } },
  ])
  // @ts-expect-error type should communicate that the setting can be undefined
  setting.value
  expect(setting?.value).toEqual(true)
  expect(setting?.interval).toEqual(500)
})

test('values without a default can be undefined', () => {
  const setting = settingFor('editor.auto-save', [])
  expect(setting).toBe(undefined)
})
