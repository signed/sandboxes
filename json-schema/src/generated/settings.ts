export type SupportedLanguage = "EO" | "EN" | "ZH" | "ES";
export type SupportedMode = "dark" | "light";
export type SupportedTheme = "Specular" | "Amos" | "Folie";

/**
 * All settings supported by the application
 */
export interface SettingsDocument {
  "editor.auto-save"?: AutoSave;
  "general.language"?: Language;
  "ui.mode"?: Mode;
  "ui.theme"?: Theme;
}
/**
 * Automatically save changes
 */
export interface AutoSave {
  type: "editor.auto-save";
  value: {
    value: boolean;
    interval: number;
  };
}
/**
 * The language used in the user interface
 */
export interface Language {
  type: "general.language";
  value: SupportedLanguage;
}
/**
 * The current ui mode
 */
export interface Mode {
  type: "ui.mode";
  value: SupportedMode;
}
/**
 * The current theme
 */
export interface Theme {
  type: "ui.theme";
  value: SupportedTheme;
}

export type Setting = AutoSave | Language | Mode | Theme
export type SettingTypeWithDefault = 'general.language' | 'ui.mode'

export type SettingType = Setting['type']
export type Settings = Required<SettingsDocument>
export type Defaults = {
  [Property in keyof Settings as Extract<Property, SettingTypeWithDefault>]: Settings[Property]['value']
}
export const defaults: Defaults = {
  'general.language': 'EN',
  'ui.mode': 'dark'
}

// 📋  not yet generated
export type SettingValueTypeLookup = {
  [type in keyof Settings]: Settings[type]['value'] | (type extends keyof Defaults ? never : undefined)
}
