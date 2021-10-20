import $RefParser from '@apidevtools/json-schema-ref-parser'
import { writeFileSync } from 'fs'
import { JSONSchema7Type, JSONSchema7TypeName } from 'json-schema'
import { compile, Options } from 'json-schema-to-typescript'
import { extname } from 'path'
import {
  absolutPathToSettingsJson,
  pathToSettingsTs,
  readAllSettingSchemas,
  readSchema,
  settingTypeFor,
} from './shared'

export const generateTypesFromSchema = async () => {
  const settingsSchema = readSchema(absolutPathToSettingsJson)

  const parser = new $RefParser()
  const refs = await parser.resolve(settingsSchema)
  const settingTypeWithDefaultTypeCode = settingWithDefaultType(refs)
  const defaultsTypeCode = defaultsType(refs)
  const clientsTypeCode = await clientsType()
  const handCraftedCode = handCrafted()

  const options: Partial<Options> = {
    cwd: process.cwd(),
    bannerComment: '',
    unreachableDefinitions: true,
    style: { singleQuote: true },
  }
  const settingsCode = await compile(settingsSchema, stripExtension(absolutPathToSettingsJson), options)

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
  if (defaultt.type === 'object') {
    return JSON.stringify(defaultt.value)
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

const clientsType = async () => {
  const parser = new $RefParser()

  const foundSchemas = readAllSettingSchemas()
  const clientsToUsedSettings = new Map<string, string[]>()
  for (const found of foundSchemas) {
    const settingType = settingTypeFor(found)
    const refs = await parser.resolve(readSchema(found.path))
    const consumers = refs.get('#/$meta/consumer') as string[]
    for (const consumer of consumers) {
      let usedSettings = clientsToUsedSettings.get(consumer)
      if (usedSettings === undefined) {
        usedSettings = []
        clientsToUsedSettings.set(consumer, usedSettings)
      }
      usedSettings.push(settingType)
    }
  }
  return [...clientsToUsedSettings]
    .map(([key, types]) => {
      const arrayValues = types.map((type) => `'${type}'`).join(', ')
      return `export const settingsUsedInClient${capitalizeFirstCharacter(key)} = [${arrayValues}] as const`
    })
    .join('\n')
}

const capitalizeFirstCharacter = (input: string) => input.charAt(0).toUpperCase() + input.slice(1)

const handCrafted = () => ``

generateTypesFromSchema().catch((e: unknown) => console.error(e))
