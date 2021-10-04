import { writeFileSync } from 'fs'
import { resolve, sep } from 'path'
import { findSchemasIn } from './shared'

export const generateSettingsSchema = () => {
  const settingsBase = resolve(process.cwd() + '/src/schemas/settings/')
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
    const json = schema.segments.join(sep) + '.json'
    const pathToSchema = 'src/schemas/settings/' + json
    acc[type] = {
      '$ref': pathToSchema,
    }
    return acc
  }, {} as any)

  const settingsSchema = { ...settingsSchemaTemplate, required, properties }

  const settingsPath = resolve(process.cwd() + '/src/schemas/settings.json')
  writeFileSync(settingsPath, JSON.stringify(settingsSchema, null, 2))
}

generateSettingsSchema()
