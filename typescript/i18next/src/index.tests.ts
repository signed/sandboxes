import {expect, test} from 'vitest'
import {createInstance, type ResourceKey} from 'i18next';


let translation: ResourceKey = {};

test('basic lookup', async () => {
  translation = {
    "key": "hello world"
  }
  expect(await translate('key')).toEqual('hello world')
});

async function translate(key: string) {
  const i18n = createInstance({debug: false});
  const t = await i18n.init({
    lng: 'en',
    debug: false,
    resources: {
      en: {
        translation: translation
      }
    }
  });
  return t(key);
}



