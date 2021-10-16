import { defaults, Setting, Settings, SettingTypeWithDefault } from '../generated/settings'
import { SettingsDocument } from './parser'

export function settingFor<T extends SettingTypeWithDefault>(settings: Setting[], id: T): Settings[T]["value"]
export function settingFor<T extends keyof Settings>(
  settings: Setting[],
  id: T,
): Settings[T]["value"] | undefined
export function settingFor<T extends keyof Settings>(settings: Setting[], id: T) {
  const found = settings.find((setting) => setting.type === id)
  if (found !== undefined) {
    return found.value
  }
  return defaults[id as SettingTypeWithDefault]
}

type SettingType = keyof Settings

// idea: third override with a default
export function settingFor2<K extends SettingType, T extends Extract<K, SettingTypeWithDefault>>(settings: SettingsDocument<K>, id: T): Settings[T]['value']
export function settingFor2<K extends SettingType, T extends Extract<K,SettingType>>(
  settings: SettingsDocument<K>,
  id: T,
): Settings[T]["value"] | undefined
export function settingFor2<K extends SettingType, T extends K>(settings: SettingsDocument<K>, id: T) {
  const found = settings[id]
  if (found !== undefined) {
    return found.value
  }
  return defaults[id as SettingTypeWithDefault]
}
