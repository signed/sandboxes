import $RefParser from '@apidevtools/json-schema-ref-parser'
import { readFileSync, writeFileSync } from 'fs'
import { JSONSchema4 } from 'json-schema'
import { compile } from 'json-schema-to-typescript'
import { extname } from 'path'

export async function generate() {
  const fileName = process.cwd() + '/schemas/configuration/settings.json'
  const schema = readSchema(fileName)

  const parser = new $RefParser()
  const wohoo = await parser.resolve(schema)
  const settingTypeCode = settingType(wohoo)
  const settingTypeWithDefaultTypeCode = settingWithDefaultType(wohoo)

  const options = { cwd: process.cwd(), bannerComment: '' }
  const settingsCode = await compile(schema, stripExtension(fileName), options)

  const snippets = [
    settingsCode,
    settingTypeCode,
    settingTypeWithDefaultTypeCode,
  ]
  const code = snippets.join('\n')
  console.log(code)
  writeFileSync(process.cwd() + '/src/generated/settings.ts', code)
}

const readSchema = (filename: string): JSONSchema4 => {
  const contents = readFileSync(filename)
  return JSON.parse(contents.toString())
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
