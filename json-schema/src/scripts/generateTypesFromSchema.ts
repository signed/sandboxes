import $RefParser from '@apidevtools/json-schema-ref-parser'
import { writeFileSync } from 'fs'
import { compile, Options } from 'json-schema-to-typescript'
import { extname } from 'path'
import { readSchema } from './shared'


export async function generateTypesFromSchema() {
  const fileName = process.cwd() + '/src/schemas/settings.json'
  const schema = readSchema(fileName)

  const parser = new $RefParser()
  const wohoo = await parser.resolve(schema)
  const settingTypeWithDefaultTypeCode = settingWithDefaultType(wohoo)
  const defaultsTypeCode = defaultsType(wohoo)
  const clientsTypeCode = clientsType()
  const handCraftedCode = handCrafted()

  const options: Partial<Options> = {
    cwd: process.cwd(),
    bannerComment: '',
    unreachableDefinitions: true,
    style: { singleQuote: true },
  }
  const settingsCode = await compile(schema, stripExtension(fileName), options)

  const snippets = [
    settingsCode,
    settingTypeWithDefaultTypeCode,
    defaultsTypeCode,
    clientsTypeCode,
    handCraftedCode
  ]
  const code = snippets.join('\n')
  writeFileSync(process.cwd() + '/src/generated/settings.ts', code)
}

const stripExtension = (filename: string): string => filename.replace(extname(filename), '')

const settingsBase = '#/definitions/settings'

const pathToDefaultFor = (type: string) => `${settingsBase}/properties/${type}/default`

const settingWithDefaultType = function(wohoo: $RefParser.$Refs) {
  const o = wohoo.get(`${settingsBase}/properties`)
  if (o === null) {
    throw new Error('Settings has no properties, that should not happen')
  }
  const types = Object.keys(o)
  const keysWithDefault = types.filter(type => wohoo.exists(pathToDefaultFor(type)))
  const union = keysWithDefault.map(type => `'${type}'`).join(' | ')
  return `export type SettingTypeWithDefault = ${union}`
}

const defaultsType = function(wohoo: $RefParser.$Refs) {
  const o = wohoo.get(`${settingsBase}/properties`)
  if (o === null) {
    throw new Error('Settings has no properties, that should not happen')
  }
  const types = Object.keys(o)
  const keysWithDefault = types.filter(type => wohoo.exists(pathToDefaultFor(type)))
  const union = keysWithDefault.map(type => {
    const defaultt = wohoo.get(pathToDefaultFor(type))
    return { type, defaultt }
  })
  return `
export type SettingType = keyof SettingsDocument
export type Settings = Required<SettingsDocument>

type Defaults = {
  [Property in SettingType as Extract<Property, SettingTypeWithDefault>]: Settings[Property]
}

export const defaults: Defaults = {
  ${union.map(({ type, defaultt }) => `'${type}': '${defaultt}'`).join(',\n  ')}
}

export type SettingValueTypeLookup = {
  [type in SettingType]: Settings[type] | (type extends keyof Defaults ? never : undefined)
}
`
}

const clientsType = () => `
//TODO create from meta data in the schema
export const settingsUsedInClientOne = ['editor.auto-save', 'general.language', 'ui.mode', 'ui.theme'] as const
export const settingsUsedInClientTwo = ['ui.mode', 'ui.theme'] as const
`
const handCrafted = () => ``

generateTypesFromSchema().catch((e: unknown) => console.error(e))
