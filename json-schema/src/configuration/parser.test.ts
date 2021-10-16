import { AutoSave, Language, Mode, Theme } from '../generated/settings'
import { SettingsDto } from './dto'
import { typesInOne } from './one'
import { extractSettings } from './parser'


test('extract known settings and ignore unknown settings', () => {
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
  const receivedJson = JSON.parse(received)
  const settingsDocument = extractSettings(receivedJson, typesInOne)

  expect(assertType<MayBe<AutoSave>>(settingsDocument['editor.auto-save'])).toStrictEqual({
    type: 'editor.auto-save',
    value: {
      value: false,
      interval: 45,
    },
  })
  expect(assertType<MayBe<Mode>>(settingsDocument['ui.mode'])).toStrictEqual({
    type: 'ui.mode',
    value: 'dark',
  })
  expect(Object.keys(settingsDocument)).toStrictEqual(['editor.auto-save', 'ui.mode'])
  assertType<MayBe<Theme>>(settingsDocument['ui.theme'])
  assertType<MayBe<Language>>(settingsDocument['general.language'])
})

const sendOverTheWire = (serialized: string) => serialized

type MayBe<T> = T | undefined

const assertType = <T>(x: T) => x
