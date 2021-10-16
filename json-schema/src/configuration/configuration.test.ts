import { assertType, Maybe } from '../asserts'
import { Defaults, defaults, Settings, SettingType, SettingTypeWithDefault, SupportedLanguage } from '../generated/settings'
import { settingFor } from './configuration'
import { SettingsDocument } from './parser'

type UsedSettings = 'general.language' | "editor.auto-save"

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

test('only allow quering for UsedSettings', () => {
  const settings: SettingsDocument<UsedSettings> = {}

  // @ts-expect-error trying to get a setting without a default that is not in UsedSettings results in type error
  settingFor(settings, 'ui.theme')

  // @ts-expect-error trying to get a setting with a default that is not in UsedSettings results in type error
  settingFor(settings, 'ui.mode')
})


type Used = 'general.language' | 'editor.auto-save'

test('asfasdf', () => {
  const settings: SettingsDocument<Used> = {}
  const configuration = new DocumentBackedConfiguration(settings)
  const autoSave = configuration.settingFor('editor.auto-save')
  const language = configuration.settingFor('general.language')
  // @ts-expect-error trying to get a setting with a default that is not in UsedSettings results in type error
  configuration.settingFor('ui.mode')
})

export class DocumentBackedConfiguration<UsedSetting extends SettingType> {
  constructor(private readonly settings: SettingsDocument<UsedSetting>) {
  }

  paralel<Identifier extends UsedSetting>(id: Identifier): Identifier extends keyof Defaults ? Settings[Identifier]['value'] : Settings[Identifier]['value'] | undefined{
    const found = this.settings[id]
    if (found !== undefined) {
      // did not find a way how to properly tyep this
      return found.value as any
    }
    return defaults[id as SettingTypeWithDefault]
  }

  settingFor<Identifier extends UsedSetting>(id: Identifier): Settings[Identifier]['value'] | undefined {
    const found = this.settings[id]
    if (found !== undefined) {
      return found.value
    }
    return defaults[id as SettingTypeWithDefault]
  }
}
