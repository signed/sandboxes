import { SettingsDocumentOne } from './one'

type KnowSetting = keyof SettingsDocumentOne

export const extractSettings = (receivedJson: any, types: KnowSetting []): SettingsDocumentOne => {
  return types.reduce((document: SettingsDocumentOne, type) => {
    const value = extractSettingFrom(receivedJson, type)
    if (value !== undefined) {
      document[type] = {
        type, value,
      } as any
    }
    return document
  }, {})
}

const extractSettingFrom = (receivedJson: any, type: KnowSetting) =>
  type.split('.').reduce((object, segment) => {
    return object?.[segment]
  }, receivedJson)
