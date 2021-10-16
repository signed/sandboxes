export type SupportedLanguage = 'EO' | 'EN' | 'ZH' | 'ES';
export type SupportedMode = 'dark' | 'light';
export type SupportedTheme = 'Specular' | 'Amos' | 'Folie';

/**
 * The wire format of the settings
 */
export interface SettingsDtoWip {
  editor?: Editor;
  general?: General;
  ui?: Ui;
  [k: string]: unknown;
}
export interface Editor {
  hello?: 'string';
  [k: string]: unknown;
}
export interface General {
  hello?: 'string';
  [k: string]: unknown;
}
export interface Ui {
  hello?: 'string';
  [k: string]: unknown;
}
/**
 * All settings supported by the application
 *
 * This interface was referenced by `SettingsDtoWip`'s JSON-Schema
 * via the `definition` "settings".
 */
export interface SettingsDocument {
  'editor.auto-save'?: AutoSave;
  'general.language'?: Language;
  'ui.mode'?: Mode;
  'ui.theme'?: Theme;
}
/**
 * Automatically save changes
 */
export interface AutoSave {
  type: 'editor.auto-save';
  value: {
    value: boolean;
    interval: number;
  };
}
/**
 * The language used in the user interface
 */
export interface Language {
  type: 'general.language';
  value: SupportedLanguage;
}
/**
 * The current ui mode
 */
export interface Mode {
  type: 'ui.mode';
  value: SupportedMode;
}
/**
 * The current theme
 */
export interface Theme {
  type: 'ui.theme';
  value: SupportedTheme;
}

export type Setting = AutoSave | Language | Mode | Theme
export type SettingTypeWithDefault = 'general.language' | 'ui.mode'

export type SettingType = Setting['type']

export type Settings = Required<SettingsDocument>

type Defaults = {
  [Property in SettingType as Extract<Property, SettingTypeWithDefault>]: Settings[Property]['value']
}

export const defaults: Defaults = {
  'general.language': 'EN',
  'ui.mode': 'dark'
}

export type SettingValueTypeLookup = {
  [type in SettingType]: Settings[type]['value'] | (type extends keyof Defaults ? never : undefined)
}


//TODO create from meta data in the schema
export const settingsUsedInClientOne = ['editor.auto-save', 'general.language', 'ui.mode', 'ui.theme'] as const
export const settingsUsedInClientTwo = ['ui.mode', 'ui.theme'] as const

// 📋  not yet generated
export interface SettingsDto {
  'editor'?: {
    'auto-save'?: {
      value: boolean;
      interval: number;
    },
    [segment: string]: unknown
  },
  'general'?: {
    language: SupportedLanguage
    [segment: string]: unknown
  }
  'ui'?: {
    'mode'?: SupportedMode
    'theme'?: SupportedTheme
    [segment: string]: unknown
  },

  [segment: string]: unknown
}
