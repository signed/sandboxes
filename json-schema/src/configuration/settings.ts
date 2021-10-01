import { AutoSave } from './settings/auto-save'
import { Language } from './settings/language'
import { Mode } from './settings/mode'
import { Theme } from './settings/theme'

// Is generated
export type Setting = Theme | Mode | Language | AutoSave

// Is generated
export type Settings = {
  'ui.theme': Theme
  'ui.mode': Mode
  'general.language': Language
  'editor.auto-save': AutoSave
}

// Is generated
export type SettingTypeWithDefault =  'ui.mode' | 'general.language'

export type Defaults = {
  [Property in keyof Settings as Extract<Property, SettingTypeWithDefault>]: Settings[Property]['value']
}

// Is generated
export const defaults: Defaults = {
  'ui.mode': 'dark',
  'general.language': 'EN',
}
