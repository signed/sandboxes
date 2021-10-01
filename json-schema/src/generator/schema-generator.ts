import $RefParser from '@apidevtools/json-schema-ref-parser'
import fs, { writeFileSync } from 'fs'
import p from 'path'
import { readSchema } from './shared'

export const generateSettingsSchema = () => {
  const settingsBase = p.resolve(process.cwd() + '/schemas/configuration/settings-nested/')
  const settingsSchemaTemplate = {
    '$comment': 'this is auto generated',
    '$schema': 'http://json-schema.org/draft-06/schema/schema',
    'title': 'Settings',
    'description': 'All settings supported by the application',
    'type': 'object',
    'additionalProperties': false,
    'required': [],
  }
  const foundSchemas = findSchemasIn(settingsBase)
  const required = foundSchemas.reduce((acc: string[], schema) => {
      const type = schema.segments.join('.')
      return [...acc, type]
    }, [],
  )
  const properties = foundSchemas.reduce((acc, schema) => {
    const type = schema.segments.join('.')
    const json = schema.segments.join(p.sep) + '.json'
    const pathToSchema = 'schemas/configuration/settings-nested/' + json
    acc[type] = {
      type: 'object',
      '$ref': pathToSchema,
    }
    return acc
  }, {} as any)

  const settingsSchema = { ...settingsSchemaTemplate, required, properties }

  const settingsPath = p.resolve(process.cwd() + '/schemas/configuration/settings-nested.json')
  writeFileSync(settingsPath, JSON.stringify(settingsSchema, null, 2))
}

export const ensureCorrectTypePropertyInSettings = () => {
  const settingsBase = p.resolve(process.cwd() + '/schemas/configuration/settings-nested/')
  const foundSchemas = findSchemasIn(settingsBase)

  foundSchemas.forEach(async found => {
    await ensureProperType(found)
  })
}

const findSchemasIn = function(settingsBase: string) {
  const schemas = jsonFiles(settingsBase)
  return schemas.map(found => {
    const sub = p.dirname(found).replace(settingsBase + '/', '')
    const filename = p.basename(found, '.json')
    const segments = [...sub.split(p.sep), filename]
    return { path: found, segments }
  })
}

const jsonFiles = (base: string, found: string [] = []) => {
  const elements = fs.readdirSync(base)
  elements.forEach((element) => {
    const path = p.join(base, element)
    const stat = fs.lstatSync(path)
    if (stat.isDirectory()) {
      return jsonFiles(path, found)
    }
    const s = p.extname(path)
    if (stat.isFile() && s === '.json') {
      found.push(path)
    }
  })
  return found
}

const ensureProperType = async function(found: { path: string; segments: string[] }) {
  const schema = readSchema(found.path)
  const parser = new $RefParser()
  const r = await parser.resolve(schema)
  r.set('#/properties/type/const', found.segments.join('.'))
  fs.writeFileSync(found.path, JSON.stringify(schema, null, 2))
}
