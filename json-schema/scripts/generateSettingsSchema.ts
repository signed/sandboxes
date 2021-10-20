import { writeFileSync } from 'fs'
import { JSONSchema7, JSONSchema7Definition } from 'json-schema'
import { sep } from 'path'
import {
  absolutPathToSettingsJson,
  FoundSettingSchema,
  readAllSettingSchemas,
  relativeSchemasBase,
  settingTypeFor,
} from './shared'

type JsonSchemaProperties = Exclude<JSONSchema7['properties'], undefined>

export const generateSettingsSchema = () => {
  const foundSchemas = readAllSettingSchemas()
  const settings = schemaForSettingsFrom(foundSchemas)

  const settingsDocumentProperties = foundSchemas.reduce((acc: JsonSchemaProperties, schema) => {
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
      $ref: jsonPathTo(schema),
    }
    return acc
  }, {})

  const settingsSchemaTemplate: JSONSchema7 = {
    $comment: 'this is auto generated',
    $schema: 'http://json-schema.org/draft-06/schema/schema',
    definitions: {},
    title: 'SettingsDocument',
    description: 'The wire format of the settings',
    type: 'object',
    additionalProperties: true,
  }
  const settingsSchema: JSONSchema7 = {
    ...settingsSchemaTemplate,
    properties: settingsDocumentProperties,
    definitions: {
      settings,
    },
  }

  writeFileSync(absolutPathToSettingsJson, JSON.stringify(settingsSchema, null, 2))
}

const schemaForSettingsFrom = (foundSchemas: FoundSettingSchema[]) => {
  const settings: JSONSchema7Definition = {
    title: 'Settings',
    description: 'Support type for TypeScript to get a mapping from setting type to the value type of each setting',
    type: 'object',
    additionalProperties: false,
  }
  settings.required = foundSchemas.reduce((acc: string[], schema) => {
    return [...acc, settingTypeFor(schema)]
  }, [])
  settings.properties = foundSchemas.reduce((acc: JsonSchemaProperties, schema) => {
    const type = settingTypeFor(schema)
    acc[type] = {
      $ref: jsonPathTo(schema),
    }
    return acc
  }, {})
  return settings
}

const jsonPathTo = (schema: FoundSettingSchema) => {
  const schemaFile = schema.segments.join(sep)
  return `${relativeSchemasBase}/${schemaFile}.json`
}

generateSettingsSchema()
