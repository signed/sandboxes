import $RefParser from '@apidevtools/json-schema-ref-parser'
import { writeFileSync } from 'fs'
import { compile } from 'json-schema-to-typescript'
import { extname } from 'path'
import { readSchema } from './shared'

export async function generateTypesFromSchema() {
  const fileName = process.cwd() + '/src/schemas/settings.json'
  const schema = readSchema(fileName)

  const parser = new $RefParser()
  const wohoo = await parser.resolve(schema)
  const settingTypeCode = settingType(wohoo)
  const settingTypeWithDefaultTypeCode = settingWithDefaultType(wohoo)
  const defaultsTypeCode = defaultsType(wohoo)

  const options = { cwd: process.cwd(), bannerComment: '' }
  const settingsCode = await compile(schema, stripExtension(fileName), options)

  const snippets = [
    settingsCode,
    settingTypeCode,
    settingTypeWithDefaultTypeCode,
    defaultsTypeCode,
  ]
  const code = snippets.join('\n')
  writeFileSync(process.cwd() + '/src/generated/settings.ts', code)
}

const stripExtension = (filename: string): string => filename.replace(extname(filename), '')

const settingType = function(wohoo: $RefParser.$Refs) {
  const o = wohoo.get('#/properties')
  if (o === null) {
    throw new Error('Settings has no properties, that should not happen')
  }
  const keys = Object.keys(o)
  const union = keys.map(key => wohoo.get(`#/properties/${key}/title`)).join(' | ')
  return `export type Setting = ${union}`
}

const settingWithDefaultType = function(wohoo: $RefParser.$Refs) {
  const o = wohoo.get('#/properties')
  if (o === null) {
    throw new Error('Settings has no properties, that should not happen')
  }
  const keys = Object.keys(o)
  const keysWithDefault = keys.filter(key => wohoo.exists(`#/properties/${key}/properties/value/default`))
  const union = keysWithDefault.map(key => {
    return wohoo.get(`#/properties/${key}/properties/type/const`)
  }).map(type => `'${type}'`).join(' | ')
  return `export type SettingTypeWithDefault = ${union}`
}

const defaultsType = function(wohoo: $RefParser.$Refs) {
  const o = wohoo.get('#/properties')
  if (o === null) {
    throw new Error('Settings has no properties, that should not happen')
  }
  const keys = Object.keys(o)
  const keysWithDefault = keys.filter(key => wohoo.exists(`#/properties/${key}/properties/value/default`))
  const union = keysWithDefault.map(key => {
    const type = wohoo.get(`#/properties/${key}/properties/type/const`)
    const defaultt = wohoo.get(`#/properties/${key}/properties/value/default`)
    return { type , defaultt }
  })
  return `
type Defaults = {
  [Property in keyof Settings as Extract<Property, SettingTypeWithDefault>]: Settings[Property]['value']
}
export const defaults: Defaults = { 
  ${union.map(({type, defaultt}) => `'${type}': '${defaultt}'`).join(',\n  ')}
}`
}

generateTypesFromSchema().catch(e => console.error(e))
