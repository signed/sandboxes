import { assertType, Maybe } from './asserts'
import { usedSettings as settingsUsedInClientOne } from './configuration/client-one'
import { settingFor } from './configuration/configuration'
import { SettingsDto } from './configuration/dto'
import { extractSettings } from './configuration/parser'
import { Language, SupportedLanguage, SupportedMode, SupportedTheme, Theme } from './generated/settings'

const backendGenerated: SettingsDto = {
  editor: {
    'auto-save': {
      value: false,
      interval: 45,
    },
    unknown: 'not know should be ignored',
  },
  ui: {
    mode: 'dark',
    unknown: 'not know should be ignored',
  },
  unknown: {
    'category': {
      unknown: 'not know should be ignored',
    },
  },
}

test('extract known settings and ignore unknown settings', () => {
  const receivedJson = roundTripJson()
  const settingsDocument = extractSettings(receivedJson, settingsUsedInClientOne)

  assertType<Maybe<Theme>>(settingsDocument['ui.theme'])
  assertType<Maybe<Language>>(settingsDocument['general.language'])

  const autoSave = settingFor(settingsDocument, 'editor.auto-save')
  assertType<Maybe<{
    value: boolean;
    interval: number;
  }>>(autoSave)
  expect(autoSave).toStrictEqual({
    value: false,
    interval: 45,
  })
  const language = settingFor(settingsDocument, 'general.language')
  assertType<SupportedLanguage>(language)
  expect(language).toBe('EN')

  const mode = settingFor(settingsDocument, 'ui.mode')
  assertType<SupportedMode>(mode)
  expect(mode).toBe('dark')

  const theme = settingFor(settingsDocument, 'ui.theme')
  assertType<Maybe<SupportedTheme>>(theme)
  expect(theme).toBe(undefined)
})

export const settingsUsedInClientTwo = ['ui.mode', 'ui.theme'] as const

test('not all settings', () => {
  const receivedJson = roundTripJson()
  const settingsDocument = extractSettings(receivedJson, settingsUsedInClientTwo)

  const mode = settingFor(settingsDocument, 'ui.mode')
  assertType<SupportedMode>(mode)
  expect(mode).toBe('dark')

  const theme = settingFor(settingsDocument, 'ui.theme')
  assertType<Maybe<SupportedTheme>>(theme)
  expect(theme).toBe(undefined)

  // @ts-expect-error types prohibite access to
  settingFor(settingsDocument, 'general.language')
  // @ts-expect-error types prohibite access to
  settingFor(settingsDocument, 'editor.auto-save')
})

const roundTripJson = () => {
  const serialized = JSON.stringify(backendGenerated)
  const received = sendOverTheWire(serialized)
  return JSON.parse(received)
}

const sendOverTheWire = (serialized: string) => serialized

