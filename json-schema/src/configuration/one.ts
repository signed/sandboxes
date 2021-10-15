import { AutoSave, Language, Mode, SettingsDocument, Theme } from '../generated/settings'

export const oneTypesOfKnowSettings: (keyof SettingsDocument) [] = ['editor.auto-save', 'general.language', 'ui.mode', 'ui.theme']


export interface SettingsDocumentOne {
  'editor.auto-save'?: AutoSave;
  'general.language'?: Language;
  'ui.mode'?: Mode;
  'ui.theme'?: Theme;
}
