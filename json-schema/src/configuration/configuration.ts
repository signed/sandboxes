import { defaults, Setting, Settings, SettingTypeWithDefault } from '../generated/settings'

export function settingFor<T extends SettingTypeWithDefault>(id: T, settings: Setting[]): Settings[T]
export function settingFor<T extends keyof Settings>(
  id: T,
  settings: Setting[],
): Settings[T]['value'] | undefined
export function settingFor<T extends keyof Settings>(id: T, settings: Setting[]) {
  const found = settings.find((setting) => setting.type === id)
  if (found !== undefined) {
    return found.value
  }
  return defaults[id as SettingTypeWithDefault]
}
