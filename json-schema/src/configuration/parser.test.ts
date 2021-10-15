import { SettingsDocument } from '../generated/settings'
import { SettingsDto } from './dto'
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
  const typesOfKnowSettings: (keyof SettingsDocument) [] = ['editor.auto-save', 'general.language', 'ui.mode', 'ui.theme']
  const settingsDocument = extractSettings(receivedJson, typesOfKnowSettings)

  expect(settingsDocument['editor.auto-save']).toStrictEqual({
    type: 'editor.auto-save',
    value: {
      value: false,
      interval: 45,
    },
  })
  expect(settingsDocument['ui.mode']).toStrictEqual({
    type: 'ui.mode',
    value: 'dark',
  })
  expect(Object.keys(settingsDocument)).toStrictEqual(['editor.auto-save', 'ui.mode'])
})

const sendOverTheWire = (serialized: string) => serialized



