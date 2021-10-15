import { Settings, SettingsDocument } from '../generated/settings'

const typesInOne = ['editor.auto-save', 'general.language', 'ui.mode', 'ui.theme'] as const
export const oneTypesOfKnowSettings: (keyof SettingsDocument) [] = typesInOne.map(type => type)
type Bongo = (typeof typesInOne)[number]
export type SettingsDocumentOne = {
  [prop in Bongo]?: Settings[prop]
}
