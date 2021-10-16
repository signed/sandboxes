import { assertType, Maybe } from '../asserts'
import { SupportedLanguage, SupportedMode, SupportedTheme } from '../generated/settings'
import { settingFor } from './configuration'
import { SettingsDocument } from './parser'

type UsedSettings = 'general.language'| "ui.theme" | "ui.mode" | "editor.auto-save"

test('return the value from the settings', () => {
  const document: SettingsDocument<UsedSettings> = {}
  document['general.language'] = { type: 'general.language', value: 'ES' }

  const language = settingFor(document, 'general.language')
  expect(language).toBe('ES')
  assertType<SupportedLanguage>(language)
})

test('return default if it exists and not explicit setting', () => {
  const document: SettingsDocument<UsedSettings> = {}

  const language = settingFor(document, 'general.language')
  expect(language).toBe('EN')
})

test('values without a default can be undefined', () => {
  const document: SettingsDocument<UsedSettings> = {}
  document['editor.auto-save'] = { type: 'editor.auto-save', value: { value: true, interval: 500 } }
  const autoSave = settingFor(document, 'editor.auto-save')
  assertType<Maybe<{
    value: boolean;
    interval: number;
  }>>(autoSave)

  expect(autoSave?.value).toEqual(true)
  expect(autoSave?.interval).toEqual(500)
})

test('values without a default can be undefined', () => {
  const document: SettingsDocument<UsedSettings> = {}
  const setting = settingFor(document, 'editor.auto-save')
  expect(setting).toBe(undefined)
})

test('quick and dirty', () => {
  const settings: SettingsDocument<UsedSettings> = {}

  const theme = settingFor(settings, "ui.theme")
  assertType<Maybe<SupportedTheme>>(theme)
  const mode = settingFor(settings, "ui.mode")
  assertType<SupportedMode>(mode)
})
