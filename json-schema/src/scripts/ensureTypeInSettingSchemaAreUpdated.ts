import $RefParser from '@apidevtools/json-schema-ref-parser'
import { writeFileSync } from 'fs'
import { resolve } from 'path'
import { findSchemasIn, FoundSettingSchema, readSchema } from './shared'

export const ensureCorrectTypePropertyInSettings = () => {
  const settingsBase = resolve(process.cwd() + '/src/schemas/settings/')
  const foundSchemas = findSchemasIn(settingsBase)

  foundSchemas.forEach(async found => {
    await ensureProperType(found)
  })
}

const ensureProperType = async function(found: FoundSettingSchema) {
  const schema = readSchema(found.path)
  const parser = new $RefParser()
  const r = await parser.resolve(schema)
  r.set('#/properties/type/const', found.segments.join('.'))
  writeFileSync(found.path, JSON.stringify(schema, null, 2))
}

ensureCorrectTypePropertyInSettings()
