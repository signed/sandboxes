import {
  defaults,
  Settings,
  SettingsDocument,
  SettingType,
  SettingTypeWithDefault,
  SettingValueTypeLookup,
} from '../../generated/typescript/settings'

export interface Configuration<U extends SettingType> {
  settingFor<Type extends U>(type: Type): SettingValueTypeLookup[Type]
}

export const extractConfigurationFrom = <T extends SettingType>(
  receivedJson: string,
  types: readonly T[],
): Configuration<T> => {
  const settingsDocument = JSON.parse(receivedJson) as SettingsDocument
  const dictionary: SettingsDictionary<T> = extractSettings(settingsDocument, types)
  return new DictionaryBackedConfiguration(dictionary)
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
  return types.reduce((dictionary: SettingsDictionary<T>, type) => {
    const value = extractSettingFrom(receivedJson, type)
    if (value !== undefined) {
      dictionary[type] = value
    }
    return dictionary
  }, {})
}

const extractSettingFrom = <T extends SettingType>(receivedJson: any, type: T) =>
  type.split('.').reduce((object, segment) => {
    return object?.[segment]
  }, receivedJson)

export type SettingsDictionary<T extends SettingType> = {
  [prop in T]?: Settings[prop]
}
