import { writeFileSync } from 'fs'
import { resolve, sep } from 'path'
import { Maybe } from '../asserts'
import { findSchemasIn } from './shared'
import { JSONSchema7 } from 'json-schema'

// todo use jscon schema types
type SchemaReference = {
  '$ref': string
}

type SchemaReferences = {
  [identifier: string]: Maybe<SchemaReference>
}

type JsonSchmeaProperties = Exclude<JSONSchema7['properties'], undefined>

export const generateSettingsSchema = () => {
  const settingsBase = resolve(process.cwd() + '/src/schemas/settings/')
  const settingsSchemaTemplate: JSONSchema7 = {
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

  let settingsDtoProperties: JsonSchmeaProperties = {
    editor: {
      type: 'object',
      additionalProperties: true,
      title: 'Editor',
      properties: {
        'hello': {
          type: 'string',
        },
      },
    },
    general: {
      type: 'object',
      additionalProperties: true,
      title: 'General',
      properties: {
        'hello': {
          type: 'string',
        }
      },
    },
    ui: {
      type: 'object',
      additionalProperties: true,
      title: 'Ui',
      properties: {
        'hello': {
          type: 'string',
        }
      },
    },
  }

  settingsDtoProperties = foundSchemas.reduce((acc: JsonSchmeaProperties, schema) => {
    console.log(schema.segments)
    return acc
  }, {})

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
