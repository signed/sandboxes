// https://www.reddit.com/r/typescript/comments/d8w658/dynamic_property_lookup_via_type/
import { AutoSave } from './settings/auto-save'
import { Language } from './settings/language'
import { Mode } from './settings/mode'
import { Theme } from './settings/theme'

type Setting = Theme | Mode | Language | AutoSave

type Settings = {
  'ui.theme': Theme
  'ui.mode': Mode
  'general.language': Language
  'editor.auto-save': AutoSave
}

// TODO has to be generated
type SettingsWithDefault = 'general.language' | 'ui.mode'

// TODO has to be generated
const defaults: Defaults = {
  'general.language': 'EN',
  'ui.mode': 'dark',
}

type Defaults = {
  [Property in keyof Settings as Extract<Property, SettingsWithDefault>]: Settings[Property]['value']
}

// fake it till you make it
export function settingFor<T extends SettingsWithDefault>(id: T, settings: Setting[]): Settings[T]
export function settingFor<T extends keyof Settings>(
  id: T,
  settings: Setting[],
): Settings[T]['value'] | undefined
export function settingFor<T extends keyof Settings>(id: T, settings: Setting[]) {
  const found = settings.find((setting) => setting.type === id)
  if (found !== undefined) {
    return found.value
  }
  return defaults[id as SettingsWithDefault]
}
