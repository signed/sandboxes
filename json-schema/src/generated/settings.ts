export type SupportedTheme = "Specular" | "Amos" | "Folie";
export type SupportedMode = "dark" | "light";
export type SupportedLanguage = "EO" | "EN" | "ZH" | "ES";

/**
 * All settings supported by the application
 */
export interface Settings {
  "ui.theme": Theme;
  "ui.mode": Mode;
  "general.language": Language;
  "editor.auto-save": AutoSave;
}
/**
 * The current theme
 */
export interface Theme {
  type: "ui.theme";
  value: SupportedTheme;
}
/**
 * The current ui mode
 */
export interface Mode {
  type: "ui.mode";
  value: SupportedMode;
}
/**
 * The language used in the user interface
 */
export interface Language {
  type: "general.language";
  value: SupportedLanguage;
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

export type Setting = Theme | Mode | Language | AutoSave