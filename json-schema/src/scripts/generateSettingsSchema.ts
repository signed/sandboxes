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
    'definitions': {},
    'title': 'SettingsDtoWip',
    'description': 'The wire format of the settings',
    'type': 'object',
    'additionalProperties': true,
    'required': [],
  }
  const foundSchemas = findSchemasIn(settingsBase)
  const settingsProperties = foundSchemas.reduce((acc: SchemaReferences, schema) => {
    const type = schema.segments.join('.')
    const json = schema.segments.join(sep) + '.json'
    const pathToSchema = 'src/schemas/settings/' + json
    acc[type] = {
      '$ref': pathToSchema,
    }
    return acc
  }, {})

  const settingsDtoProperties = {
    editor: {
      type: 'object',
      additionalProperties: true,
      title: 'Editor',
      properties: {
        'hello': 'string',
      }
    },
    general: {
      type: 'object',
      additionalProperties: true,
      title: 'General',
      properties: {
        'hello': 'string',
      },
    },
    ui: {
      type: 'object',
      additionalProperties: true,
      title: 'Ui',
      properties: {
        'hello': 'string',
      },
    },
  }

  const settingsSchema = {
    ...settingsSchemaTemplate, properties: settingsDtoProperties, definitions: {
      'settings': {
        'title': 'SettingsDocument',
        'description': 'All settings supported by the application',
        'type': 'object',
        'additionalProperties': false,
        properties: settingsProperties,
      },
    },
  }

  const settingsPath = resolve(process.cwd() + '/src/schemas/settings.json')
  writeFileSync(settingsPath, JSON.stringify(settingsSchema, null, 2))
}

generateSettingsSchema()
