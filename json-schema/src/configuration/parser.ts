import { OneSettingsTypes, SettingsDocumentOne } from './one'

export const extractSettings = (receivedJson: any, types: OneSettingsTypes []): SettingsDocumentOne => {
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

const extractSettingFrom = (receivedJson: any, type: OneSettingsTypes) =>
  type.split('.').reduce((object, segment) => {
    return object?.[segment]
  }, receivedJson)
