import { Settings } from '../generated/settings'

export type SettingsDocument<T extends keyof Settings> = {
  [prop in T]?: Settings[prop]
}

export const extractSettings = <T extends keyof Settings>(receivedJson: any, types: readonly T[]): SettingsDocument<T> => {
  return types.reduce((document: SettingsDocument<T>, type) => {
    const value = extractSettingFrom(receivedJson, type)
    if (value !== undefined) {
      document[type] = {
        type, value,
      } as any
    }
    return document
  }, {})
}

const extractSettingFrom = <T extends keyof Settings>(receivedJson: any, type: T) =>
  type.split('.').reduce((object, segment) => {
    return object?.[segment]
  }, receivedJson)
