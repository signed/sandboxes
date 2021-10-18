import { assertType, Maybe } from '../asserts'
import { SupportedLanguage } from '../generated/settings'
import { Configuration, DictionaryBackedConfiguration } from './configuration'
import { SettingsDictionary } from './configuration'

type UsedSettings = 'general.language' | 'editor.auto-save'

test('return the value from the settings', () => {
  const dictionary: SettingsDictionary<UsedSettings> = {}
  dictionary['general.language'] = 'ES'
  const configuration: Configuration<UsedSettings> = new DictionaryBackedConfiguration(dictionary)
  const language = configuration.settingFor('general.language')
  expect(language).toBe('ES')
  assertType<SupportedLanguage>(language)
})

test('return default if it exists and not explicit setting', () => {
  const dictionary: SettingsDictionary<UsedSettings> = {}
  const configuration: Configuration<UsedSettings> = new DictionaryBackedConfiguration(dictionary)

  const language = configuration.settingFor('general.language')
  expect(language).toBe('EN')
})

test('values without a default can be undefined', () => {
  const dictionary: SettingsDictionary<UsedSettings> = {}
  dictionary['editor.auto-save'] = { value: true, interval: 500 }
  const configuration: Configuration<UsedSettings> = new DictionaryBackedConfiguration(dictionary)

  const autoSave = configuration.settingFor('editor.auto-save')
  assertType<
    Maybe<{
      value: boolean
      interval: number
    }>
  >(autoSave)

  expect(autoSave?.value).toEqual(true)
  expect(autoSave?.interval).toEqual(500)
})

test('values without a default can be undefined', () => {
  const dictionary: SettingsDictionary<UsedSettings> = {}
  const configuration: Configuration<UsedSettings> = new DictionaryBackedConfiguration(dictionary)
  const setting = configuration.settingFor('editor.auto-save')
  expect(setting).toBe(undefined)
})

test('only allow quering for UsedSettings', () => {
  const dictionary: SettingsDictionary<UsedSettings> = {}
  const configuration: Configuration<UsedSettings> = new DictionaryBackedConfiguration(dictionary)

  // @ts-expect-error trying to get a setting without a default that is not in UsedSettings results in type error
  configuration.settingFor('ui.theme')

  // @ts-expect-error trying to get a setting with a default that is not in UsedSettings results in type error
  configuration.settingFor('ui.mode')
})
