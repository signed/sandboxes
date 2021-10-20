import { assertType, Maybe } from '../asserts'
import { AutoSave, SupportedLanguage, Mode, SettingsDocument, Theme } from '../../generated/typescript/settings'
import { extractSettings } from './configuration'

test('extract known settings and ignore unknown settings', () => {
  const backendGenerated: SettingsDocument = {
    editor: {
      'auto-save': {
        value: false,
        interval: 45,
      },
      unknown: 'not know should be ignored',
    },
    ui: {
      mode: 'dark',
      unknown: 'not know should be ignored',
    },
    unknown: {
      category: {
        unknown: 'not know should be ignored',
      },
    },
  }
  const receivedJson = sendOverTheWire(backendGenerated)
  const useAllSettings = ['editor.auto-save', 'general.language', 'ui.mode', 'ui.theme'] as const
  const settingsDocument = extractSettings(receivedJson, useAllSettings)

  expect(assertType<Maybe<AutoSave>>(settingsDocument['editor.auto-save'])).toStrictEqual({
    value: false,
    interval: 45,
  })
  expect(assertType<Maybe<Mode>>(settingsDocument['ui.mode'])).toStrictEqual('dark')
  expect(Object.keys(settingsDocument)).toStrictEqual(['editor.auto-save', 'ui.mode'])
  assertType<Maybe<Theme>>(settingsDocument['ui.theme'])
  assertType<Maybe<SupportedLanguage>>(settingsDocument['general.language'])
})

test('not all settings', () => {
  const backendGenerated: SettingsDocument = {
    editor: {
      'auto-save': {
        value: false,
        interval: 45,
      },
      unknown: 'not know should be ignored',
    },
    ui: {
      mode: 'dark',
      unknown: 'not know should be ignored',
    },
    unknown: {
      category: {
        unknown: 'not know should be ignored',
      },
    },
  }
  const receivedJson = sendOverTheWire(backendGenerated)
  const useSubsetOfSettings = ['ui.mode', 'ui.theme'] as const
  const settingsDocument = extractSettings(receivedJson, useSubsetOfSettings)

  expect(assertType<Maybe<Mode>>(settingsDocument['ui.mode'])).toStrictEqual('dark')
  expect(assertType<Maybe<Theme>>(settingsDocument['ui.theme'])).toBe(undefined)
  expect(Object.keys(settingsDocument)).toStrictEqual(['ui.mode'])
})

const sendOverTheWire = (backendGenerated: SettingsDocument) => {
  const serialized = JSON.stringify(backendGenerated)
  return JSON.parse(serialized)
}
