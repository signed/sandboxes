import { defaults, Setting, Settings, SettingTypeWithDefault } from '../generated/settings'
import { SettingsDocument } from './parser'

type SettingType = Setting['type']

// idea: third override with a default
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
