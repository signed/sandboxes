import { defaults, Settings, SettingsDto, SettingType, SettingTypeWithDefault, SettingValueTypeLookup } from '../generated/settings'

export interface Configuration<U extends SettingType> {
  settingFor<Type extends U>(type: Type): SettingValueTypeLookup[Type]
}

export class DocumentBackedConfiguration<U extends SettingType> implements Configuration<U> {
  constructor(private readonly settings: SettingsDocument<U>) {
  }

  settingFor<Type extends U>(type: Type): SettingValueTypeLookup[Type] {
    const found = this.settings[type]
    if (found !== undefined) {
      return found.value as SettingValueTypeLookup[Type]
    }
    return defaults[type as SettingTypeWithDefault] as SettingValueTypeLookup[Type]
  }
}

export const extractSettings = <T extends SettingType>(receivedJson: SettingsDto, types: readonly T[]): SettingsDocument<T> => {
  return types.reduce((document: SettingsDocument<T>, type) => {
    const value = extractSettingFrom(receivedJson, type)
    if (value !== undefined) {
      document[type] = {
        type, value,
      } as any
    }
    return document
  }, {})
}

const extractSettingFrom = <T extends SettingType>(receivedJson: any, type: T) =>
  type.split('.').reduce((object, segment) => {
    return object?.[segment]
  }, receivedJson)


export type SettingsDocument<T extends SettingType> = {
  [prop in T]?: Settings[prop]
}
