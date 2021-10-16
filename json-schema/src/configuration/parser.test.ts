import { assertType, Maybe } from '../asserts'
import { AutoSave, Language, Mode, Theme } from '../generated/settings'
import { SettingsDto } from './dto'
import { usedSettings } from './client-one'
import { extractSettings } from './parser'

test('extract known settings and ignore unknown settings', () => {
  const receivedJson = roundTripJson()
  const settingsDocument = extractSettings(receivedJson, usedSettings)

  expect(assertType<Maybe<AutoSave>>(settingsDocument['editor.auto-save'])).toStrictEqual({
    type: 'editor.auto-save',
    value: {
      value: false,
      interval: 45,
    },
  })
  expect(assertType<Maybe<Mode>>(settingsDocument['ui.mode'])).toStrictEqual({
    type: 'ui.mode',
    value: 'dark',
  })
  expect(Object.keys(settingsDocument)).toStrictEqual(['editor.auto-save', 'ui.mode'])
  assertType<Maybe<Theme>>(settingsDocument['ui.theme'])
  assertType<Maybe<Language>>(settingsDocument['general.language'])
})

export const typesInTwo = ['ui.mode', 'ui.theme'] as const
test('not all settings', () => {
  const receivedJson = roundTripJson()
  const settingsDocument = extractSettings(receivedJson, typesInTwo)

  expect(assertType<Maybe<Mode>>(settingsDocument['ui.mode'])).toStrictEqual({
    type: 'ui.mode',
    value: 'dark',
  })
  expect(assertType<Maybe<Theme>>(settingsDocument['ui.theme'])).toBe(undefined)
  expect(Object.keys(settingsDocument)).toStrictEqual(['ui.mode'])
})

const roundTripJson = () => {
  const backendGenerated: SettingsDto = {
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
      'category': {
        unknown: 'not know should be ignored',
      },
    },
  }
  const serialized = JSON.stringify(backendGenerated)
  const received = sendOverTheWire(serialized)
  return JSON.parse(received)
}

const sendOverTheWire = (serialized: string) => serialized

