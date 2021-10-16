import { defaults, Settings, SettingType, SettingTypeWithDefault, SettingValueTypeLookup } from '../generated/settings'
import { SettingsDocument } from './parser'

export function settingFor<K extends SettingType, T extends Extract<K, SettingTypeWithDefault>>(settings: SettingsDocument<K>, id: T): Settings[T]['value']
export function settingFor<K extends SettingType, T extends Extract<K, SettingType>>(
  settings: SettingsDocument<K>,
  id: T,
): Settings[T]['value'] | undefined
export function settingFor<K extends SettingType, T extends Extract<K, SettingType>>(settings: SettingsDocument<K>, id: T) {
  const found = settings[id]
  if (found !== undefined) {
    return found.value
  }
  return defaults[id as SettingTypeWithDefault]
}

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
