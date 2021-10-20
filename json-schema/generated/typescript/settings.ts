/**
 * The language used in the user interface
 */
export type SupportedLanguage = 'EO' | 'EN' | 'ZH' | 'ES';
/**
 * Setting with boolean default
 */
export type WithDefaultBoolean = boolean;
/**
 * Setting with boolean default
 */
export type WithDefaultNumber = number;
/**
 * Setting with boolean default
 */
export type WithDefaultString = string;
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
export interface SettingsDocument {
  editor?: EditorCategory;
  general?: GeneralCategory;
  testing?: TestingCategory;
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
export interface TestingCategory {
  'with-default-boolean'?: WithDefaultBoolean;
  'with-default-number'?: WithDefaultNumber;
  'with-default-object'?: WithDefaultObject;
  'with-default-string'?: WithDefaultString;
  [k: string]: unknown;
}
/**
 * Setting with boolean default
 */
export interface WithDefaultObject {
  value: string;
}
export interface UiCategory {
  mode?: Mode;
  theme?: Theme;
  [k: string]: unknown;
}
/**
 * Support type for TypeScript to get a mapping from setting type to the value type of each setting
 *
 * This interface was referenced by `SettingsDocument`'s JSON-Schema
 * via the `definition` "settings".
 */
export interface Settings {
  'editor.auto-save': AutoSave;
  'general.language': SupportedLanguage;
  'testing.with-default-boolean': WithDefaultBoolean;
  'testing.with-default-number': WithDefaultNumber;
  'testing.with-default-object': WithDefaultObject;
  'testing.with-default-string': WithDefaultString;
  'ui.mode': Mode;
  'ui.theme': Theme;
}

export type SettingTypeWithDefault = 'general.language' | 'testing.with-default-boolean' | 'testing.with-default-number' | 'testing.with-default-object' | 'testing.with-default-string' | 'ui.mode'
export type SettingType = keyof Settings

type Defaults = {
  [Property in SettingType as Extract<Property, SettingTypeWithDefault>]: Settings[Property]
}

export const defaults: Defaults = {
  'general.language': 'EN',
  'testing.with-default-boolean': true,
  'testing.with-default-number': 42,
  'testing.with-default-object': {"value":"default in an object"},
  'testing.with-default-string': 'Hello World',
  'ui.mode': 'dark'
}

export type SettingValueTypeLookup = {
  [type in SettingType]: Settings[type] | (type extends keyof Defaults ? never : undefined)
}
export const settingsUsedInClientOne = ['editor.auto-save', 'general.language', 'ui.mode', 'ui.theme'] as const
export const settingsUsedInClientTest = ['testing.with-default-boolean', 'testing.with-default-number', 'testing.with-default-object', 'testing.with-default-string'] as const
export const settingsUsedInClientTwo = ['ui.mode', 'ui.theme'] as const
