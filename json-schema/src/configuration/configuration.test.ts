import { settingFor, settingFor2 } from './configuration'
import { SettingsDocument } from './parser'

test('return the value from the settings', () => {
  const language = settingFor([{ type: 'general.language', value: 'ES' }], 'general.language')
  expect(language).toBe('ES')
})

test('return default if it exists and not explicit setting', () => {
  const language = settingFor([], 'general.language')
  expect(language).toBe('EN')
})

test('values without a default can be undefined', () => {
  const setting = settingFor([
    { type: 'editor.auto-save', value: { value: true, interval: 500 } },
  ], 'editor.auto-save')
  // @ts-expect-error type should communicate that the setting can be undefined
  setting.value
  expect(setting?.value).toEqual(true)
  expect(setting?.interval).toEqual(500)
})

test('values without a default can be undefined', () => {
  const setting = settingFor([], 'editor.auto-save')
  expect(setting).toBe(undefined)
})

test('quick and dirty', () => {
  const settings: SettingsDocument<'general.language'| "ui.theme" | "ui.mode" | "editor.auto-save"> = {}

  const language = settingFor2(settings, 'general.language')
  const theme = settingFor2(settings, "ui.theme")
  const mode = settingFor2(settings, "ui.mode")
  const autosave = settingFor2(settings, "editor.auto-save")
})
