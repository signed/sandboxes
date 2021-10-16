import { assertType, Maybe } from './asserts'
import { usedSettings as settingsUsedInClientOne } from './configuration/client-one'
import { Configuration, DocumentBackedConfiguration } from './configuration/configuration'
import { SettingsDto } from './configuration/dto'
import { extractSettings } from './configuration/parser'
import { SupportedLanguage, SupportedMode, SupportedTheme } from './generated/settings'

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

type ClientOneSettings = (typeof settingsUsedInClientOne)[number]

test('extract known settings and ignore unknown settings', () => {
  const receivedJson = roundTripJson()
  const document = extractSettings(receivedJson, settingsUsedInClientOne)
  const configuration: Configuration<ClientOneSettings> = new DocumentBackedConfiguration(document)
  const autoSave = configuration.settingFor('editor.auto-save')
  assertType<Maybe<{
    value: boolean;
    interval: number;
  }>>(autoSave)
  expect(autoSave).toStrictEqual({
    value: false,
    interval: 45,
  })
  const language = configuration.settingFor('general.language')
  assertType<SupportedLanguage>(language)
  expect(language).toBe('EN')

  const mode = configuration.settingFor('ui.mode')
  assertType<SupportedMode>(mode)
  expect(mode).toBe('dark')

  const theme = configuration.settingFor('ui.theme')
  assertType<Maybe<SupportedTheme>>(theme)
  expect(theme).toBe(undefined)
})

export const settingsUsedInClientTwo = ['ui.mode', 'ui.theme'] as const
type ClientTwoSettings = (typeof settingsUsedInClientTwo)[number]

test('not all settings', () => {
  const receivedJson = roundTripJson()
  const document = extractSettings(receivedJson, settingsUsedInClientTwo)
  const configuration: Configuration<ClientTwoSettings> = new DocumentBackedConfiguration(document)

  const mode = configuration.settingFor('ui.mode')
  assertType<SupportedMode>(mode)
  expect(mode).toBe('dark')

  const theme = configuration.settingFor('ui.theme')
  assertType<Maybe<SupportedTheme>>(theme)
  expect(theme).toBe(undefined)

  // @ts-expect-error types prohibite access to
  configuration.settingFor('general.language')
  // @ts-expect-error types prohibite access to
  configuration.settingFor('editor.auto-save')
})

const roundTripJson = () => {
  const serialized = JSON.stringify(backendGenerated)
  const received = sendOverTheWire(serialized)
  return JSON.parse(received)
}

const sendOverTheWire = (serialized: string) => serialized

