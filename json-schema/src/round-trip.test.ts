import { assertType, Maybe } from './asserts'
import { extractConfigurationFrom } from './configuration/configuration'
import {
  Mode,
  SettingsDocument,
  settingsUsedInClientOne,
  settingsUsedInClientTwo,
  settingsUsedInTestClient,
  SupportedLanguage,
  Theme,
} from './generated/settings'

const backendGenerated: SettingsDocument = {
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
    category: {
      unknown: 'not know should be ignored',
    },
  },
}

test('extract known settings and ignore unknown settings', () => {
  const configuration = extractConfigurationFrom(roundTripJson(), settingsUsedInClientOne)
  const autoSave = configuration.settingFor('editor.auto-save')
  assertType<
    Maybe<{
      value: boolean
      interval: number
    }>
  >(autoSave)
  expect(autoSave).toStrictEqual({
    value: false,
    interval: 45,
  })
  const language = configuration.settingFor('general.language')
  assertType<SupportedLanguage>(language)
  expect(language).toBe('EN')

  const mode = configuration.settingFor('ui.mode')
  assertType<Mode>(mode)
  expect(mode).toBe('dark')

  const theme = configuration.settingFor('ui.theme')
  assertType<Maybe<Theme>>(theme)
  expect(theme).toBe(undefined)
})

test('not all settings', () => {
  const configuration = extractConfigurationFrom(roundTripJson(), settingsUsedInClientTwo)

  const mode = configuration.settingFor('ui.mode')
  assertType<Mode>(mode)
  expect(mode).toBe('dark')

  const theme = configuration.settingFor('ui.theme')
  assertType<Maybe<Theme>>(theme)
  expect(theme).toBe(undefined)

  // @ts-expect-error types prohibite access to
  configuration.settingFor('general.language')
  // @ts-expect-error types prohibite access to
  configuration.settingFor('editor.auto-save')
})

const roundTripJson = () => {
  const serialized = JSON.stringify(backendGenerated)
  return sendOverTheWire(serialized)
}

const sendOverTheWire = (serialized: string) => serialized

const emptySettingsDocument = '{}'
describe('use default for', () => {
  const configuration = extractConfigurationFrom(emptySettingsDocument, settingsUsedInTestClient)

  test('use default for boolean values', () => {
    expect(configuration.settingFor('testing.with-default-boolean')).toBe(true)
  })
  test('use default for number values', () => {
    expect(configuration.settingFor('testing.with-default-number')).toBe(42)
  })
  test('use default for string values', () => {
    expect(configuration.settingFor('testing.with-default-string')).toBe('Hello World')
  })
  test('use default for object values', () => {
    expect(configuration.settingFor('testing.with-default-object')).toStrictEqual({
      value: 'default in an object',
    })
  })
})
