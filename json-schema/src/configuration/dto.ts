import { SupportedLanguage, SupportedMode, SupportedTheme } from '../generated/settings'

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
