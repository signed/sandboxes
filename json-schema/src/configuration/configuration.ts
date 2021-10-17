import {
  defaults,
  Settings,
  SettingsDocument,
  SettingType,
  SettingTypeWithDefault,
  SettingValueTypeLookup,
} from '../generated/settings'

export interface Configuration<U extends SettingType> {
  settingFor<Type extends U>(type: Type): SettingValueTypeLookup[Type]
}

export const extractConfigurationFrom = <T extends SettingType>(
  receivedJson: SettingsDocument,
  types: readonly T[],
): Configuration<T> => {
  const document: SettingsDictionary<T> = extractSettings(receivedJson, types)
  return new DictionaryBackedConfiguration(document)
}

export class DictionaryBackedConfiguration<U extends SettingType> implements Configuration<U> {
  constructor(private readonly settings: SettingsDictionary<U>) {}

  settingFor<Type extends U>(type: Type): SettingValueTypeLookup[Type] {
    const found = this.settings[type]
    if (found !== undefined) {
      return found as SettingValueTypeLookup[Type]
    }
    return defaults[type as SettingTypeWithDefault] as SettingValueTypeLookup[Type]
  }
}

export const extractSettings = <T extends SettingType>(
  receivedJson: SettingsDocument,
  types: readonly T[],
): SettingsDictionary<T> => {
  return types.reduce((document: SettingsDictionary<T>, type) => {
    const value = extractSettingFrom(receivedJson, type)
    if (value !== undefined) {
      document[type] = value
    }
    return document
  }, {})
}

const extractSettingFrom = <T extends SettingType>(receivedJson: any, type: T) =>
  type.split('.').reduce((object, segment) => {
    return object?.[segment]
  }, receivedJson)

export type SettingsDictionary<T extends SettingType> = {
  [prop in T]?: Settings[prop]
}
