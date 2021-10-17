import { writeFileSync } from 'fs'
import { JSONSchema7 } from 'json-schema'
import { resolve, sep } from 'path'
import { Maybe } from '../asserts'
import { findSchemasIn, FoundSettingSchema } from './shared'

// todo use jscon schema types
type SchemaReference = {
  '$ref': string
}

type SchemaReferences = {
  [identifier: string]: Maybe<SchemaReference>
}

type JsonSchmeaProperties = Exclude<JSONSchema7['properties'], undefined>

const pathTo = (schema: FoundSettingSchema) => {
  const json = schema.segments.join(sep) + '.json'
  return 'src/schemas/settings/' + json
}

export const generateSettingsSchema = () => {
  const settingsBase = resolve(process.cwd() + '/src/schemas/settings/')
  const settingsSchemaTemplate: JSONSchema7 = {
    '$comment': 'this is auto generated',
    '$schema': 'http://json-schema.org/draft-06/schema/schema',
    'definitions': {},
    'title': 'SettingsDto',
    'description': 'The wire format of the settings',
    'type': 'object',
    'additionalProperties': true,
    'required': [],
  }
  const foundSchemas = findSchemasIn(settingsBase)
  const settingsProperties = foundSchemas.reduce((acc: SchemaReferences, schema) => {
    const type = schema.segments.join('.')
    acc[type] = {
      $ref: pathTo(schema),
    }
    return acc
  }, {})

  const settingsDtoProperties = foundSchemas.reduce((acc: JsonSchmeaProperties, schema) => {
    const categoryName = schema.segments[0]
    let category = acc[categoryName] as JSONSchema7 | undefined
    if (category === undefined) {
      category = {
        type: 'object',
        additionalProperties: true,
        title: `${categoryName}Category`,
        properties: {}
      }
      acc[categoryName] = category
    }
    const settingsProperties = category.properties ?? {}
    settingsProperties[schema.segments[1]] = {
      $ref: pathTo(schema)
    }
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
