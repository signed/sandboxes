import { Settings, SettingsDocument } from '../generated/settings'

const typesInOne = ['editor.auto-save', 'general.language', 'ui.mode', 'ui.theme'] as const
export const oneTypesOfKnowSettings: (keyof SettingsDocument) [] = typesInOne.map(type => type)
export type OneSettingsTypes = (typeof typesInOne)[number]

type SettingsDocument2<T extends keyof Settings> = {
  [prop in T]?: Settings[prop]
}

export type SettingsDocumentOne = SettingsDocument2<OneSettingsTypes>
