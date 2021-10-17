import { assertType, Maybe } from '../asserts'
import { SupportedLanguage } from '../generated/settings'
import { Configuration, DocumentBackedConfiguration } from './configuration'
import { SettingsDocument } from './configuration'

type UsedSettings = 'general.language' | 'editor.auto-save'

test('return the value from the settings', () => {
  const document: SettingsDocument<UsedSettings> = {}
  document['general.language'] = 'ES'
  const configuration: Configuration<UsedSettings> = new DocumentBackedConfiguration(document)
  const language = configuration.settingFor('general.language')
  expect(language).toBe('ES')
  assertType<SupportedLanguage>(language)
})

test('return default if it exists and not explicit setting', () => {
  const document: SettingsDocument<UsedSettings> = {}
  const configuration: Configuration<UsedSettings> = new DocumentBackedConfiguration(document)

  const language = configuration.settingFor('general.language')
  expect(language).toBe('EN')
})

test('values without a default can be undefined', () => {
  const document: SettingsDocument<UsedSettings> = {}
  document['editor.auto-save'] = { value: true, interval: 500 }
  const configuration: Configuration<UsedSettings> = new DocumentBackedConfiguration(document)

  const autoSave = configuration.settingFor('editor.auto-save')
  assertType<Maybe<{
    value: boolean;
    interval: number;
  }>>(autoSave)

  expect(autoSave?.value).toEqual(true)
  expect(autoSave?.interval).toEqual(500)
})

test('values without a default can be undefined', () => {
  const document: SettingsDocument<UsedSettings> = {}
  const configuration: Configuration<UsedSettings> = new DocumentBackedConfiguration(document)
  const setting = configuration.settingFor('editor.auto-save')
  expect(setting).toBe(undefined)
})

test('only allow quering for UsedSettings', () => {
  const document: SettingsDocument<UsedSettings> = {}
  const configuration: Configuration<UsedSettings> = new DocumentBackedConfiguration(document)

  // @ts-expect-error trying to get a setting without a default that is not in UsedSettings results in type error
  configuration.settingFor('ui.theme')

  // @ts-expect-error trying to get a setting with a default that is not in UsedSettings results in type error
  configuration.settingFor('ui.mode')
})
