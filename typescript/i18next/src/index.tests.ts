import {expect, test} from 'vitest'
import {createInstance, type ResourceKey} from 'i18next';


let translation: ResourceKey = {};

test('basic lookup', async () => {
  translation = {
    "key": "hello world"
  }
  expect((await translate())('key')).toEqual('hello world')
});

test('nested interpolation', async () => {
  translation = {
    "girlsAndBoys": "They have $t(girls, {\"count\": {{girls}} }) and $t(boys, {\"count\": {{boys}} })",
    "boys": "{{count}} boy",
    "boys_other": "{{count}} boys",
    "girls": "{{count}} girl",
    "girls_other": "{{count}} girls",
  }

  expect((await translate())('girlsAndBoys', {girls: 3, boys: 2})).toEqual('They have 3 girls and 2 boys')
});

const translate = async () => {
  const i18n = createInstance({debug: false});
  return await i18n.init({
    lng: 'en',
    debug: false,
    resources: {
      en: {
        translation: translation
      }
    }
  });
};



