import { readFileSync, writeFileSync } from 'fs'
import { JSONSchema4 } from 'json-schema'
import { compile } from 'json-schema-to-typescript'
import { extname } from 'path'
import $RefParser from '@apidevtools/json-schema-ref-parser'


function readSchema(filename: string): JSONSchema4 {
  const contents = readFileSync(filename)
  return JSON.parse(contents.toString())
}

export async function generate() {
  const fileName = process.cwd() + '/schemas/configuration/settings.json'
  const schema = readSchema(fileName)
  const options = { cwd: process.cwd(), bannerComment: '' }

  let parser = new $RefParser()
  const wohoo = await parser.resolve(schema)

  const o = wohoo.get('#/properties')
  if (o === null) {
    throw new Error('Settings has no properties, that should not happen')
  }
  const keys = Object.keys(o)
  const union = keys.map(key => wohoo.get(`#/properties/${key}/title`)).join(' | ')
  const settingType = `export type Setting = ${union}`
  const schemaCode = await compile(schema, stripExtension(fileName), options)

  const snippets = [schemaCode, settingType]
  const code = snippets.join('\n')
  console.log(code)
  writeFileSync(process.cwd() + '/src/generated/settings.ts', code)
}


export function stripExtension(filename: string): string {
  return filename.replace(extname(filename), '')
}
