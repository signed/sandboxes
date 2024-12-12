import {expect, test} from 'vitest'

import {createInstance} from 'i18next';

test('basic lookup', () => {
  const i18n = createInstance({
    lng: 'en', // if you're using a language detector, do not define the lng option
    debug: true,
    resources: {
      en: {
        translation: {
          "key": "hello world"
        }
      }
    }
  });
  i18n.init()
  expect(i18n.t('key')).toEqual('hello world')
});


