import $RefParser from '@apidevtools/json-schema-ref-parser'
import { writeFileSync } from 'fs'
import { JSONSchema7Type, JSONSchema7TypeName } from 'json-schema'
import { compile, Options } from 'json-schema-to-typescript'
import { extname } from 'path'
import { absolutPathToSettingsJson, pathToSettingsTs, readSchema } from './shared'

export const generateTypesFromSchema = async () => {
  const schema = readSchema(absolutPathToSettingsJson)

  const parser = new $RefParser()
  const refs = await parser.resolve(schema)
  const settingTypeWithDefaultTypeCode = settingWithDefaultType(refs)
  const defaultsTypeCode = defaultsType(refs)
  const clientsTypeCode = clientsType()
  const handCraftedCode = handCrafted()

  const options: Partial<Options> = {
    cwd: process.cwd(),
    bannerComment: '',
    unreachableDefinitions: true,
    style: { singleQuote: true },
  }
  const settingsCode = await compile(schema, stripExtension(absolutPathToSettingsJson), options)

  const snippets = [settingsCode, settingTypeWithDefaultTypeCode, defaultsTypeCode, clientsTypeCode, handCraftedCode]
  const code = snippets.join('\n')
  writeFileSync(pathToSettingsTs, code)
}

const stripExtension = (filename: string): string => filename.replace(extname(filename), '')

const settingsBase = '#/definitions/settings'

const pathToDefaultValueFor = (type: string) => `${settingsBase}/properties/${type}/default`
const pathToDefaultTypeFor = (type: string) => `${settingsBase}/properties/${type}/type`

const settingWithDefaultType = (refs: $RefParser.$Refs) => {
  const o = refs.get(`${settingsBase}/properties`)
  if (o === null) {
    throw new Error('Settings has no properties, that should not happen')
  }
  const types = Object.keys(o)
  const keysWithDefault = types.filter((type) => refs.exists(pathToDefaultValueFor(type)))
  const union = keysWithDefault.map((type) => `'${type}'`).join(' | ')
  return `export type SettingTypeWithDefault = ${union}`
}

const typesWithoutValueEscape = ['boolean', 'number']
const createDefaultValue = (defaultt: { type: JSONSchema7TypeName; value: JSONSchema7Type }) => {
  if (typesWithoutValueEscape.includes(defaultt.type)) {
    return `${defaultt.value}`
  }
  return `'${defaultt.value}'`
}

const defaultsType = (refs: $RefParser.$Refs) => {
  const o = refs.get(`${settingsBase}/properties`)
  if (o === null) {
    throw new Error('Settings has no properties, that should not happen')
  }
  const types = Object.keys(o)
  const keysWithDefault = types.filter((type) => refs.exists(pathToDefaultValueFor(type)))
  const union = keysWithDefault.map((type) => {
    const defaultt = {
      type: refs.get(pathToDefaultTypeFor(type)) as JSONSchema7TypeName,
      value: refs.get(pathToDefaultValueFor(type)),
    }
    return { type, defaultt }
  })
  return `
export type SettingType = keyof Settings

type Defaults = {
  [Property in SettingType as Extract<Property, SettingTypeWithDefault>]: Settings[Property]
}

export const defaults: Defaults = {
  ${union.map(({ type, defaultt }) => `'${type}': ${createDefaultValue(defaultt)}`).join(',\n  ')}
}

export type SettingValueTypeLookup = {
  [type in SettingType]: Settings[type] | (type extends keyof Defaults ? never : undefined)
}
`.trim()
}

const clientsType = () =>
  `
//TODO create from meta data in the schema
export const settingsUsedInClientOne = ['editor.auto-save', 'general.language', 'ui.mode', 'ui.theme'] as const
export const settingsUsedInClientTwo = ['ui.mode', 'ui.theme'] as const
export const settingsUsedInTestClient = ['testing.with-default-boolean', 'testing.with-default-number', 'testing.with-default-string'] as const
`.trim()
const handCrafted = () => ``

generateTypesFromSchema().catch((e: unknown) => console.error(e))
