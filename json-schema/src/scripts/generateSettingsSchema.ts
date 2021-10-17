import { writeFileSync } from 'fs'
import { JSONSchema7, JSONSchema7Definition } from 'json-schema'
import { resolve, sep } from 'path'
import { findSchemasIn, FoundSettingSchema } from './shared'

type JsonSchmeaProperties = Exclude<JSONSchema7['properties'], undefined>

const pathTo = (schema: FoundSettingSchema) => {
  const json = schema.segments.join(sep) + '.json'
  return 'src/schemas/settings/' + json
}

export const generateSettingsSchema = () => {
  const settingsBase = resolve(process.cwd() + '/src/schemas/settings/')
  const settingsSchemaTemplate: JSONSchema7 = {
    $comment: 'this is auto generated',
    $schema: 'http://json-schema.org/draft-06/schema/schema',
    definitions: {},
    title: 'SettingsDto',
    description: 'The wire format of the settings',
    type: 'object',
    additionalProperties: true,
  }
  const foundSchemas = findSchemasIn(settingsBase)
  const settings = schemaForSettingsFrom(foundSchemas)

  const settingsDtoProperties = foundSchemas.reduce((acc: JsonSchmeaProperties, schema) => {
    const categoryName = schema.segments[0]
    let category = acc[categoryName] as JSONSchema7 | undefined
    if (category === undefined) {
      category = {
        type: 'object',
        additionalProperties: true,
        title: `${categoryName}Category`,
        properties: {},
      }
      acc[categoryName] = category
    }
    const settingsProperties = category.properties ?? {}
    settingsProperties[schema.segments[1]] = {
      $ref: pathTo(schema),
    }
    return acc
  }, {})

  const settingsSchema: JSONSchema7 = {
    ...settingsSchemaTemplate,
    properties: settingsDtoProperties,
    definitions: {
      settings,
    },
  }

  const settingsPath = resolve(process.cwd() + '/src/schemas/settings.json')
  writeFileSync(settingsPath, JSON.stringify(settingsSchema, null, 2))
}

const schemaForSettingsFrom = (foundSchemas: FoundSettingSchema[]) => {
  const settings: JSONSchema7Definition = {
    title: 'Settings',
    description: 'Support type for TypeScript to get a mapping from setting type to the value type of each setting',
    type: 'object',
    additionalProperties: false,
  }
  settings.required = foundSchemas.reduce((acc: string[], schema) => {
    return [...acc, schema.segments.join('.')]
  }, [])
  settings.properties = foundSchemas.reduce((acc: JsonSchmeaProperties, schema) => {
    const type = schema.segments.join('.')
    acc[type] = {
      $ref: pathTo(schema),
    }
    return acc
  }, {})
  return settings
}

generateSettingsSchema()
