import { writeFileSync } from 'fs'
import { resolve, sep } from 'path'
import { Maybe } from '../asserts'
import { findSchemasIn } from './shared'

type SchemaReference = {
  '$ref': string
}

type SchemaReferences = {
  [identifier: string]: Maybe<SchemaReference>
}

export const generateSettingsSchema = () => {
  const settingsBase = resolve(process.cwd() + '/src/schemas/settings/')
  const settingsSchemaTemplate = {
    '$comment': 'this is auto generated',
    '$schema': 'http://json-schema.org/draft-06/schema/schema',
    'title': 'SettingsDocument',
    'description': 'All settings supported by the application',
    'type': 'object',
    'additionalProperties': false,
    'required': [],
  }
  const foundSchemas = findSchemasIn(settingsBase)
  const properties = foundSchemas.reduce((acc: SchemaReferences, schema) => {
    const type = schema.segments.join('.')
    const json = schema.segments.join(sep) + '.json'
    const pathToSchema = 'src/schemas/settings/' + json
    acc[type] = {
      '$ref': pathToSchema,
    }
    return acc
  }, {})

  const settingsSchema = { ...settingsSchemaTemplate, properties }

  const settingsPath = resolve(process.cwd() + '/src/schemas/settings.json')
  writeFileSync(settingsPath, JSON.stringify(settingsSchema, null, 2))
}

generateSettingsSchema()
