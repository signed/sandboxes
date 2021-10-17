/**
 * The language used in the user interface
 */
export type SupportedLanguage = 'EO' | 'EN' | 'ZH' | 'ES';
/**
 * The current ui mode
 */
export type Mode = 'dark' | 'light';
/**
 * The current theme
 */
export type Theme = 'Specular' | 'Amos' | 'Folie';

/**
 * The wire format of the settings
 */
export interface SettingsDto {
  editor?: EditorCategory;
  general?: GeneralCategory;
  ui?: UiCategory;
  [k: string]: unknown;
}
export interface EditorCategory {
  'auto-save'?: AutoSave;
  [k: string]: unknown;
}
/**
 * Automatically save changes
 */
export interface AutoSave {
  value: boolean;
  interval: number;
}
export interface GeneralCategory {
  language?: SupportedLanguage;
  [k: string]: unknown;
}
export interface UiCategory {
  mode?: Mode;
  theme?: Theme;
  [k: string]: unknown;
}
/**
 * Support type for TypeScript to get a mapping from setting type to the value type of each setting
 *
 * This interface was referenced by `SettingsDto`'s JSON-Schema
 * via the `definition` "settings".
 */
export interface Settings {
  'editor.auto-save': AutoSave;
  'general.language': SupportedLanguage;
  'ui.mode': Mode;
  'ui.theme': Theme;
}

export type SettingTypeWithDefault = 'general.language' | 'ui.mode'
export type SettingType = keyof Settings

type Defaults = {
  [Property in SettingType as Extract<Property, SettingTypeWithDefault>]: Settings[Property]
}

export const defaults: Defaults = {
  'general.language': 'EN',
  'ui.mode': 'dark'
}

export type SettingValueTypeLookup = {
  [type in SettingType]: Settings[type] | (type extends keyof Defaults ? never : undefined)
}
//TODO create from meta data in the schema
export const settingsUsedInClientOne = ['editor.auto-save', 'general.language', 'ui.mode', 'ui.theme'] as const
export const settingsUsedInClientTwo = ['ui.mode', 'ui.theme'] as const
